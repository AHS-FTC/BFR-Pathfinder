package edu.ahs.robotics.pathfinder.util;


import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * An ordered collection of waypoints that makes up an autopath.
 *
 * @author Alex Appleby, team 16896
 */
public class Path {
    private ArrayList<WayPoint> wayPoints = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private Group grapics = new Group();

    private Color color;
    private boolean showInterpolations = true;

    private RadioButton radioButton = new RadioButton();

    private static int pathCount = 0;
    public String name;

    private static HashMap<Integer,Color> defaultColors = new HashMap();

    static {
        defaultColors.put(1, Color.rgb(253, 238, 0));
        defaultColors.put(2, Color.CYAN);
        defaultColors.put(3, Color.DARKORANGE);
        defaultColors.put(4, Color.DODGERBLUE);
        defaultColors.put(5, Color.MAGENTA);
        defaultColors.put(6, Color.TOMATO);
    }

    public Path() {
        pathCount++;
        name = "Path"+pathCount;

        radioButton.selectedProperty().addListener(e -> PathWindow.getInstance().setActivePath(this));

        color = defaultColors.get(pathCount);
        if(color == null){
            int r = (int)Math.round(Math.random() * 255.0);
            int g = (int)Math.round(Math.random() * 255.0);
            int b = (int)Math.round(Math.random() * 255.0);

            color = Color.rgb(r,g,b);
        }
    }

    public void addWayPoint(WayPoint wayPoint) {
        wayPoints.add(wayPoint);
        wayPoint.setColor(color);
        wayPoint.setCount(wayPoints.size());

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

    public RadioButton getRadioButton(){
        return radioButton;
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
