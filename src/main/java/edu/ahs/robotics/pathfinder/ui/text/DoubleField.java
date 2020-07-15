package edu.ahs.robotics.pathfinder.ui.text;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;

public class DoubleField extends TextField {
    private final DoubleProperty value;
    private final double min, max;
    private static final DoubleStringConverter converter = new DoubleStringConverter();


    public DoubleField(double initialValue, double min, double max){
        value = new SimpleDoubleProperty(initialValue);

        this.min = min;
        this.max = max;
        setText(initialValue + "");

        //add a change listener that updates the double property
        value.addListener((observableValue, oldVal, newVal) -> {
            if(newVal == null){
                setText("");
            } else {
                if(newVal.doubleValue() > max){
                    value.setValue(max);
                    return;
                }
                if(newVal.doubleValue() < min){
                    value.setValue(min);
                    return;
                }

                value.setValue(newVal);
                update();
            }
        });

        //add a filter that only allows numbered inputs
        addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if(!("0123456789.".contains(keyEvent.getCharacter())) || keyEvent.getCharacter().equals("")){
                keyEvent.consume();
            }
        });

        textProperty().addListener((observableValue, oldString, newString) -> {
            if (newString == null || "".equals(newString)) {
                value.setValue(0.0);
                return;
            }

            double doubleValue = 0;
            try {
                 doubleValue = converter.fromString(newString);
            } finally { }

            if(doubleValue > max){
                value.set(max);
                return;
            }

            if(doubleValue < min){
                value.set(min);
                return;
            }

            value.set(doubleValue);
            //update();

        });

    }

    public DoubleProperty getDoubleProperty(){
        return value;
    }

    private void update(){
        setText(value.get() + "");
    }
}
