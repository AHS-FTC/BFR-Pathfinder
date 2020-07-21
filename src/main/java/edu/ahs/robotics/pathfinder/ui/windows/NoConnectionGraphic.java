package edu.ahs.robotics.pathfinder.ui.windows;

import javafx.scene.image.Image;

/**
 * Handles the 'no connection' graphic utilized in VisionWindow, so that you can tell when the thread is running.
 * @see VisionWindow
 * @author Alex Appleby, team 16896
 */
public class NoConnectionGraphic {
    private static final Image[] images = new Image[4];
    private static final Image notStreamingImg;
    private static final Image timeoutImg;
    private static final int maxPhases = 12;
    private long loopStartTime;

    static {
        for (int i = 0; i < images.length; i++) {
            images[i] = new Image(NoConnectionGraphic.class.getResourceAsStream("/ui/streamgraphics/loading" + i + ".png"));
        }
        notStreamingImg = new Image(NoConnectionGraphic.class.getResourceAsStream("/ui/streamgraphics/notstreaming.png"));
        timeoutImg = new Image(NoConnectionGraphic.class.getResourceAsStream("/ui/streamgraphics/timeout.png"));

    }

    public NoConnectionGraphic() {
        this.loopStartTime = System.currentTimeMillis();
    }

    /**
     * For when there's no connecting going on
     */
    public static Image getNotStreamingImg(){
        return notStreamingImg;
    }

    /**
     * For after initTimeout
     */
    public static Image getTimeoutImg(){
        return timeoutImg;
    }



    public Image getImage(){
        int phase = (int)((System.currentTimeMillis() - loopStartTime)/100); //dangerous cast, must be int for switch

        if(phase > maxPhases){
            loopStartTime = System.currentTimeMillis();
            phase = 0;
        }

        int i;

        switch (phase){
            case 1:
            case 5:
                i = 1;
                break;
            case 2:
            case 4:
                i = 2;
                break;
            case 3:
                i = 3;
                break;
            default:
                i = 0;
        }
        return images[i];
    }
}
