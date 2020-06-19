package edu.ahs.robotics.pathfinder.ui;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.util.Coordinate;
import edu.ahs.robotics.pathfinder.util.WayPoint;

public class NewWayPointWindow extends XYHeadingWindow {

    private Environment environment;

    public NewWayPointWindow(Environment environment) {
        super("Create New Waypoint");
        this.environment = environment;
    }

    @Override
    public void apply(double x, double y, double heading) {
        Coordinate c = Coordinate.newFromInches(x,y);
        environment.addWayPoint(new WayPoint(c, heading));
    }
}
