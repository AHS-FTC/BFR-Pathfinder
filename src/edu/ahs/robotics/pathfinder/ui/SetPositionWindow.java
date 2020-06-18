package edu.ahs.robotics.pathfinder.ui;

import edu.ahs.robotics.pathfinder.util.Coordinate;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SetPositionWindow {

    private Robot robot;

    public SetPositionWindow(Robot robot) {
        this.robot = robot;

        Stage stage = new Stage();
        stage.setTitle("Set Robot Position");

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setVgap(20);
        layout.setHgap(20);

        Text xText = new Text("X position (in):");
        xText.getStyleClass().add("standard-text");
        Text yText = new Text("Y position (in):");
        yText.getStyleClass().add("standard-text");
        Text headingText = new Text("Heading (deg):");
        headingText.getStyleClass().add("standard-text");

        TextField xField = new TextField();
        TextField yField = new TextField();
        TextField headingField = new TextField();

        Button apply = new Button("Apply");
        Button finish = new Button("Finish");

        apply.setOnAction(e -> apply(xField.getText(), yField.getText(), headingField.getText()));

        layout.add(xText,0,0);
        layout.add(yText,0,1);
        layout.add(headingText,0,2);

        layout.add(xField, 1, 0);
        layout.add(yField, 1, 1);
        layout.add(headingField, 1, 2);

        layout.add(apply, 0,3);
        layout.add(finish, 1,3);


        Scene scene = new Scene(layout);
        scene.getStylesheets().add("botui/buck.css");

        stage.setScene(scene);
        stage.show();
    }

    public void apply(String x_, String y_, String heading_){

        try{
            double x, y, heading;
            x = Double.valueOf(x_);
            y = Double.valueOf(y_);
            heading = Math.toRadians(Double.valueOf(heading_));

            robot.setPosition(Coordinate.newFromInches(x,y,heading));
        } catch (Exception e){
            System.out.println(); //todo find a better solution to this, like an error dialogue
        }
    }
}
