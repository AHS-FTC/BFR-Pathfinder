package edu.ahs.robotics.pathfinder.util;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.path.Coordinate;
import edu.ahs.robotics.pathfinder.ui.primary.MainApplication;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.ui.primary.SideBar;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private SideBar sidebar;
    private Environment environment;

    @Override
    public void start(Stage primaryStage) throws Exception{
        double windowSize = Screen.getPrimary().getBounds().getMaxY() - 30; //offset gives room for window topbar

        Coordinate.initialize(windowSize);

        MainApplication.init(windowSize);

//        environment = new Environment(windowSize);
//        PathWindow.init(environment);
//        SideBar.init(windowSize, environment);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
