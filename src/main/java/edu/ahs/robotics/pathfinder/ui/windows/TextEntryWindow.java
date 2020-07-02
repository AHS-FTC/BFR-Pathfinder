package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.ui.text.StandardText;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TextEntryWindow extends Window {
    private Path path;
    private TextField textField;

    public TextEntryWindow(String title, String fieldLabel, TextHandler textHandler) {
        super(title);

        this.path = path;
        textField = new TextField();
        StandardText label = new StandardText(fieldLabel);
        Button finished = new Button("Finished");
        finished.setOnAction(e -> onFinished(textHandler)); //todo generalize this class

        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label, textField);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(hBox, finished);
        vBox.setAlignment(Pos.CENTER);

        vBox.setPadding(new Insets(10));

        createScene(vBox);
    }

    private void onFinished(TextHandler th){
        th.handleText(textField.getText());
        stage.close();
    }

    public interface TextHandler {
        void handleText(String s);
    }
}
