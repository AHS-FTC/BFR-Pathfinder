package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.ui.text.StandardText;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sun.plugin.dom.html.HTMLBodyElement;

public class TextEntryWindow extends Window {
    private Path path;
    private TextField textField;

    public TextEntryWindow(String title, String fieldLabel, Path path) {
        super(title);

        this.path = path;
        textField = new TextField();
        StandardText label = new StandardText(fieldLabel);
        Button finished = new Button("Finished");
        finished.setOnAction(e -> onFinished()); //todo generalize this class

        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label, textField);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(hBox, finished);
        vBox.setAlignment(Pos.CENTER);

        vBox.setPadding(new Insets(10));

        createScene(vBox);
    }

    private void onFinished(){
        path.rename(textField.getText());
        stage.close();
    }
}
