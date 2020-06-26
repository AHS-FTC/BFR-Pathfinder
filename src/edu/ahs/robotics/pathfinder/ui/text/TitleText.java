package edu.ahs.robotics.pathfinder.ui.text;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Fancy text with the font loaded and integrated style and everything
 */
public class TitleText extends Text {

    private static final Font font;

    static {
        font = Font.loadFont(TitleText.class.getResource("/botui/NOOA-Demi-Serif.ttf").toExternalForm(), 10);
    }

    public TitleText(String text) {
        //font only works in caps
        super(text.toUpperCase());
        setFont(font);
        getStyleClass().add("title-text");
        setTextAlignment(TextAlignment.CENTER);
    }
}
