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

/**
 *                                                ________ ____  ____  _____
 *                                               <  / ___/( __ )/ __ \/ ___/
 *                                               / / __ \/ __  / /_/ / __ \
 *                                              / / /_/ / /_/ /\__, / /_/ /
 *                                             /_/\____/\____//____/\____/
 *
 *     ____  __    ___   ________ __    __________  ____  _________________   ____  ____  ____  ____  _____________________
 *    / __ )/ /   /   | / ____/ //_/   / ____/ __ \/ __ \/ ____/ ___/_  __/  / __ \/ __ \/ __ )/ __ \/_  __/  _/ ____/ ___/
 *   / __  / /   / /| |/ /   / ,<     / /_  / / / / /_/ / __/  \__ \ / /    / /_/ / / / / __  / / / / / /  / // /    \__ \
 *  / /_/ / /___/ ___ / /___/ /| |   / __/ / /_/ / _, _/ /___ ___/ // /    / _, _/ /_/ / /_/ / /_/ / / / _/ // /___ ___/ /
 * /_____/_____/_/  |_\____/_/ |_|  /_/    \____/_/ |_/_____//____//_/    /_/ |_|\____/_____/\____/ /_/ /___/\____//____/
 *
 *                                                 FEARTHEBUCK
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        double windowSize = Screen.getPrimary().getBounds().getMaxY() - 30; //offset gives room for window topbar

        Coordinate.initialize(windowSize);

        MainApplication.init(windowSize);


    }



    public static void main(String[] args) {
        launch(args);
    }
}
