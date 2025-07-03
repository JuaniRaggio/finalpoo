package com.tp.poo.frontend;

import java.util.EnumSet;
import com.tp.poo.backend.model.figures.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Format {

    private Color color;
    private BorderType borderType;
    private EnumSet<Effects> filter = EnumSet.noneOf(Effects.class);

    public Format(Color color, BorderType borderType) {
        setFormat(color, borderType);
    }

    public void setFormat(Color color, BorderType borderType) {
        this.color = color;
        this.borderType = borderType;
    }

    public Color getColor() {
        return color;
    }

    public BorderType getBorderType() {
        return borderType;
    }

    public EnumSet<Effects> getFilters() {
        return filter;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void applyFormat(GraphicsContext gc, Figure figure, CustomizeFigure selectedFigure) {
        if (selectedFigure != null && figure == selectedFigure.getBaseFigure())
            gc.setStroke(Color.RED);
        else
            gc.setStroke(Color.BLACK);
        gc.setFill(color);
        borderType.applyBorder(gc);
        CustomizeFigure.fill(figure, gc);
    }

}
