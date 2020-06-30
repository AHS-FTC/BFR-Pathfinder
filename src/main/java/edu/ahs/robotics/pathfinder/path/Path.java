package edu.ahs.robotics.pathfinder.path;


import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.ui.primary.SideBar;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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
    private Group graphics = new Group();

    private Color color;

    private RadioButton radioButton = new RadioButton();
    private CheckBox viewBox = new CheckBox();

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
        viewBox.setSelected(true);
        viewBox.selectedProperty().addListener(e -> graphics.setVisible(viewBox.isSelected()));

        color = defaultColors.get(pathCount);
        if(color == null){
            int r = (int)Math.round(Math.random() * 255.0);
            int g = (int)Math.round(Math.random() * 255.0);
            int b = (int)Math.round(Math.random() * 255.0);

            color = Color.rgb(r,g,b);
        }
    }

    public void addWayPoint(WayPoint wayPoint) {

        //auto assign headings to ambiguous waypoints
        //todo consider a refactor to make this algorithm clear? perhaps factor out method?
        if(wayPoint.getHeading() == null){
            if(wayPoints.size() != 0){
                WayPoint lastWayPoint = wayPoints.get(wayPoints.size() - 1);
                double angle;
                if(SideBar.getInstance().isFollow()){
                    angle = lastWayPoint.getCoordinate().angleTo(wayPoint.getCoordinate());
                    wayPoint.setHeading(angle);
                    lastWayPoint.reFollow(wayPoint);
                } else { //hold
                    if(lastWayPoint.getHeading() != null) {
                        angle = lastWayPoint.getHeading();
                        wayPoint.setHeading(angle);
                        if (!lastWayPoint.isAmbiguous()) {
                            wayPoint.disambiguate();
                        }
                    }
                }
            }
        }

        wayPoints.add(wayPoint);
        wayPoint.setColor(color);
        wayPoint.setCount(wayPoints.size());

        if (wayPoints.size() > 1) {
            int newestPointIndex = wayPoints.size() - 1;
            Coordinate c1 = wayPoints.get(newestPointIndex).getCoordinate();
            Coordinate c2 = wayPoints.get(newestPointIndex - 1).getCoordinate();
            Line l = createLine(c1,c2);

            lines.add(l);
            graphics.getChildren().add(l);
        }

        graphics.getChildren().add(wayPoint.getGraphic());
    }

    /**
     * @return An group of all graphical elements
     */
    public Group getGraphics() {
        return graphics;
    }

    public Color getColor(){
        return color;
    }

    public RadioButton getRadioButton(){
        return radioButton;
    }

    public void enableRadioButton(){
//        if(!radioButton.isSelected()){ //todo fix
//            radioButton.setSelected(true);
//        }
    }

    public CheckBox getViewBox() {
        return viewBox;
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
