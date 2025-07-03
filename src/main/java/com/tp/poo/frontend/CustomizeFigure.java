package com.tp.poo.frontend;

import java.util.EnumSet;
import java.util.Optional;

import com.tp.poo.backend.model.figures.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomizeFigure {

    private Color color;
    private BorderType borderType;
    private final Figure figure;
    private EnumSet<Effects> filter = EnumSet.noneOf(Effects.class);

    public CustomizeFigure(Figure figure, Color color) {
        this.figure = figure;
        this.color = color;
    }

    // Las nuevas figuras tienen las mismas propiedades que las anteriores
    public CustomizeFigure(Figure figure, BorderType borderType, Color color) {
        this.figure = figure;
        this.borderType = borderType;
        this.color = color;
    }

    public Figure getBaseFigure() {
        return figure;
    }

    public void setColor(GraphicsContext gc) {
        this.color = (Color) gc.getFill();
    }

    public Color getOriginalColor() {
        return color;
    }

    public boolean figureBelongs(Point point) {
        return figure.isContained(point);
    }

    public void moveD(double dx, double dy) {
        figure.moveD(dx, dy);
    }

    public void format(GraphicsContext gc, CustomizeFigure selectedFigure) {
        if (selectedFigure != null && figure == selectedFigure.getBaseFigure())
            gc.setStroke(Color.RED);
        else
            gc.setStroke(Color.BLACK);
        gc.setFill(color);
        borderType.applyBorder(gc);
        fill(gc);
    }

    public void fill(GraphicsContext gc) {
        if (figure instanceof Ellipse)
            fill(gc, (Ellipse) figure);
        else if (figure instanceof Rectangle)
            fill(gc, (Rectangle) figure);
    }

    private void fill(GraphicsContext gc, Ellipse ellipse) {
        gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                ellipse.getVerticalAxis());
        gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                ellipse.getVerticalAxis());
    }

    private void fill(GraphicsContext gc, Rectangle rectangle) {
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
