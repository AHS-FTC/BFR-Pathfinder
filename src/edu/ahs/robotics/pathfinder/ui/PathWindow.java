package edu.ahs.robotics.pathfinder.ui;

import edu.ahs.robotics.pathfinder.environment.Environment;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PathWindow extends Window{

    private static double COLOR_BOX_SIZE = 10; // in px
    private Environment environment;
    //private ArrayList<Path> paths;

    public PathWindow(Environment environment) {
        super("Paths");
        this.environment = environment;
        //paths = environment.getPaths();
        Stage stage = new Stage();
        GridPane layout = new GridPane();

        //for each path in paths, add a new element
        int i = 0;
        Rectangle r = new Rectangle(COLOR_BOX_SIZE, COLOR_BOX_SIZE, Color.rgb(0,0,0));
        Text t = new Text("Path " + i);

        layout.add(r, 0, 1);

    }
}
