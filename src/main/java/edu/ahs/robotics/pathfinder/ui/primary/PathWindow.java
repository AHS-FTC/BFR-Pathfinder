package edu.ahs.robotics.pathfinder.ui.primary;


import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.ui.text.TitleText;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PathWindow{ //todo consider making major windows singletons in nature

    private static double COLOR_BOX_SIZE = 10; // in px
    private Environment environment;

    private GridPane grid;

    private Path activePath = null;
    private ArrayList<Path> paths;
    private Node layout;

    private ToggleGroup toggleGroup = new ToggleGroup();

    private static PathWindow instance = null;

    private PathWindow() {
        environment = Environment.getInstance();
        Stage stage = new Stage();
        stage.setMinWidth(300);
        VBox vBox = new VBox(10);
        grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(-10);
        grid.setPadding(new Insets(5, 50,5,50));

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().add(new TitleText("Paths"));
        vBox.getChildren().add(new Separator(Orientation.HORIZONTAL));

        vBox.getChildren().add(grid);

        paths = environment.getPaths();

        layout = vBox;
    }

    public Node getLayout(){
        return layout;
    }

    public void addPath(Path path){
        int index = paths.size() - 1;//todo consider removing paths arraylist

        Text text = path.getPathText();
        Rectangle colorBox = new Rectangle(COLOR_BOX_SIZE, COLOR_BOX_SIZE, path.getColor());
        RadioButton activePathButton = path.getRadioButton();
        activePathButton.setToggleGroup(toggleGroup);

        grid.add(path.getViewBox(),0, index);
        grid.add(activePathButton, 1, index);
        grid.add(text, 2, index);
        grid.add(colorBox, 3, index);
    }

    public void setActivePath(Path path){
        activePath = path;
    }

    public static void init(){
        instance = new PathWindow();
        Path newPath = new Path();
        newPath.getRadioButton().setSelected(true);
        instance.environment.addPath(newPath);
        instance.activePath = newPath;

    }

    public static PathWindow getInstance(){
        return instance;
    }


    public Path getActivePath(){
        return activePath;
    }
}
