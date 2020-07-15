package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.path.WayPoint;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.ui.text.DoubleField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;

public class PowerWindow extends Window{

    public PowerWindow() {
        super("Power Profiles");

        HBox hBox = new HBox(10);

        Path activePath = PathWindow.getInstance().getActivePath();
        Color color = activePath.getColor();
        String colorString = "#" + Integer.toHexString(color.hashCode());

        for (WayPoint wayPoint : activePath.getWayPoints()) {
            //DoubleProperty sliderValue = new SimpleDoubleProperty();

            VBox vBox = new VBox();

            Slider s = wayPoint.getSlider();
            DoubleField textField = new DoubleField(0,0,1);

            s.valueProperty().bindBidirectional(textField.getDoubleProperty());



            s.setStyle("-path-color: " + colorString);
            s.setOrientation(Orientation.VERTICAL);

            vBox.getChildren().addAll(textField,s);
            hBox.getChildren().add(vBox);
        }

        createScene(hBox);
    }
}
