package com.tp.poo.frontend;

import java.util.EnumSet;

import com.tp.poo.backend.model.figures.Ellipse;
import com.tp.poo.backend.model.figures.Figure;
import com.tp.poo.backend.model.figures.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Format {

    private final static Color selectedStrokeColor = Color.RED;
    private final static Color strokeColor = Color.BLACK;
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

//    public boolean toggleFilter(Effects filter) {
//        this.filter.add(filter);
//    }

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
            gc.setStroke(selectedStrokeColor);
        else
            gc.setStroke(strokeColor);
        gc.setFill(color);
        borderType.applyBorder(gc);
        fill(figure, gc);
    }

    public static void fill(Figure figure, GraphicsContext gc) {
        if (figure instanceof Ellipse)
            fill(gc, (Ellipse) figure);
        else if (figure instanceof Rectangle)
            fill(gc, (Rectangle) figure);
    }

    private static void fill(GraphicsContext gc, Ellipse ellipse) {
        gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                ellipse.getVerticalAxis());
        gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                ellipse.getVerticalAxis());
    }

    private static void fill(GraphicsContext gc, Rectangle rectangle) {
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }

}
