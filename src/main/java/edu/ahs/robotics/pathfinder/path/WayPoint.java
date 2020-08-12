package edu.ahs.robotics.pathfinder.path;

import edu.ahs.robotics.pathfinder.ui.text.StandardText;
import edu.ahs.robotics.pathfinder.util.Util;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A desired robot state on the field, as an element of an autonomous path.
 * @author Alex Appleby, team 16896
 *
 * heading is stored as a nonprimitive to allow for a null heading with an ambiguous waypoint.
 * A null heading prevents bugs by handing over a null pointer instead of an arbitrary value.
 */
public class WayPoint {
    private static final double GRAPHIC_RADIUS = 8;

    private Coordinate coordinate;

    private Double heading = null;  //see class javadoc

    private boolean ambiguous = true; //see constructors
    private boolean selected = false;

    //the lines connecting to the previous and next waypoints respectively
    private Line prevLine, nextLine;

    private Group group;
    private Text numberLabel, xLabel, yLabel, headingLabel;
    private VBox labelBox = new VBox(2); //contains x,y,h labels
    private Rectangle selectionBox;
    private HeadingPointer headingPointer;

    private int count;
    private static final double X_OFFSET = 4.0;
    private Color color = Color.WHITE;
    private final Slider slider = new Slider(0, 1, .5);

    private static ArrayList<WayPoint> selectedWayPoints = new ArrayList<>();

    /**
     * Determined heading constructor.
     */
    public WayPoint(Coordinate coordinate, double heading) {
        this(coordinate);
        this.heading = heading;
        ambiguous = false;
    }

