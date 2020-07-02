package edu.ahs.robotics.pathfinder.ui.primary;


import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.environment.Robot;
import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.path.WayPoint;
import edu.ahs.robotics.pathfinder.ui.text.StandardText;
import edu.ahs.robotics.pathfinder.ui.text.TitleText;
import edu.ahs.robotics.pathfinder.ui.windows.NewWayPointWindow;
import edu.ahs.robotics.pathfinder.ui.windows.SetPositionWindow;
import edu.ahs.robotics.pathfinder.ui.windows.TextEntryWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * UI sidebar to compliment main pathfinder window.
 * @author Alex Appleby, team 16896
 */
public class SideBar {

    private Stage window;
    private Scene scene;
    private VBox verticalLayout;
    private HBox pathHBox = new HBox(10);

    boolean follow = true;

    private Environment environment;
    private PathWindow pathWindow;

    private static SideBar instance;

    private SideBar(double fieldWindowSize, Environment environment) {
        window = new Stage();
        window.setTitle("BFR Pathfinder UI");
        window.onCloseRequestProperty().setValue(e -> Platform.exit());

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

        verticalLayout.getChildren().add(new StandardText("Handle Ambiguous Heading via:"));
        verticalLayout.getChildren().add(makeWayPointModes());

        verticalLayout.getChildren().add(new Separator(Orientation.HORIZONTAL));

        pathHBox.getChildren().addAll(makeNewPathButton(), makeDeletePathButton());
        pathHBox.setAlignment(Pos.CENTER);

        verticalLayout.getChildren().add(pathHBox);

        verticalLayout.getChildren().add(makeRenamePathButton());

        scene = new Scene(verticalLayout,300, fieldWindowSize);
        scene.getStylesheets().add("ui/buck.css");
        window.setScene(scene);
        window.setX(0);
        window.setY(0);
        window.show();
    }

    public static void init(double fieldWindowSize, Environment environment){
        if(instance == null) {
            instance = new SideBar(fieldWindowSize, environment);
        }
    }

    public static SideBar getInstance(){
        return instance;
    }

    public boolean isFollow(){
        return follow;
    }

    private Button makeSetPositionButton(){
        Button b = new Button("Set Robot Position");
        b.setPrefWidth(200);
        b.setOnAction(e -> new SetPositionWindow(environment.getRobot()));
        return b;
    }

    private Button makeNewWayPointButton (){
        Button b = new Button("Add New Waypoint");
        b.setOnAction(e -> new NewWayPointWindow(pathWindow));
        return b;
    }

    private Button makeSetPosAsWayPointButton (){
        Button b = new Button("Set Robot Pos as Waypoint");
        Robot r = environment.getRobot();
        b.setOnAction(e -> pathWindow.getActivePath().addWayPoint(new WayPoint(r.getCoordinate(), r.getHeading())));
        return b;
    }

    private HBox makeWayPointModes(){
        HBox layout = new HBox(5);
        layout.setAlignment(Pos.CENTER);

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton followPath = new RadioButton("Follow");
        RadioButton holdPath = new RadioButton("Hold");

        followPath.setToggleGroup(toggleGroup);
        holdPath.setToggleGroup(toggleGroup);

        followPath.selectedProperty().addListener(e -> follow = true);
        holdPath.selectedProperty().addListener(e -> follow = false);

        followPath.setSelected(true);

        layout.getChildren().addAll(followPath,holdPath);
        return layout;
    }

    private Button makeNewPathButton(){
        Button b = new Button("New Path");
        b.setOnAction(e -> environment.addPath(new Path()));
        return b;
    }

    private Button makeDeletePathButton(){
        Button b = new Button("Delete Path");
        b.setOnAction(e -> environment.deletePath(pathWindow.getActivePath()));
        return b;
    }

    private Button  makeRenamePathButton(){
        Button b = new Button("Rename Active Path");
        Path activePath = PathWindow.getInstance().getActivePath();
        b.setOnAction(e -> new TextEntryWindow("Rename Active Path", "New Name: ", activePath::rename));
        return b;
    }

}
