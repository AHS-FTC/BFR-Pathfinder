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

    public Environment(double windowSize) {
        field = new Field(windowSize);

        layout.getChildren().add(field.getImageView());
        layout.getChildren().add(robot.getImageView());

        layout.prefHeight(windowSize);
        layout.prefWidth(windowSize);

//        window.setScene(scene);
//        window.setTitle("Black Forest Robotics Pathfinder");
//        window.onCloseRequestProperty().setValue(e -> Platform.exit());

        layout.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onMouseClick);

        //window.show();
        //lockWindow();
    }

    public Node getLayout(){
        return layout;
    }

    private void onMouseClick(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY){
            //presumably a Polygon or Circle and thus a waypoint, but there may be a better way to do this
            if (!(e.getTarget() instanceof Shape)) { //if this is true, WayPoint handles the MouseEvent
                if (KeyManager.isPressed(KeyCode.CONTROL)) {

                        Coordinate c = Coordinate.newFromPixels(e.getX(), e.getY());

                        WayPoint w = new WayPoint(c);
                        PathWindow.getInstance().getActivePath().addWayPoint(w);

                    } else {
                        robot.setPosition(Coordinate.newFromPixels(e.getX(), e.getY()));
                    }
            }
        } else if (e.getButton() == MouseButton.SECONDARY){
            robot.pointTowards(Coordinate.newFromPixels(e.getX(), e.getY()));
        }
    }

//    private void lockWindow() {
//        double height = window.getHeight();
//        double width = window.getWidth();
//
//        window.setMinHeight(height);
//        window.setMaxHeight(height);
//        window.setMinWidth(width);
//        window.setMaxWidth(width);
//    }

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