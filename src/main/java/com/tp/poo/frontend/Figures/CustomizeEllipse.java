package com.tp.poo.frontend.Figures;

import com.tp.poo.frontend.*;
import com.tp.poo.backend.model.figures.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomizeEllipse extends CustomizeFigure {

    public CustomizeEllipse(Ellipse figure, BorderType borderType, Color color, boolean brighten, boolean shadow, boolean hMirror, boolean vMirror) {
        super(figure, borderType, color, brighten, shadow, hMirror, vMirror);
    }

    public CustomizeEllipse(Point start, Point end, BorderType borderType, Color color, boolean brighten, boolean shadow, boolean hMirror, boolean vMirror) {
        this(new Ellipse(new Point(
            Math.abs(end.getX() + start.getX()) / 2,
            Math.abs(end.getY() + start.getY()) / 2
        ), Math.abs(end.getY() - start.getY()), Math.abs(end.getX() - start.getX())), borderType, color, brighten, shadow, hMirror, vMirror);
    }

    public CustomizeEllipse(Ellipse figure, Format format) {
        super(figure, format);
    }

    @Override
    protected CustomizeFigure getCopy(Figure figure, Format format) {
        return new CustomizeEllipse((Ellipse) figure, format);
    }

    @Override
    public void fill(GraphicsContext gc) {
        Ellipse fig = (Ellipse) figure;
        gc.strokeOval(fig.getCenterPoint().getX() - (fig.getHorizontalAxis() / 2),
            fig.getCenterPoint().getY() - (fig.getVerticalAxis() / 2), fig.getHorizontalAxis(),
            fig.getVerticalAxis());
        gc.fillOval(fig.getCenterPoint().getX() - (fig.getHorizontalAxis() / 2),
            fig.getCenterPoint().getY() - (fig.getVerticalAxis() / 2), fig.getHorizontalAxis(),
            fig.getVerticalAxis());
    }
    
}
