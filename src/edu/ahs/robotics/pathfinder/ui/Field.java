package edu.ahs.robotics.pathfinder.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Field {

    public enum Mode{
        EMPTY(""), //todo render empty field in Solidworks
        SKYSTONE("/botui/ssfieldsimple_cropped.PNG");

        String fileLoc;
        Mode(String fileLoc) {
            this.fileLoc = fileLoc;
        }
    }

    private static double scaleFactor; //the ratio of pixels to inches

    private Mode mode = Mode.SKYSTONE;

    private ImageView fieldView;

    public Field(double fieldSize) {

        Image field = new Image(this.getClass().getResourceAsStream(mode.fileLoc));
        fieldView = new javafx.scene.image.ImageView(field);
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
