package edu.ahs.robotics.pathfinder.environment;

import edu.ahs.robotics.pathfinder.path.Coordinate;
import edu.ahs.robotics.pathfinder.path.WayPoint;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.util.KeyManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;


public class Field {

    public enum Mode{
        EMPTY("/ui/emptyfield.png"), //todo make empty field render straight
        SKYSTONE("/ui/ssfieldsimple_cropped.PNG");

        String fileLoc;
        Mode(String fileLoc) {
            this.fileLoc = fileLoc;
        }
    }

    private static double scaleFactor; //the ratio of pixels to inches

    private static final Mode MODE = Mode.SKYSTONE;

    private ImageView fieldView;
    private Robot robot;

    public Field(double fieldSize, Robot robot) {
        this.robot = robot;
        Image field = new Image(this.getClass().getResourceAsStream(MODE.fileLoc));
        fieldView = new ImageView(field);
        fieldView.setPreserveRatio(true);
        fieldView.setFitWidth(fieldSize);
        fieldView.onMouseClickedProperty().setValue(this::onMouseClick);
    }

    private void onMouseClick(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY){
            WayPoint.deselectAll();
            if (KeyManager.isPressed(KeyCode.CONTROL)) {

                Coordinate c = Coordinate.newFromPixels(e.getX(), e.getY());

                WayPoint w = new WayPoint(c);
                PathWindow.getInstance().getActivePath().addWayPoint(w);

            } else {
                robot.setPosition(Coordinate.newFromPixels(e.getX(), e.getY()));
            }
        } else if (e.getButton() == MouseButton.SECONDARY){
            robot.pointTowards(Coordinate.newFromPixels(e.getX(), e.getY()));
        }
    }

    /**
     * Returns a formatted image of the field for the background
     */
    public ImageView getImageView(){
        return fieldView;
    }
}
