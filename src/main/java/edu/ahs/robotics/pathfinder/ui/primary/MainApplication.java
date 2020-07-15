package edu.ahs.robotics.pathfinder.ui.primary;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.path.Coordinate;
import edu.ahs.robotics.pathfinder.path.WayPoint;
import edu.ahs.robotics.pathfinder.util.KeyManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainApplication {
    private static MainApplication instance;

    private PathWindow pathWindow;
    private Environment environment;
    private SideBar sideBar;

    private Stage stage;
    private BorderPane layout = new BorderPane();

    private MainApplication(double windowSize) {
        Environment.init(windowSize);
        PathWindow.init();
        SideBar.init();

        environment = Environment.getInstance();
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

        scene.setOnKeyPressed(e -> {
            KeyManager.setKeyStatus(e.getCode(), true);
            switch (e.getCode()){
                case A:
                    scootWayPoints(-1, 0);
                    break;
                case D:
                    scootWayPoints(1, 0);
                    break;
                case W:
                    scootWayPoints(0, 1);
                    break;
                case S:
                    scootWayPoints(0, -1);
                    break;
            }
        });
        scene.setOnKeyReleased(e -> KeyManager.setKeyStatus(e.getCode(), false));

        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("/ui/b.png")));
    }

    private void scootWayPoints(double xIns, double yIns){
        ArrayList<WayPoint> wayPoints = WayPoint.getSelectedWayPoints();
        wayPoints.forEach(wayPoint -> wayPoint.scoot(xIns,yIns));
    }

    public static void init(double windowSize){
        if (instance == null){
            instance = new MainApplication(windowSize);
        }
    }

    public static Stage getStage(){
        if(instance == null) return null;
        return instance.stage;
    }
}
