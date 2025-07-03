package com.tp.poo.frontend;

import java.util.EnumSet;
import java.util.Optional;

import com.tp.poo.backend.model.figures.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomizeFigure {

    public static class Format {

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

        public static void applyFormat(GraphicsContext gc, CustomizeFigure selectedFigure) {
            if (selectedFigure != null && figure == selectedFigure.getBaseFigure())
            gc.setStroke(Color.RED);
            else
            gc.setStroke(Color.BLACK);
            gc.setFill(color);
            borderType.applyBorder(gc);
            CustomizeFigure.fill(gc);
        }

    }

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
