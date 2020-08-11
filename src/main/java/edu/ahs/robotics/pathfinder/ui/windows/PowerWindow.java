package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.path.WayPoint;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.ui.text.DoubleField;
import edu.ahs.robotics.pathfinder.ui.text.StandardText;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PowerWindow extends Window{

    public PowerWindow() {
        super("Power Profiles");

        Pane pane = new Pane(); //pane contains both hBox and lines
        HBox hBox = new HBox(0);

        Path activePath = PathWindow.getInstance().getActivePath();
        Color color = activePath.getColor();
        String colorString = "#" + Integer.toHexString(color.hashCode());

        int width = 75 * activePath.getWayPoints().size();
        pane.setPrefWidth(width);
        hBox.setPrefWidth(width);

        ArrayList<Slider> sliders = new ArrayList<>();
        for (WayPoint wayPoint : activePath.getWayPoints()) {
            VBox vBox = new VBox(5);
            vBox.setAlignment(Pos.CENTER);

            Slider slider = wayPoint.getSlider();
            System.out.println(slider.getValue());
            sliders.add(slider);
            DoubleField textField = new DoubleField(slider.getValue(),0,1);

            slider.valueProperty().bindBidirectional(textField.getDoubleProperty());
            slider.setStyle("-path-color: " + colorString);
            slider.setOrientation(Orientation.VERTICAL);

            Text label = new StandardText(String.valueOf(wayPoint.getCount()));
            label.setStroke(activePath.getColor());

            vBox.getChildren().addAll(textField, slider, label);
            hBox.getChildren().add(vBox);
        }

        pane.getChildren().add(hBox);
        createScene(pane);
        stage.setResizable(false);

        //make all the lines
        for (int j = 1; j < sliders.size(); j++) {
            Slider thisSlider = sliders.get(j);
            Slider lastSlider = sliders.get(j - 1);

            Line l = new Line();
            l.setStroke(activePath.getColor());

            Node lastThumb = lastSlider.lookup(".thumb");
            Node thisThumb = thisSlider.lookup(".thumb");

            //todo ask if this causes a 'memory leak'
            thisSlider.valueProperty().addListener(e -> updateEndPoints(l, thisThumb));
            updateEndPoints(l, thisThumb);

            lastSlider.valueProperty().addListener(e -> updateStartPoints(l, lastThumb));
            updateStartPoints(l, lastThumb);


            l.setStroke(activePath.getColor());
            pane.getChildren().add(l);
        }

    }

    private void updateEndPoints(Line l, Node thisThumb) {
        Bounds newBounds = thisThumb.localToScene(thisThumb.getBoundsInLocal());
        l.setEndY(newBounds.getCenterY());
        l.setEndX(newBounds.getCenterX());
    }

    private void updateStartPoints(Line l, Node lastThumb) {
        Bounds newBounds = lastThumb.localToScene(lastThumb.getBoundsInLocal());
        l.setStartY(newBounds.getCenterY());
        l.setStartX(newBounds.getCenterX());
    }
}
