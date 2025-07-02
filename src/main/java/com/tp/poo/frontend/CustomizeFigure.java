package com.tp.poo.frontend;

import java.util.EnumSet;
import java.util.Optional;

import com.tp.poo.backend.model.figures.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomizeFigure {

    private static Color defaultColor = Color.BLACK;
    private static BorderType defaultBorderType = BorderType.SOLID;
    private Color color = defaultColor;
    private BorderType borderType = defaultBorderType;
    private final Figure figure;
    private EnumSet<Effects> filter = EnumSet.noneOf(Effects.class);

    public CustomizeFigure(Figure figure) {
        this.figure = figure;
    }

    // Las nuevas figuras tienen las mismas propiedades que las anteriores
    public CustomizeFigure(Figure figure, BorderType borderType, Color color) {
        this.figure = figure;
        this.borderType = defaultBorderType = borderType;
        this.color = defaultColor = color;
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

    public static void fill(GraphicsContext gc, Figure figure) {
        if (figure instanceof Ellipse || figure instanceof Circle)
            fill(gc, (Ellipse) figure);
        else if (figure instanceof Rectangle || figure instanceof Square)
            fill(gc, (Rectangle) figure);
    }

    public static void fill(GraphicsContext gc, Ellipse ellipse) {
        gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                ellipse.getVerticalAxis());
        gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                ellipse.getVerticalAxis());
    }

    public static void fill(GraphicsContext gc, Rectangle rectangle) {
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }

}
