package edu.ahs.robotics.pathfinder.environment;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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

    public Field(double fieldSize) {

        Image field = new Image(this.getClass().getResourceAsStream(MODE.fileLoc));
        fieldView = new ImageView(field);
        fieldView.setPreserveRatio(true);
        fieldView.setFitWidth(fieldSize);
    }

    /**
     * Returns a formatted image of the field for the background
     */
    public ImageView getImageView(){
        return fieldView;
    }
}
