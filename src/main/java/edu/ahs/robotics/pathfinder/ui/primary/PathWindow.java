package edu.ahs.robotics.pathfinder.ui.primary;


import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.ui.text.StandardText;
import edu.ahs.robotics.pathfinder.ui.text.TitleText;
import edu.ahs.robotics.pathfinder.ui.windows.Window;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PathWindow extends Window { //todo consider making major windows singletons in nature

    private static double COLOR_BOX_SIZE = 10; // in px
    private Environment environment;

    private GridPane grid;

    private Path activePath = null;
    private ArrayList<Path> paths;

    private ToggleGroup toggleGroup = new ToggleGroup();

    private static PathWindow instance = null;

    private PathWindow(Environment environment) {
        super("Paths");
        this.environment = environment;
        //paths = environment.getPaths();
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

        createScene(vBox);

        paths = environment.getPaths();
    }

    public void addPath(Path path){
        int index = paths.size() - 1;//todo consider removing paths arraylist

        Text text = new StandardText(path.name);
        Rectangle colorBox = new Rectangle(COLOR_BOX_SIZE, COLOR_BOX_SIZE, path.getColor());
        RadioButton activePathButton = path.getRadioButton();
        activePathButton.setToggleGroup(toggleGroup);

        grid.add(path.getViewBox(),0, index);
        grid.add(activePathButton, 1, index);
        grid.add(text, 2, index);
        grid.add(colorBox, 3, index);
        stage.sizeToScene();
    }

    public void setActivePath(Path path){
        activePath = path;
        if(activePath != null){
            path.enableRadioButton();
        }
    }

    public static void init(Environment e){
        instance = new PathWindow(e);
        Path newPath = new Path();
        newPath.getRadioButton().setSelected(true);
        instance.environment.addPath(newPath);
        instance.activePath = newPath;

        double x = Screen.getPrimary().getBounds().getMaxX() - instance.stage.getWidth();

        instance.stage.setX(x);
        instance.stage.setY(0);
    }

    public static PathWindow getInstance(){
        return instance;
    }


    public Path getActivePath(){
        return activePath;
    }
}
