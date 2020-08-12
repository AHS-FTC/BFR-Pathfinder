package edu.ahs.robotics.pathfinder.ui.text;

import javafx.scene.text.Text;

public class StandardText extends Text {
    public StandardText(String text) {
        super(text);
        getStyleClass().add("standard-text");
    }
    public StandardText() {
        this("");
    }
}
