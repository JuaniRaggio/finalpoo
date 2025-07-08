package com.tp.poo.frontend;

import javafx.scene.control.CheckBox;

import java.util.Map;

public interface VisualManager<E extends Enum<E>> extends Map<E, CheckBox> {

    default void initialize(){
        for (E visual : this.keySet()) {
            CheckBox checkBox = createCheckBox(visual.toString());
            this.put(visual, checkBox);
        }
    }

    private CheckBox createCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setMinWidth(UIConstants.DEFAULT_BUTTON_MIN_WIDTH);
        checkBox.setCursor(UIConstants.DEFAULT_CURSOR_STYLE);
        return checkBox;
    }
}
