package edu.ahs.robotics.pathfinder.path;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

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

    private Group group;
    private Text label;
    private Circle circle;
    private Rectangle selectionBox;
    private HeadingPointer headingPointer;

    private int count;
    private static final double X_OFFSET = 4.0;
    private Color color = Color.WHITE;

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

        circle = new Circle(coordinate.getPixelX(), coordinate.getPixelY(), GRAPHIC_RADIUS);
        circle.setFill(color);

        label = new Text(String.valueOf(count));

        label.setX(coordinate.getPixelX() + X_OFFSET);
        label.setY(coordinate.getPixelY());
        label.setFill(color);

        group = new Group();

        group.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onMouseClicked);

        headingPointer = new HeadingPointer(GRAPHIC_RADIUS, coordinate);
        selectionBox = makeSelectionBox();

        group.getChildren().addAll(headingPointer.getGraphics(), label, selectionBox);
    }

    /**
     * @param heading Heading in standard convention radians
     */
    public void setHeading(double heading){ // setter enforces heading primitivism
        this.heading = heading;
        headingPointer.setHeading(heading);
    }

    /**
     * for ambiguous points, reassess pathfollowing heading after a point ahead of this one is added
     */
    public void reFollow(WayPoint wayPoint){
        if(isAmbiguous() && heading != null ){
            double newIdealHeading = coordinate.angleTo(wayPoint.getCoordinate()); //todo fix pos/negative bug on this method with this implementation

            //average of old and new heading is new heading
            setHeading((heading + newIdealHeading)/2.0);
        }
    }

    public Double getHeading(){ //nonprimitive to hold ambiguous null value
        return heading;
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

    private Rectangle makeSelectionBox(){
        Rectangle r = new Rectangle();
        r.setWidth(GRAPHIC_RADIUS * 2);
        r.setHeight(GRAPHIC_RADIUS * 2);
        r.setX(coordinate.getPixelX() - (r.getWidth()/2.0));
        r.setY(coordinate.getPixelY() - (r.getHeight()/2.0));
        r.setFill(Color.TRANSPARENT);
        r.setVisible(false);

        return r;
    }

    private void onMouseClicked(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY){ //toggle selection
            if(selected){
                selectedWayPoints.remove(this);
            } else {
                selectedWayPoints.add(this);
            }
            selected = !selected;
            selectionBox.setVisible(selected);
        }
    }
}
