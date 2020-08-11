package edu.ahs.robotics.pathfinder.environment;

import edu.ahs.robotics.pathfinder.path.WayPoint;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.path.Coordinate;
import edu.ahs.robotics.pathfinder.util.KeyManager;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Class that represents the virtual environment containing the field, the robot, etc.
 * @author Alex Appleby, team 16896
 */
public class Environment {
    //private Stage window = new Stage();
    private Group layout = new Group();
    private Field field;
    private Robot robot = new Robot();
    private ArrayList<Path> paths = new ArrayList();

    private static Environment instance;

    private Environment(double windowSize) {
        field = new Field(windowSize, robot);

        layout.getChildren().add(field.getImageView());
        layout.getChildren().add(robot.getImageView());

        layout.prefHeight(windowSize);
        layout.prefWidth(windowSize);
    }

    public static void init(double windowSize){
        if(instance == null){
            instance = new Environment(windowSize);
        }
    }

    public static Environment getInstance(){
        return instance;
    }

    public Node getLayout(){
        return layout;
    }

    public Robot getRobot(){
        return robot;
    }

    public void addPath(Path path){
        paths.add(path);
        layout.getChildren().add(path.getGraphics());
        PathWindow.getInstance().addPath(path);
        path.makeActivePath();
    }

    public void deletePath(Path path){
        int i = paths.indexOf(path);
        paths.remove(i);

        //try to set another path as active
        try{
           PathWindow.getInstance().setActivePath(paths.get(i + 1));
        } catch (IndexOutOfBoundsException e){
            try {
                PathWindow.getInstance().setActivePath(paths.get(i + 1));
            } catch (IndexOutOfBoundsException e1){
                PathWindow.getInstance().setActivePath(null);
            }
        }
    }

    public ArrayList<Path> getPaths(){
        return paths;
    }
}