package com.tp.poo.frontend;

import java.util.EnumSet;

import com.tp.poo.backend.model.figures.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomizeFigure {

    private final Figure figure;
    private Color color;
    private EnumSet<Effects> filter;
    private BorderType borderType;

    public CustomizeFigure(Figure figure){
        this.figure = figure;
    }

    public void setColor(GraphicsContext gc){
        this.color = (Color) gc.getFill();
    }

    public Color getOriginalColor(){
        return color;
    }

}
