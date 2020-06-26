package edu.ahs.robotics.pathfinder.util;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.ui.primary.Sidebar;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private Sidebar sidebar;
    private Environment environment;
    private PathWindow pathWindow;

    @Override
    public void start(Stage primaryStage) throws Exception{
        double windowSize = Screen.getPrimary().getBounds().getMaxY() - 30; //offset gives room for window topbar

        Coordinate.initialize(windowSize);

        environment = new Environment(windowSize);
        pathWindow = new PathWindow(environment);
        sidebar = new Sidebar(windowSize, environment, pathWindow);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
