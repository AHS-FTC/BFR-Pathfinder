package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.util.Coordinate;
import edu.ahs.robotics.pathfinder.util.WayPoint;

public class NewWayPointWindow extends XYHeadingWindow {

    private Environment environment;
    private PathWindow pathWindow;

    public NewWayPointWindow(Environment environment, PathWindow pathWindow) {
        super("Create New Waypoint");
        this.environment = environment;
    }

    @Override
    public void apply(double x, double y, double heading) {
        Coordinate c = Coordinate.newFromInches(x,y);
        pathWindow.getActivePath().addWayPoint(new WayPoint(c, heading));
    }
}
