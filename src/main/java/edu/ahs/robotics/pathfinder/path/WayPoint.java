package edu.ahs.robotics.pathfinder.path;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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

    private Group group;
    private Text label;
    private Circle circle;
    private HeadingPointer headingPointer;

    private int count;
    private static final double X_OFFSET = 4.0;
    private Color color = Color.WHITE;

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

        headingPointer = new HeadingPointer(GRAPHIC_RADIUS, coordinate, 0);

        group.getChildren().addAll(headingPointer, label);
    }

    /**
     * @param heading Heading in standard convention radians
     */
    public void setHeading(double heading){ // setter enforces heading primitivism
        this.heading = heading;
        headingPointer.setHeading(heading);
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
}
