package edu.ahs.robotics.pathfinder.path;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private static final double GRAPHIC_RADIUS = 6;

    private Coordinate coordinate;

    private Double heading = null;  //see class javadoc

    private boolean ambiguous = true; //see constructors
    private boolean selected = false;

    //the lines connecting to the previous and next waypoints respectively
    private Line prevLine, nextLine;

    private Group group;
    private Text label;
    private Circle circle;
    private Rectangle selectionBox;
    private HeadingPointer headingPointer;

    private int count;
    private static final double X_OFFSET = 4.0;
    private Color color = Color.WHITE;
    private Slider slider = new Slider(0, 1, 0);

    private static ArrayList<WayPoint> selectedWayPoints = new ArrayList<>();

    /**
     * Determined heading constructor.
     */
    public WayPoint(Coordinate coordinate, double heading) {
        this(coordinate);
        this.heading = heading;
        ambiguous = false;
        headingPointer.setHeading(heading);
    }

    /**
     * Ambiguous heading constructor.
     * By assigning an ambiguous waypoint, the heading will be assigned in context when the WayPoint is given to a Path.
     */
    public WayPoint(Coordinate coordinate){
        this.coordinate = coordinate;

        circle = new Circle(GRAPHIC_RADIUS);
        circle.setFill(color);

        label = new Text(String.valueOf(count));


        label.setFill(color);

        group = new Group();

        group.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onMouseClicked);

        headingPointer = new HeadingPointer(GRAPHIC_RADIUS, coordinate);

        makeSelectionBox();

        renderPosition();

        group.getChildren().addAll(headingPointer.getGraphics(), label, selectionBox);
    }

    /**
     * @param heading Heading in standard convention radians
     */
    public void setHeading(double heading){ // setter enforces heading primitivism
        this.heading = heading;
        headingPointer.setHeading(heading);
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
    public void reFollow(WayPoint wayPoint, double newIdealHeading){
        if(isAmbiguous() && heading != null ){
            //double newIdealHeading = coordinate.angleTo(wayPoint.getCoordinate()); //todo fix pos/negative bug on this method with this implementation

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

        renderPosition();
    }

    /*protected*/ void setColor(Color color){ //only use in path package
        this.color = color;
        circle.setFill(color);
        headingPointer.setFill(color);
        label.setFill(color);
        selectionBox.setStroke(color);
    }

    /*protected*/ void setCount (int count){
        this.count = count;
        label.setText(String.valueOf(count));
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

    public void setSelected(boolean selected){
        this.selected = selected;
        if(selected){
            selectedWayPoints.add(this);
        } else {
            selectedWayPoints.remove(this);
        }
        selectionBox.setVisible(selected);
    }

    public void disableLabel(){
        label.setVisible(false);
    }

    private void renderPosition(){
        double y = coordinate.getPixelY();
        double x = coordinate.getPixelX();

        label.setX(x + X_OFFSET);
        label.setY(y);

        circle.setCenterX(x);
        circle.setCenterY(y);

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
