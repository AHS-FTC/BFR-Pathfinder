package edu.ahs.robotics.pathfinder.util;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    private static int count; //todo integrate count with higher level collection
    private static final double X_OFFSET = 4.0;
    private static final Color COLOR = Color.rgb(253,238,0);


    public WayPoint(Coordinate coordinate, double heading) {
        count++;

        this.coordinate = coordinate;
        this.heading = heading;

        circle = new Circle(coordinate.getPixelX(), coordinate.getPixelY(), GRAPHIC_RADIUS);
        circle.setFill(COLOR);

        label = new Text(String.valueOf(count));

        label.setX(coordinate.getPixelX() + X_OFFSET);
        label.setY(coordinate.getPixelY());
        label.setFill(COLOR);

        group = new Group();

        group.getChildren().addAll(circle, label);
    }

    public Node getGraphic(){
        return group;
    }

}
