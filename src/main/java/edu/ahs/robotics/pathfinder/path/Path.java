package edu.ahs.robotics.pathfinder.path;


import edu.ahs.robotics.pathfinder.path.geometry.Pose2d;
import edu.ahs.robotics.pathfinder.path.geometry.Rotation2d;
import edu.ahs.robotics.pathfinder.path.geometry.Translation2d;
import edu.ahs.robotics.pathfinder.path.spline.GeometricPath;
import edu.ahs.robotics.pathfinder.path.spline.PathPoint;
import edu.ahs.robotics.pathfinder.path.spline.SplinePath;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.ui.primary.SideBar;
import edu.ahs.robotics.pathfinder.ui.text.StandardText;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

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
    private Text pathText;

    private static int pathCount = 0;
    public String name;

    private static final HashMap<Integer,Color> defaultColors = new HashMap<>();

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

        pathText = new StandardText(name);
        pathText.setOnMouseClicked(e -> onClick());

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
        if(wayPoint.getHeading() == null){
            determineHeading(wayPoint);
        }

        wayPoints.add(wayPoint);
        wayPoint.setColor(color);
        wayPoint.setCount(wayPoints.size());

        if (wayPoints.size() > 1) {
            int newestPointIndex = wayPoints.size() - 1;

            WayPoint newestWayPoint = wayPoints.get(newestPointIndex);
            WayPoint previousWayPoint = wayPoints.get(newestPointIndex - 1);

            Coordinate c1 = newestWayPoint.getCoordinate();
            Coordinate c2 = previousWayPoint.getCoordinate();
            Line l = createLine(c1,c2);

            newestWayPoint.setPrevLine(l);
            previousWayPoint.setNextLine(l);

            lines.add(l);
            graphics.getChildren().add(l);
        }

        graphics.getChildren().add(wayPoint.getGraphic());
    }

    private void determineHeading(WayPoint wayPoint) {
        if(wayPoints.size() != 0){
            WayPoint lastWayPoint = wayPoints.get(wayPoints.size() - 1);
            double angle;
            if(SideBar.getInstance().isFollow()){
                angle = lastWayPoint.getCoordinate().angleTo(wayPoint.getCoordinate());

                if(lastWayPoint.getHeading() != null) {
                    angle = calculateBestAngle(angle, lastWayPoint.getHeading());
                }

                wayPoint.setHeading(angle);
                lastWayPoint.reFollow(angle);

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

    /*protected for testing*/ static double calculateBestAngle(double rawAngle, double lastAngle){
        double retAngle;
        double angleDifference = lastAngle - rawAngle;

        //make sure angleDifference isn't more than 2pi away from the last angle
        if (Math.abs(angleDifference) > 2 * Math.PI) {
            angleDifference  %= (2 * Math.PI);
        }

        // Make sure we have the most 'efficient' relative angleDistance
        if (Math.abs(angleDifference) > Math.PI) {
            if (Math.signum(angleDifference) == 1) {
                angleDifference = (2 * Math.PI) - angleDifference;
            } else {
                angleDifference =  angleDifference + (2 * Math.PI);
            }
        }

        retAngle = lastAngle - angleDifference;//derived from definition of angleDifference
        return retAngle;
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

    public void rename(String s){
        name = s;
        name = name.replace(" ", "_");
        pathText.setText(name);
    }

    public void interpolate(){
        ArrayList<Pose2d> splinePoints = new ArrayList<>();

        for (WayPoint wayPoint : wayPoints) {
            Coordinate c = wayPoint.getCoordinate();
            Translation2d t = new Translation2d(c.getInchX(), c.getInchY());
            Rotation2d r = Rotation2d.Companion.fromRadians(wayPoint.getHeading());
            Pose2d pose2d = new Pose2d(t, r);
            splinePoints.add(pose2d);
        }

        GeometricPath splinePath = SplinePath.Companion.composite(splinePoints);
        for(int i = 0; i < splinePath.getLength(); i++){
            PathPoint pathPoint = splinePath.getPoint(i);
            Pose2d pose2d = pathPoint.getPose();
            Translation2d t = pose2d.getTranslation();
            Coordinate c = Coordinate.newFromInches(t.x(), t.y());

            WayPoint wayPoint = new WayPoint(c, pose2d.getRotation().getRadians());
            wayPoint.disableLabel();
            addWayPoint(wayPoint);
        }
    }

    public Text getPathText(){
        return pathText;
    }

    private void onClick(){
        wayPoints.forEach(wayPoint -> wayPoint.setSelected(true));
    }

    public RadioButton getRadioButton(){
        return radioButton;
    }

    /**
     * Makes this path the active path, also properly updates the gui.
     * Use this when possible over setActivePath()
     */
    public void makeActivePath(){
        radioButton.fire(); //calls the lambda on the radio button
    }

    public CheckBox getViewBox() {
        return viewBox;
    }

    public ArrayList<WayPoint> getWayPoints(){
        return wayPoints;
    }

    /**
     * Create line segment
     */
    private Line createLine(Coordinate c1, Coordinate c2) {
        Line l = new Line(c2.getPixelX(), c2.getPixelY(), c1.getPixelX(), c1.getPixelY());
        l.setStroke(color);

        return l;
    }
}
