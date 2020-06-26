package edu.ahs.robotics.pathfinder.ui.primary;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.environment.Robot;
import edu.ahs.robotics.pathfinder.ui.text.TitleText;
import edu.ahs.robotics.pathfinder.ui.windows.NewWayPointWindow;
import edu.ahs.robotics.pathfinder.ui.windows.SetPositionWindow;
import edu.ahs.robotics.pathfinder.util.Path;
import edu.ahs.robotics.pathfinder.util.WayPoint;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * UI sidebar to compliment main pathfinder window.
 * @author Alex Appleby, team 16896
 */
public class Sidebar {

    private Stage window;
    private Scene scene;
    private VBox verticalLayout;

    private Environment environment;
    private PathWindow pathWindow;

    public Sidebar(double fieldWindowSize, Environment environment) {
        window = new Stage();
        window.setTitle("BFR Pathfinder UI");

        this.environment = environment;
        this.pathWindow  = PathWindow.getInstance();

        verticalLayout = new VBox(10);
        verticalLayout.setAlignment(Pos.TOP_CENTER);

        verticalLayout.getChildren().add(new TitleText("BFR Pathfinder UI"));

        verticalLayout.getChildren().add(new Separator(Orientation.HORIZONTAL));

        verticalLayout.getChildren().add(makeSetPositionButton());

        verticalLayout.getChildren().add(new Separator(Orientation.HORIZONTAL));

        verticalLayout.getChildren().add(makeNewWayPointButton());
        verticalLayout.getChildren().add(makeSetPosAsWayPointButton());

        verticalLayout.getChildren().add(new Separator(Orientation.HORIZONTAL));

        verticalLayout.getChildren().add(makeNewPathButton());

        scene = new Scene(verticalLayout,300, fieldWindowSize);
        scene.getStylesheets().add("ui/buck.css");
        window.setScene(scene);
        window.setX(0);
        window.setY(0);
        window.show();
    }

    private Button makeSetPositionButton(){
        Button b = new Button("Set Robot Position");
        b.setPrefWidth(200);
        b.setOnAction(e -> new SetPositionWindow(environment.getRobot()));
        return b;
    }

    private Button makeNewWayPointButton (){
        Button b = new Button("Add New Waypoint");
        b.setOnAction(e -> new NewWayPointWindow(environment, pathWindow));
        return b;
    }

    private Button makeSetPosAsWayPointButton (){
        Button b = new Button("Set Robot Pos as Waypoint");
        Robot r = environment.getRobot();
        b.setOnAction(e -> pathWindow.getActivePath().addWayPoint(new WayPoint(r.getCoordinate(), r.getHeading())));
        return b;
    }

    private Button makeNewPathButton(){
        Button b = new Button("New Path");
        b.setOnAction(e -> environment.addPath(new Path()));
        return b;
    }

}
