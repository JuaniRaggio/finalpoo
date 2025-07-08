package com.tp.poo.frontend;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;

public final class UIConstants {
    public static final double DEFAULT_CANVAS_WIDTH = 800;
    public static final double DEFAULT_CANVAS_HEIGHT = 600;
    public static final int DEFAULT_HORIZONTAL_SPACING = 10;
    public static final int DEFAULT_VERTICAL_SPACING = DEFAULT_HORIZONTAL_SPACING;
    public static final int DEFAULT_PADDING = 5;
    public static final int DEFAULT_EFFECTS_PADDING_LEFT = 120;
    public static final int DEFAULT_EFFECTS_BAR_HEIGHT = 20;
    public static final int DEFAULT_SIDEBAR_WIDTH = 100;
    public static final int DEFAULT_BUTTON_MIN_WIDTH = 90;

    public static final int DEFAULT_FIGURE_LINE_WIDTH = 1;

    public static final Cursor DEFAULT_CURSOR_STYLE = Cursor.HAND;

    public static final Color DEFAULT_COLOR_PICKER = Color.YELLOW;
    public static final Color DEFAULT_STROKE_COLOR = Color.BLACK;
    public static final Color DEFAULT_SELECTED_STROKE_COLOR = Color.RED;

    public static final BorderType DEFAULT_BORDER_TYPE = BorderType.SOLID;

    public static final String SELECTED_LOG_TEXT = "Selected: ";

    public static final String SELECT_BUTTON_TEXT = "Select";
    public static final String RECTANGLE_BUTTON_TEXT = "Rectangle";
    public static final String CIRCLE_BUTTON_TEXT = "Circle";
    public static final String SQUARE_BUTTON_TEXT = "Square";
    public static final String ELLIPSE_BUTTON_TEXT = "Ellipse";
    public static final String DELETE_BUTTON_TEXT = "Erase";
    
    public static final String DIVIDE_H_BUTTON_TEXT = "Divide H.";
    public static final String DIVIDE_V_BUTTON_TEXT = "Divide V.";
    public static final String MULTIPLY_BUTTON_TEXT = "Multiply";
    public static final String TRANSFER_BUTTON_TEXT = "Transfer";
    public static final String ASKING_FOR_N_TEXT = "Enter value for N:";
    public static final String ASKING_FOR_COORDS_TEXT = "Enter coordinates (x, y):";
    
    public static final String COPY_FORMAT_BUTTON_TEXT = "Copy format";
    public static final String PASTE_FORMAT_BUTTON_TEXT = "Paste format";
    
    public static final String SHADOW_BUTTON_TEXT = "Darken";
    public static final String BRIGHTEN_BUTTON_TEXT = "Brighten";
    public static final String HORIZONTAL_MIRROR_BUTTON_TEXT = "Horizontal Mirror";
    public static final String VERTICAL_MIRROR_BUTTON_TEXT = "Vertical Mirror";
    
    public static final String EFFECTS_LABEL_TEXT = "Effects:";
    public static final String OPERATIONS_LABEL_TEXT = "Operations:";
    
    public static final String SIDEBAR_STYLE = "-fx-background-color: #999";
    public static final String EFFECTS_BAR_STYLE = "-fx-background-color: #999;";
    
    public static final String NO_FIGURE_FOUND_MESSAGE = "No figure found";
    public static final String NOT_AN_INTEGER_MESSAGE = "Not an integer";
    public static final String INVALID_AMOUNT_OF_PARAMETERS_MESSAGE = "Invalid amount of parameters";
    public static final String INVALID_FACTOR_MESSAGE = "Invalid factor value";
    public static final String INVALID_MAGNIFICATION_RATE_MESSAGE = "Invalid magnification rate";
    public static final String EMPTY_CLIPBOARD_MESSAGE = "Empty clipboard";
    
    private UIConstants() {
    }
} 
