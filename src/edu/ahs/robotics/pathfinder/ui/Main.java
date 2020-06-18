package edu.ahs.robotics.pathfinder.ui;

import edu.ahs.robotics.pathfinder.util.Coordinate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;
    private Scene scene;
    private BorderPane layout;

    private Field field;
    private Robot robot;


    private double windowSize;

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        layout = new BorderPane();
        windowSize = Screen.getPrimary().getBounds().getMaxY() - 30; //offset gives room for window topbar

        Coordinate.initialize(windowSize);

        field = new Field(windowSize);
        robot = new Robot();

        layout.getChildren().add(field.getImageView());
        layout.getChildren().add(robot.getImageView());

        scene = new Scene(layout,windowSize,windowSize);
        window.setScene(scene);
        window.setTitle("Black Forest Robotics Pathfinder");

        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onMouseClick);

        new Sidebar(windowSize, robot);

        window.show();
        lockWindow();
    }

    private void lockWindow() {
        double height = window.getHeight();
        double width = window.getWidth();

        window.setMinHeight(height);
        window.setMaxHeight(height);
        window.setMinWidth(width);
        window.setMaxWidth(width);
    }

    private void onMouseClick(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY){
            robot.setPosition(Coordinate.newFromPixels(e.getX(),e.getY(),0));
        } else if (e.getButton() == MouseButton.SECONDARY){
            robot.pointTowards(Coordinate.newFromPixels(e.getX(), e.getY(),0));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
