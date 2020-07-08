package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.ui.primary.MainApplication;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Just throws a standardized error window
 */
public class ErrorAlert {

    private ErrorAlert(){}

    public static void throwErrorWindow(String header, String content){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.initOwner(MainApplication.getStage());

        error.setHeaderText(header);
        error.setContentText(content);

        Stage stage = (Stage) error.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        error.showAndWait();
    }
}
