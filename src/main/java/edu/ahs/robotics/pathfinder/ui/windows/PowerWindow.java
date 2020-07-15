package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PowerWindow extends Window{
    public PowerWindow() {
        super("Power Profiles");

        HBox hBox = new HBox(10);

        //Path activePath = PathWindow.getInstance().getActivePath();
        Color color = Color.TOMATO;//activePath.getColor();

        Slider slider1 = new Slider(0,1,0.5);
        slider1.setOrientation(Orientation.VERTICAL);
        String colorString = "#" + Integer.toHexString(color.hashCode());
        slider1.setStyle("-fx-path-color: " + colorString);

        hBox.getChildren().add(slider1);
        createScene(hBox);
    }
}
