package edu.ahs.robotics.pathfinder.ui.windows;

import edu.ahs.robotics.pathfinder.util.Network;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.SocketException;

public class VisionWindow extends Window{
    private final ImageView fieldView;
    private Image image;
    private VisionThread thread;

    public VisionWindow(String title) {
        super(title);

        SplitPane pane = new SplitPane();
        VBox controlLayout = new VBox(10);
        controlLayout.setAlignment(Pos.TOP_CENTER);

        image = NoConnectionGraphic.getNotStreamingImg();

        fieldView = new ImageView(image);
        pane.getItems().add(fieldView);
        pane.getItems().add(controlLayout);

        controlLayout.getChildren().add(createStartButton());
        controlLayout.getChildren().add(createStopButton());

        createScene(pane);

        stage.setOnCloseRequest(e -> { if(thread != null) thread.kill(); });
    }

    private Button createStartButton(){
        Button b = new Button("Connect and Stream");
        b.setOnAction(e -> {
            InitThread initThread = new InitThread();
            thread = new VisionThread();
            TimeoutThread timeoutThread = new TimeoutThread(initThread, 30000);

            initThread.start();
            thread.start();
            timeoutThread.start();
        });
        return b;
    }

    private Button createStopButton(){
        Button b = new Button("Stop Streaming");
        b.setOnAction(e -> {
            if(thread != null){
                thread.kill();
            }
        });
        return b;
    }

    private class VisionThread extends Thread{
        private boolean running = true;
        private boolean timeOut = false;

        @Override
        public void run(){
            while (running) {
                image = Network.receiveTCP();
                fieldView.setImage(image);
            }
            if (timeOut){
                image = NoConnectionGraphic.getTimeoutImg();
            } else {
                image = NoConnectionGraphic.getNotStreamingImg();
            }
            fieldView.setImage(image);
        }

        public void kill(){
            running = false;
        }

        public void timeOut(){
            timeOut = true;
            kill();
        }
    }

    private class InitThread extends Thread{
        @Override
        public void run(){
            try {
                Network.initTCP();
            } catch (SocketException e) {
                thread.timeOut();
            }
        }
    }

    /**
     * Manages the timeout for the InitThread
     */
    private class TimeoutThread extends Thread{
        private long timeOut;
        private InitThread thread;

        public TimeoutThread(InitThread thread, long timeOut) {
            this.thread = thread;
            this.timeOut = timeOut;
        }

        @Override
        public void run() {
            try { //sleep a given amount
                Thread.sleep(timeOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //if the thread is still alive, close the socket, triggering an exception the main vision thread.
            if(thread.isAlive()){
                Network.closeTCPSocket();
            }
        }
    }
}
