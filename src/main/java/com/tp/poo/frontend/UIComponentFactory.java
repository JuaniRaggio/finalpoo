package com.tp.poo.frontend;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public final class UIComponentFactory {
    
    private static final int DEFAULT_BUTTON_MIN_WIDTH = 90;
    
    private static void setControl(Control ctr) {
        ctr.setMinWidth(DEFAULT_BUTTON_MIN_WIDTH);
        ctr.setCursor(Cursor.HAND);
    }

    public static CheckBox createEffectCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        setControl(checkBox);
        return checkBox;
    }
    
    public static ToggleButton createToolButton(String text) {
        ToggleButton button = new ToggleButton(text);
        setControl(button);
        return button;
    }
    
    public static Button createOperationButton(String text) {
        return createFormatButton(text);
    }
    
    public static Button createFormatButton(String text) {
        Button button = new Button(text);
        setControl(button);
        return button;
    }
    
    public static ComboBox<BorderType> createBorderTypeComboBox() {
        ComboBox<BorderType> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(BorderType.values());
        comboBox.setValue(BorderType.SOLID);
        setControl(comboBox);
        return comboBox;
    }
    
    public static ColorPicker createColorPicker() {
        ColorPicker colorPicker = new ColorPicker(Color.YELLOW);
        setControl(colorPicker);
        return colorPicker;
    }
    
    public static CheckBox createShadowCheckBox() {
        return createEffectCheckBox(UIConstants.SHADOW_BUTTON_TEXT);
    }
    
    public static CheckBox createBrightenCheckBox() {
        return createEffectCheckBox(UIConstants.BRIGHTEN_BUTTON_TEXT);
    }
    
    public static CheckBox createHorizontalMirrorCheckBox() {
        return createEffectCheckBox(UIConstants.HORIZONTAL_MIRROR_BUTTON_TEXT);
    }
    
    public static CheckBox createVerticalMirrorCheckBox() {
        return createEffectCheckBox(UIConstants.VERTICAL_MIRROR_BUTTON_TEXT);
    }
    
    public static ToggleButton createSelectButton() {
        return createToolButton(UIConstants.SELECT_BUTTON_TEXT);
    }
    
    public static ToggleButton createRectangleButton() {
        return createToolButton(UIConstants.RECTANGLE_BUTTON_TEXT);
    }
    
    public static ToggleButton createCircleButton() {
        return createToolButton(UIConstants.CIRCLE_BUTTON_TEXT);
    }
    
    public static ToggleButton createSquareButton() {
        return createToolButton(UIConstants.SQUARE_BUTTON_TEXT);
    }
    
    public static ToggleButton createEllipseButton() {
        return createToolButton(UIConstants.ELLIPSE_BUTTON_TEXT);
    }
    
    public static ToggleButton createDeleteButton() {
        return createToolButton(UIConstants.DELETE_BUTTON_TEXT);
    }
    
    public static Button createDivideHButton() {
        return createOperationButton(UIConstants.DIVIDE_H_BUTTON_TEXT);
    }
    
    public static Button createDivideVButton() {
        return createOperationButton(UIConstants.DIVIDE_V_BUTTON_TEXT);
    }
    
    public static Button createMultiplyButton() {
        return createOperationButton(UIConstants.MULTIPLY_BUTTON_TEXT);
    }
    
    public static Button createTransferButton() {
        return createOperationButton(UIConstants.TRANSFER_BUTTON_TEXT);
    }
    
    public static Button createCopyFormatButton() {
        return createFormatButton(UIConstants.COPY_FORMAT_BUTTON_TEXT);
    }
    
    public static Button createPasteFormatButton() {
        return createFormatButton(UIConstants.PASTE_FORMAT_BUTTON_TEXT);
    }

    private UIComponentFactory() {
    }
} 
