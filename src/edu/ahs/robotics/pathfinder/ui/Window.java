package edu.ahs.robotics.pathfinder.ui;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {
    protected Stage stage;

    //scene is initialized by the subclass because JavaFX bad. Upon error, make sure createScene is called.
    private Scene scene;

    public Window(String title) {
        stage = new Stage();
        stage.setTitle(title);
    }

    /**
     * instantiates the scene with proper styling
     * @param root the parent layout of the scene
     */
    protected void createScene(Parent root){
        scene = new Scene(root);
        scene.getStylesheets().add("botui/buck.css");
        stage.setScene(scene);
        stage.show();
    }

}
