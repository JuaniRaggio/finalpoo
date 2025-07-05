package com.tp.poo.frontend.Figures;

import com.tp.poo.backend.model.figures.*;
import com.tp.poo.frontend.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomizeRectangle extends CustomizeFigure {

    public CustomizeRectangle(Point start, Point end, BorderType borderType, Color color, boolean brighten,
            boolean shadow, boolean hMirror, boolean vMirror) {
        super(new Rectangle(start, end), borderType, color, brighten, shadow, hMirror, vMirror);
    }

    public CustomizeRectangle(Rectangle figure, Format format) {
       super(figure, format);
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
