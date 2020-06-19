package edu.ahs.robotics.pathfinder.util;

import javafx.scene.shape.Circle;

/**
 * A desired robot state on the field, as an element of an autonomous path.
 * @author Alex Appleby, team 16896
 */
public class WayPoint {

    private static final double GRAPHIC_RADIUS = 4;

    private Coordinate coordinate;
    private double heading;
    private Circle circle;

    public WayPoint(Coordinate coordinate, double heading) {
        this.coordinate = coordinate;
        this.heading = heading;

        circle = new Circle(coordinate.getPixelX(), coordinate.getPixelY(), GRAPHIC_RADIUS);
    }

    public Circle getGraphic(){
        return circle;
    }

}
