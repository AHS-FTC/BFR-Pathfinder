package edu.ahs.robotics.pathfinder.ui.windows;

import javafx.scene.image.Image;

/**
 * Handles the 'no connection' graphic utilized in VisionWindow, so that you can tell when the thread is running.
 */
public class NoConnectionGraphic {
    private static final Image[] images = new Image[4];
    private static final Image notStreamingImage;
    private static final int maxPhases = 12;
    private long loopStartTime;

    static {
        for (int i = 0; i < images.length; i++) {
            images[i] = new Image(NoConnectionGraphic.class.getResourceAsStream("/ui/streamgraphics/loading" + i + ".png"));
        }
        notStreamingImage = new Image(NoConnectionGraphic.class.getResourceAsStream("/ui/streamgraphics/notstreaming.png"));
    }

    public NoConnectionGraphic() {
        this.loopStartTime = System.currentTimeMillis();
    }

    /**
     * Returns a single image, outside of the context of animation
     */
    public static Image getNotStreamingImage(){
        return notStreamingImage;
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
