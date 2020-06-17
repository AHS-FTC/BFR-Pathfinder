package edu.ahs.robotics.pathfinder.ui;

import edu.ahs.robotics.pathfinder.util.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Robot {
    private static final String IMG_PATH = "/botui/realistic_mecanum_green.png";

    private ImageView robotView;
    private static final double ROTATION_OFFSET = 90; // makes it so the front of the robot points in correct direction (in degrees)

    private Coordinate position = Coordinate.newFromPixels(0,0,0);

    private double centerOffset; // stores the number of pixels from the corner to the center for both the x and y axis

    public Robot() {
        Image image = new Image(this.getClass().getResourceAsStream(IMG_PATH));
        robotView = new ImageView(image);
        robotView.setPreserveRatio(true);
        robotView.setFitWidth(Coordinate.convertInchesToPixels(18.0));
        centerOffset = robotView.getFitWidth() / 2.0;
    }

    public ImageView getImageView(){
        return robotView;
    }

    public void setPosition(Coordinate position){
        this.position = position;
        updatePosition();
    }

    /**
     * Updates the graphical position of the robot after x or y changes, accounting for offset from corner to center.
     */
    private void updatePosition(){
        robotView.setX(position.getPixelX() - centerOffset);
        robotView.setY(position.getPixelY() - centerOffset);
    }

    public void setRotation(double angle){
        position.setHeading(angle);
        robotView.setRotate(angle);
    }

    public void pointTowards(Coordinate target){

        double angle = position.angleTo(target);

        //negative accounts for the JavaFX img rotation going clockwise on positive for some dumb reason
        setRotation(-Math.toDegrees(angle) + ROTATION_OFFSET);
    }
}
