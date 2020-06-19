package edu.ahs.robotics.pathfinder.ui;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.util.Coordinate;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private Sidebar sidebar;
    private Environment environment;

    @Override
    public void start(Stage primaryStage) throws Exception{
        double windowSize = Screen.getPrimary().getBounds().getMaxY() - 30; //offset gives room for window topbar

        environment = new Environment(windowSize);
        sidebar = new Sidebar(windowSize, environment);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
