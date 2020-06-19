package edu.ahs.robotics.pathfinder.ui;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.util.WayPoint;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
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

    private Environment environment;

    public Sidebar(double fieldWindowSize, Environment environment) {
        window = new Stage();
        window.setTitle("BFR Pathfinder UI");

        this.environment = environment;

        verticalLayout = new VBox(10);
        verticalLayout.setAlignment(Pos.TOP_CENTER);

        verticalLayout.getChildren().add(makeTitle());
        verticalLayout.getChildren().add(new Separator(Orientation.HORIZONTAL));
        verticalLayout.getChildren().add(makeSetPositionButton());

        verticalLayout.getChildren().add(makeNewWayPointButton());
        verticalLayout.getChildren().add(makeSetPosAsWayPointButton());

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

    private Button makeSetPositionButton(){
        Button b = new Button("Set Robot Position");
        b.setPrefWidth(200);
        b.setOnAction(e -> new SetPositionWindow(environment.getRobot()));
        return b;
    }

    private Button makeNewWayPointButton (){
        Button b = new Button("Add New Waypoint");
        b.setOnAction(e -> new NewWayPointWindow(environment));
        return b;
    }

    private Button makeSetPosAsWayPointButton (){
        Button b = new Button("Set Robot Pos as Waypoint");
        Robot r = environment.getRobot();
        b.setOnAction(e -> environment.addWayPoint(new WayPoint(r.getCoordinate(), r.getHeading())));
        return b;
    }
}
