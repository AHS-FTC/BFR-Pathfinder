package edu.ahs.robotics.pathfinder.ui.primary;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.util.KeyManager;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication {
    private static MainApplication instance;

    private PathWindow pathWindow;
    private Environment environment;
    private SideBar sideBar;

    private Stage stage;
    private BorderPane layout = new BorderPane();

    private MainApplication(double windowSize) {
        environment = new Environment(windowSize);
        PathWindow.init(environment);
        SideBar.init(windowSize, environment);

        pathWindow = PathWindow.getInstance();
        sideBar = SideBar.getInstance();

        stage = new Stage();

        layout.setCenter(environment.getLayout());
        layout.setRight(pathWindow.getLayout());
        layout.setLeft(sideBar.getLayout());

        layout.setBottom(null);
        layout.setTop(null);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("ui/buck.css");

        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);

        scene.setOnKeyPressed(e -> KeyManager.setKeyStatus(e.getCode(), true));
        scene.setOnKeyReleased(e -> KeyManager.setKeyStatus(e.getCode(), false));
    }

    public static void init(double windowSize){
        if (instance == null){
            instance = new MainApplication(windowSize);
        }
    }

    public static Stage getStage(){
        return instance.stage;
    }
}
