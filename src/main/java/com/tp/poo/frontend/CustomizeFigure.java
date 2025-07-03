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
        format.setColor((Color) gc.getFill());
    }

    public boolean figureBelongs(Point point) {
        return figure.isContained(point);
    }

    public void moveD(double dx, double dy) {
        figure.moveD(dx, dy);
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

    @Override
    public String toString() {
        return figure.toString();
    }

}
