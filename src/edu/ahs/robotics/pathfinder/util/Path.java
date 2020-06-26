package edu.ahs.robotics.pathfinder.util;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import javax.swing.*;
import java.util.ArrayList;

/**
 * An ordered collection of waypoints that makes up an autopath.
 *
 * @author Alex Appleby, team 16896
 */
public class Path {
    private ArrayList<WayPoint> wayPoints = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private Group grapics = new Group();

    private Color color = Color.rgb(253, 238, 0);
    private boolean showInterpolations = true;

    private static int pathCount = 0;
    public String name;

    public Path() {
        pathCount++;
        name = "Path"+pathCount;
    }

    public void addWayPoint(WayPoint wayPoint) {
        wayPoints.add(wayPoint);
        wayPoint.setColor(color);

        if (wayPoints.size() > 1) {
            int newestPointIndex = wayPoints.size() - 1;
            Coordinate c1 = wayPoints.get(newestPointIndex).getCoordinate();
            Coordinate c2 = wayPoints.get(newestPointIndex - 1).getCoordinate();
            Line l = createLine(c1,c2);

            lines.add(l);
            grapics.getChildren().add(l);
        }

        grapics.getChildren().add(wayPoint.getGraphic());
    }

    /**
     * @return An group of all graphical elements
     */
    public Group getGraphics() {
        return grapics;
    }

    public Color getColor(){
        return color;
    }

    /**
     * Create line segment
     */
    private Line createLine(Coordinate c1, Coordinate c2) {
        Line l = new Line(c1.getPixelX(), c1.getPixelY(), c2.getPixelX(), c2.getPixelY());
        l.setStroke(color);

        return l;
    }
}
