package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.ui.primary.MainApplication;
import edu.ahs.robotics.pathfinder.ui.text.StandardText;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * An abstract popup window that prompts for an x position, a y position, and a heading.
 * @author Alex Appleby, team 16896
 */
public abstract class XYHeadingWindow extends Window {


    /**
     * @param title The title of the stage.
     */
    protected XYHeadingWindow(String title) {
        super(title);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setVgap(20);
        layout.setHgap(20);

        Text xText = new StandardText("X position (in):");
        Text yText = new StandardText("Y position (in):");
        Text headingText = new StandardText("Heading (deg):");

        TextField xField = new TextField();
        TextField yField = new TextField();
        TextField headingField = new TextField();

        Button apply = new Button("Apply");
        Button finish = new Button("Finish");

        apply.setOnAction(e -> apply(xField.getText(), yField.getText(), headingField.getText()));
        finish.setOnAction(e -> finish(xField.getText(), yField.getText(), headingField.getText()));


        layout.add(xText,0,0);
        layout.add(yText,0,1);
        layout.add(headingText,0,2);

        layout.add(xField, 1, 0);
        layout.add(yField, 1, 1);
        layout.add(headingField, 1, 2);

        layout.add(apply, 0,3);
        layout.add(finish, 1,3);

        createScene(layout);
    }

    /**
     * Validates string values from text fields and converts them to doubles, then echos abstract apply method.
     * Also converts heading to radians.
     */
    private void apply(String x, String y, String heading){
        try {
            double x_ = Double.valueOf(x);
            double y_ = Double.valueOf(y);
            double heading_ = Math.toRadians(Double.valueOf(heading));

            apply(x_,y_,heading_);

        } catch (NumberFormatException e){
            ErrorAlert.throwErrorWindow("NumberFormatException", "A text field couldn't be converted to a number.");
        }
    }
    
    public abstract void apply(double x, double y, double heading);

    /**
     * Finish method calls apply while closing the window afterwards.
     */
    private void finish(String x, String y, String heading){
        apply(x, y, heading);
        stage.close();
    }
}
