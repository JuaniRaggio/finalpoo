package com.tp.poo.frontend;

import com.tp.poo.frontend.Figures.*;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ColorPicker;

public final class UIComponentFactory {

    private static void setControl(Control ctr) {
        ctr.setMinWidth(UIConstants.DEFAULT_BUTTON_MIN_WIDTH);
        ctr.setCursor(UIConstants.DEFAULT_CURSOR_STYLE);
    }

    public static Button createOperationButton(Operations operations) {
        return createOperationButton(operations.toString());
    }

    public static Button createOperationButton(String text) {
        return createFormatButton(text);
    }

    public static Button createFormatButton(String text) {
        Button button = new Button(text);
        setControl(button);
        return button;
    }

    public static CheckBox createCheckBox(Effects effect) {
        return createEffectCheckBox(effect.toString());
    }

    public static CheckBox createCheckBox(Mirrors effect) {
        return createEffectCheckBox(effect.toString());
    }

    public static CheckBox createEffectCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        setControl(checkBox);
        return checkBox;
    }

    public static ToggleButton createFigureButton(CustomizeFigureBuilder builder) {
        return createToolButton(builder.toString());
    }

    public static ToggleButton createSelectButton() {
        return createToolButton(UIConstants.SELECT_BUTTON_TEXT);
    }

    public static ToggleButton createDeleteButton() {
        return createToolButton(UIConstants.DELETE_BUTTON_TEXT);
    }

    public static ToggleButton createToolButton(String text) {
        ToggleButton button = new ToggleButton(text);
        setControl(button);
        return button;
    }

    public static ComboBox<BorderType> createBorderTypeComboBox() {
        ComboBox<BorderType> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(BorderType.values());
        comboBox.setValue(UIConstants.DEFAULT_BORDER_TYPE);
        setControl(comboBox);
        return comboBox;
    }

    public static ColorPicker createColorPicker() {
        ColorPicker colorPicker = new ColorPicker(UIConstants.DEFAULT_COLOR_PICKER);
        setControl(colorPicker);
        return colorPicker;
    }

    private UIComponentFactory() {
    }
}
