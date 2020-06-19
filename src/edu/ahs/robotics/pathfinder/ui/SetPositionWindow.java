package edu.ahs.robotics.pathfinder.ui;

import edu.ahs.robotics.pathfinder.util.Coordinate;

public class SetPositionWindow extends XYHeadingWindow {

    private Robot robot;

    public SetPositionWindow(Robot robot) {
        super("Set Robot Position");
        this.robot = robot;
    }

    @Override
    public void apply(double x, double y, double heading){
        robot.setPosition(Coordinate.newFromInches(x,y));
        robot.setRotation(heading);
    }

}
