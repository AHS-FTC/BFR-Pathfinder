package edu.ahs.robotics.pathfinder.path;

import edu.ahs.robotics.pathfinder.util.Coordinate;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * A desired robot state on the field, as an element of an autonomous path.
 * @author Alex Appleby, team 16896
 */
public class WayPoint {

    private static final double GRAPHIC_RADIUS = 4;

    private Coordinate coordinate;
    private double heading;

    private Group group;
    private Text label;
    private Circle circle;

    private int count;
    private static final double X_OFFSET = 4.0;
    private Color color = Color.WHITE;


    public WayPoint(Coordinate coordinate, double heading) {
        this.coordinate = coordinate;
        this.heading = heading;

        circle = new Circle(coordinate.getPixelX(), coordinate.getPixelY(), GRAPHIC_RADIUS);
        circle.setFill(color);

        label = new Text(String.valueOf(count));

        label.setX(coordinate.getPixelX() + X_OFFSET);
        label.setY(coordinate.getPixelY());
        label.setFill(color);

        group = new Group();

        group.getChildren().addAll(circle, label);
    }

    void setColor(Color color){ //only use in util package
        this.color = color;
        circle.setFill(color);
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
