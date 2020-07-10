package edu.ahs.robotics.pathfinder.util;

import edu.ahs.robotics.pathfinder.environment.Environment;
import edu.ahs.robotics.pathfinder.path.Coordinate;
import edu.ahs.robotics.pathfinder.path.Path;
import edu.ahs.robotics.pathfinder.path.WayPoint;
import edu.ahs.robotics.pathfinder.ui.primary.PathWindow;
import edu.ahs.robotics.pathfinder.ui.windows.ErrorAlert;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    private FileWriter fileWriter;

    public void setOutputFile(String filepath){
        try{
            File file = new File(filepath);
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            ErrorAlert.throwErrorWindow(e.getClass().toGenericString(), e.getMessage());
        }
    }

    /**
     * Saves the current active path once a filepath has been selected
     */
    public void saveFile(){
        if(fileWriter == null){
            ErrorAlert.throwErrorWindow("No File Destination Set", "Set a file destination by ------------"); //todo
        } else {
            try {
                Path path = PathWindow.getInstance().getActivePath();

                ArrayList<WayPoint> wayPoints = path.getWayPoints();

                for (WayPoint w : wayPoints) {
                    Coordinate c = w.getCoordinate();
                    String line = c.getInchX() + ", " + c.getInchY() + ", " + w.getHeading() + "\n";
                    fileWriter.append(line);
                }
                fileWriter.close();

            } catch (IOException e){
                ErrorAlert.throwErrorWindow("IOException", e.getMessage());
            }
        }
    }
}
