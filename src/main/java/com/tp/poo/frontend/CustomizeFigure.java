package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomizeFigure {
    private final Figure figure;
    private Color originalColor;
    private Color currentColor;
    private BorderType borderType;
    public CustomizeFigure(Figure figure){
        this.figure = figure;
    }
    public void getOriginalColor(GraphicsContext gc){
        this.originalColor = (Color) gc.getFill();
    }
    public Color getOriginalColor(){
        return originalColor;
    }

}
