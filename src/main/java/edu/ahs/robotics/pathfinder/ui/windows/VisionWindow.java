package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.environment.Field;
import edu.ahs.robotics.pathfinder.util.NetworkInterface;

import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VisionWindow extends Window{
    private final ImageView fieldView;
    private Image image;
    private VisionThread thread;

    public VisionWindow(String title) {
        super(title);

        SplitPane pane = new SplitPane();
        VBox controlLayout = new VBox();

        image = NoConnectionGraphic.getNotStreamingImage();

        fieldView = new ImageView(image);
        pane.getItems().add(fieldView);
        pane.getItems().add(controlLayout);

        controlLayout.getChildren().add(createStartButton());

        createScene(pane);
    }

    private class VisionThread extends Thread{

        @Override
        public void run(){
            while (true) {
                image = NetworkInterface.receiveTCP();
                fieldView.setImage(image);

//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
            }
        }
    }

    private Button createStartButton(){
        Button b = new Button("Connect and Stream");
        b.setOnAction(e -> {
            thread = new VisionThread();
            thread.start();
        });
        return b;
    }
}
