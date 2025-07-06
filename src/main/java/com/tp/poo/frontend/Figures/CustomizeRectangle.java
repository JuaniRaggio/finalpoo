package com.tp.poo.frontend.Figures;

import com.tp.poo.backend.model.figures.*;
import com.tp.poo.frontend.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.EnumSet;

public class CustomizeRectangle extends CustomizeFigure {

    public CustomizeRectangle(Point start, Point end, BorderType borderType, Color color, EnumSet<Effects> effects,
                              EnumSet<Mirrors> mirrors) {
        super(new Rectangle(start, end), borderType, color, effects, mirrors);
    }

    public CustomizeRectangle(Rectangle figure, Format format) {
       super(figure, format);
    }

    public CustomizeRectangle(Figure figure, BorderType borderType, Color color, EnumSet<Effects> effects,
                              EnumSet<Mirrors> mirrors) {
        super(figure, borderType, color, effects, mirrors);
    }

    public void fill(GraphicsContext gc) {
        Rectangle rectangle = (Rectangle) figure;
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }

    @Override
    protected CustomizeFigure getCopy(Figure figure, Format format) {
         return new CustomizeRectangle((Rectangle) figure, format);
    }
}
