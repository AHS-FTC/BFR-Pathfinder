package edu.ahs.robotics.pathfinder.ui;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * UI sidebar to compliment main pathfinder window.
 * @author Alex Appleby, team 16896
 */
public class Sidebar {

    private Stage window;
    private Scene scene;
    private VBox verticalLayout;

    public Sidebar(double fieldWindowSize) {
        window = new Stage();
        window.setTitle("BFR Pathfinder UI");

        verticalLayout = new VBox();


        verticalLayout.getChildren().add(makeTitle());
        verticalLayout.getChildren().add(new Separator(Orientation.HORIZONTAL));
        verticalLayout.getChildren().add(new Button("useless button"));

        scene = new Scene(verticalLayout,300, fieldWindowSize);
        scene.getStylesheets().add("botui/buck.css");
        window.setScene(scene);
        window.setX(0);
        window.setY(0);
        window.show();
    }

    private Text makeTitle(){
        Text t = new Text("BFR PATHFINDER UI");
        t.getStyleClass().add("title-text");
        t.setFont(Font.loadFont(getClass().getResource("/botui/NOOA-Demi-Serif.ttf").toExternalForm(), 10));
        return t;
    }
}
