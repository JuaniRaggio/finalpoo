package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomizeFigure {

    private Format format;
    private final Figure figure;

    // Las nuevas figuras tienen las mismas propiedades que las anteriores
    public CustomizeFigure(Figure figure, BorderType borderType, Color color) {
        format = new Format(color, borderType);
        this.figure = figure;
    }

    public Figure getBaseFigure() {
        return figure;
    }

    public void setColor(GraphicsContext gc) {
        changeColor((Color) gc.getFill());
    }

    public void setBorderType(BorderType borderType) {
        format.setFormat(format.getColor(), borderType);
    }

    public void changeColor(Color color){
        format.setColor(color);
    }

    public Color getOriginalColor() {
        return format.getColor();
    }

    public boolean figureBelongs(Point point) {
        return figure.isContained(point);
    }

    public void moveD(double dx, double dy) {
        figure.moveD(dx, dy);
    }

    public void format(GraphicsContext gc, CustomizeFigure selected) {
        format.applyFormat(gc, figure, selected);
    }

    @Override
    public String toString() {
        return figure.toString();
    }

}
