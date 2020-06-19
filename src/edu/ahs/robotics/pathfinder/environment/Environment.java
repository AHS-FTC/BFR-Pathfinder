package edu.ahs.robotics.pathfinder.environment;

import edu.ahs.robotics.pathfinder.ui.Field;
import edu.ahs.robotics.pathfinder.ui.Robot;
import edu.ahs.robotics.pathfinder.ui.Sidebar;
import edu.ahs.robotics.pathfinder.util.Coordinate;
import edu.ahs.robotics.pathfinder.util.WayPoint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Class that represents the virtual environment containing the field, the robot, etc.
 * @author Alex Appleby, team 16896
 */
public class Environment {
    private Stage window;
    private Scene scene;
    private Group layout;
    private Field field;
    private Robot robot;

    public Environment(double windowSize) {
        window = new Stage();
        layout = new Group();

        Coordinate.initialize(windowSize);

        field = new Field(windowSize);
        robot = new Robot();

        layout.getChildren().add(field.getImageView());
        layout.getChildren().add(robot.getImageView());

        scene = new Scene(layout,windowSize,windowSize);
        window.setScene(scene);
        window.setTitle("Black Forest Robotics Pathfinder");

        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onMouseClick);


        window.show();
        lockWindow();
    }

    private void onMouseClick(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY){
            robot.setPosition(Coordinate.newFromPixels(e.getX(),e.getY()));
        } else if (e.getButton() == MouseButton.SECONDARY){
            robot.pointTowards(Coordinate.newFromPixels(e.getX(), e.getY()));
        }
    }

    private void lockWindow() {
        double height = window.getHeight();
        double width = window.getWidth();

        window.setMinHeight(height);
        window.setMaxHeight(height);
        window.setMinWidth(width);
        window.setMaxWidth(width);
    }

    public Robot getRobot(){
        return robot;
    }

    public void addWayPoint(WayPoint w){
        layout.getChildren().add(w.getGraphic());
    }

}
