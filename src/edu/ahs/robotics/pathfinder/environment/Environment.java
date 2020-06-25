package edu.ahs.robotics.pathfinder.environment;

import edu.ahs.robotics.pathfinder.util.Coordinate;
import edu.ahs.robotics.pathfinder.util.Path;
import edu.ahs.robotics.pathfinder.util.WayPoint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Class that represents the virtual environment containing the field, the robot, etc.
 * @author Alex Appleby, team 16896
 */
public class Environment {
    private Stage window = new Stage();
    private Scene scene;
    private Group layout = new Group();
    private Field field;
    private Robot robot = new Robot();
    private Path path = new Path();

    public Environment(double windowSize) {
        field = new Field(windowSize);

        layout.getChildren().add(field.getImageView());
        layout.getChildren().add(robot.getImageView());
        layout.getChildren().add(path.getGraphics());

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
        path.addWayPoint(w);
    }

}
