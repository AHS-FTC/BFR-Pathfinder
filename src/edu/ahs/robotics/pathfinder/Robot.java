package edu.ahs.robotics.pathfinder;

import edu.ahs.robotics.pathfinder.util.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Robot {
    private static final String IMG_PATH = "/botui/realistic_mecanum_green.png";

    private ImageView robotView;

    private double x,y;

    private final double centerOffset; // stores the number of pixels from the corner to the center for both the x and y axis
    private final double rotationOffset = 90; // makes it so the front of the robot points in correct direction (in degrees)

    public Robot() {
        Image image = new Image(this.getClass().getResourceAsStream(IMG_PATH));
        robotView = new ImageView(image);
        robotView.setPreserveRatio(true);
        robotView.setFitWidth(Coordinate.convertInchesToPixels(18.0));
        centerOffset = robotView.getFitWidth() / 2.0;
        robotView.setX(x);
        robotView.setY(y);
    }

    public ImageView getImageView(){
        return robotView;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
        updatePosition();
    }

    /**
     * Updates the position of the robot after x or y changes, accounting for offset
     */
    private void updatePosition(){
        robotView.setX(x - centerOffset);
        robotView.setY(y - centerOffset);
    }

    public void setRotation(double angle){
        robotView.setRotate(angle);
    }

    public void pointTowards(double x, double y){
        double dx = x - this.x;
        double dy = y - this.y;

        double angle = Math.atan2(dy,dx);

        setRotation(Math.toDegrees(angle) - rotationOffset);
    }
}
