package edu.ahs.robotics.pathfinder.ui.windows;


import edu.ahs.robotics.pathfinder.path.WayPoint;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.util.Coordinate;

public class NewWayPointWindow extends XYHeadingWindow {

    private PathWindow pathWindow = PathWindow.getInstance();

    public NewWayPointWindow(PathWindow pathWindow) {
        super("Create New Waypoint");
    }

    @Override
    public void apply(double x, double y, double heading) {
        Coordinate c = Coordinate.newFromInches(x,y);
        pathWindow.getActivePath().addWayPoint(new WayPoint(c, heading));
    }
}
