package edu.ahs.robotics.pathfinder.ui.primary;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.ui.text.StandardText;
import edu.ahs.robotics.pathfinder.ui.text.TitleText;
import edu.ahs.robotics.pathfinder.ui.windows.Window;
import edu.ahs.robotics.pathfinder.util.Path;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PathWindow extends Window { //todo consider making major windows singletons in nature

    private static double COLOR_BOX_SIZE = 10; // in px
    private Environment environment;

    private GridPane grid;

    private Path activePath = null;
    //private ArrayList<Path> paths;

    public PathWindow(Environment environment) {
        super("Paths");
        this.environment = environment;
        //paths = environment.getPaths();
        Stage stage = new Stage();
        VBox vBox = new VBox(10);
        grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(new TitleText("Paths"));

        renderPathWindow();

        vBox.getChildren().add(grid);
        createScene(vBox);

        activePath = environment.getPath(0);

    }

    private void renderPathWindow(){
        for (int i = 0; i < environment.pathCount(); i++) {
            Path path = environment.getPath(i);
            Text text = new StandardText(path.name);

            Rectangle colorBox = new Rectangle(COLOR_BOX_SIZE, COLOR_BOX_SIZE, path.getColor());

            grid.add(text, 0, i);
            grid.add(colorBox, 1, i);
        }
    }
    public Path getActivePath(){
        return activePath;
    }
}