    /**
     * Ambiguous heading constructor.
     * By assigning an ambiguous waypoint, the heading will be assigned in context when the WayPoint is given to a Path.
     */
    public WayPoint(Coordinate coordinate){
        this.coordinate = coordinate;

        numberLabel = new Text(String.valueOf(count));

        xLabel = new Text();
        yLabel = new Text();
        headingLabel = new Text();
        headingLabel.setVisible(false);

        labelBox.getChildren().addAll(xLabel,yLabel,headingLabel);
        labelBox.setVisible(false);

        group = new Group();

        group.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onMouseClicked);
        group.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);

        headingPointer = new HeadingPointer(GRAPHIC_RADIUS, coordinate);

        makeSelectionBox();

        update();

        group.getChildren().addAll(headingPointer.getGraphics(), numberLabel, selectionBox, labelBox);
    }

    /**
     * @param heading Heading in standard convention radians
     */
    public void setHeading(double heading){ // setter enforces heading primitivism
        this.heading = heading;
        headingPointer.setHeading(heading);
        update();
    }

    public void setPrevLine(Line previous){
        prevLine = previous;
    }

    public void setNextLine(Line next){
        nextLine = next;
    }

    /**
     * for ambiguous points, reassess pathfollowing heading after a point ahead of this one is added
     */
    public void reFollow(double newIdealHeading){
        if(isAmbiguous() && heading != null ){

        //average of old and new heading is new heading
        setHeading((heading + newIdealHeading)/2.0);
        }
    }

    public Double getHeading(){ //nonprimitive to hold ambiguous null value
        return heading;
    }

    public Slider getSlider(){
        return slider;
    }

    public boolean isAmbiguous(){
        return ambiguous;
    }

    /**
     * Call when a WayPoint's heading is no longer ambiguous.
     * Heading may still be changed, but it will no longer be auto assigned by Path.
     */
    public void disambiguate(){
        if(heading == null){
            throw new UnsupportedOperationException("Tried to disambiguate a point with a null heading");
        }
        ambiguous = false;
    }

    /**
     * Nudges the current xy position of the WayPoint
     */
    public void scoot(double xIns, double yIns){
        double x = coordinate.getInchX();
        double y = coordinate.getInchY();

        coordinate.setInchX(x + xIns);
        coordinate.setInchY(y + yIns);

        update();
    }

    /*protected*/ void setColor(Color color){ //only use in path package
        this.color = color;
        headingPointer.setFill(color);
        numberLabel.setFill(color);
        xLabel.setFill(color);
        yLabel.setFill(color);
        headingLabel.setFill(color);
        selectionBox.setStroke(color);
    }

    /*protected*/ void setCount (int count){
        this.count = count;
        numberLabel.setText(String.valueOf(count));
    }

    /**
     * Returns the readable position of this waypoint within it's path, where the first point is 1.
     * You should probably only use this for graphical purposes.
     */
    public int getCount(){
        return count;
    }

    public Node getGraphic(){
        return group;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public static void deselectAll(){
        for (WayPoint wp : selectedWayPoints) {
            wp.selectionBox.setVisible(false);
            wp.labelBox.setVisible(false);
            wp.selected = false;
        }
        selectedWayPoints.clear();
    }

    public static ArrayList<WayPoint> getSelectedWayPoints(){
        return selectedWayPoints;
    }

    private void makeSelectionBox(){
        selectionBox = new Rectangle();
        selectionBox.setWidth(GRAPHIC_RADIUS * 2);
        selectionBox.setHeight(GRAPHIC_RADIUS * 2);
        setBoxCorners();
        selectionBox.setFill(Color.TRANSPARENT);
        selectionBox.setVisible(false);
    }

    private void setBoxCorners(){
        selectionBox.setX(coordinate.getPixelX() - (selectionBox.getWidth()/2.0));
        selectionBox.setY(coordinate.getPixelY() - (selectionBox.getHeight()/2.0));
    }

    private void onMouseClicked(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY){ //toggle selection
            setSelected(!selected);
        }
    }

    private void onMouseDragged(MouseEvent e){
        Point2D clickPoint = new Point2D(e.getSceneX(), e.getSceneY());

        //translate the click from scene coordinates to field coordinates
        Point2D translatedPoint = group.getParent().sceneToLocal(clickPoint);
        coordinate.setPixelX(translatedPoint.getX());
        coordinate.setPixelY(translatedPoint.getY());
        update();
    }

    public void setSelected(boolean selected){
        this.selected = selected;
        if(selected){
            selectedWayPoints.add(this);
        } else {
            selectedWayPoints.remove(this);
        }
        selectionBox.setVisible(selected);
        labelBox.setVisible(selected);
    }

    public void disableLabel(){
        numberLabel.setVisible(false);
    }

    /**
     * Updates graphical components after a positional change
     */
    private void update(){
        double y = coordinate.getPixelY();
        double x = coordinate.getPixelX();

        numberLabel.setX(x + X_OFFSET);
        numberLabel.setY(y);

        labelBox.setLayoutX(x + 10);
        labelBox.setLayoutY(y + 10);

        setBoxCorners();

        if(prevLine != null){
            prevLine.setEndX(x);
            prevLine.setEndY(y);
        }

        if(nextLine != null){
            nextLine.setStartX(x);
            nextLine.setStartY(y);
        }

        headingPointer.setPosition(coordinate);

        //update position labels
        xLabel.setText(Util.formatDouble(coordinate.getInchX()));
        yLabel.setText(Util.formatDouble(coordinate.getInchY()));

        if(heading != null){
            headingPointer.setHeading(heading);
            headingLabel.setText(Util.formatHeading(heading));
            if(!headingLabel.isVisible()){
                headingLabel.setVisible(true);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WayPoint wayPoint = (WayPoint) o;

        if (ambiguous != wayPoint.ambiguous) return false;
        if (!coordinate.equals(wayPoint.coordinate)) return false;
        return Objects.equals(heading, wayPoint.heading);
    }

//    @Override
//    public int hashCode() {
//        int result = coordinate.hashCode();
//        result = 31 * result + (heading != null ? heading.hashCode() : 0);
//        result = 31 * result + (ambiguous ? 1 : 0);
//        return result;
//    }

}
