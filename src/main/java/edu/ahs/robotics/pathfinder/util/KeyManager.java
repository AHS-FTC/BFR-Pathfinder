package edu.ahs.robotics.pathfinder.util;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

/**
 * Manages which keys are pressed
 * @author Alex Appleby, team 16896
 */
public class KeyManager {

    private static HashMap<KeyCode, Boolean> keyMap = new HashMap<>();

    static {
    }

    private KeyManager() {}

    public static void setKeyStatus(KeyCode keyCode, boolean isPressed){
        keyMap.put(keyCode, isPressed);
    }

    public static boolean isPressed(KeyCode keyCode){
        Boolean isPressed = keyMap.get(keyCode);
        if(isPressed != null){
            return isPressed;
        }
        return false;
    }

}
