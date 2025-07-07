package com.tp.poo.frontend.Figures;

import com.tp.poo.frontend.*;
import com.tp.poo.backend.model.figures.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.EnumSet;

public class CustomizeEllipse extends CustomizeFigure {

    public CustomizeEllipse(Figure figure, BorderType borderType, Color color, EnumSet<Effects> effects,
                            EnumSet<Mirrors> mirrors) {
        super(figure, borderType, color, effects, mirrors);
    }

    public CustomizeEllipse(Point start, Point end, BorderType borderType, Color color, EnumSet<Effects> effects,
                            EnumSet<Mirrors> mirrors) {
        this(new Ellipse(new Point(
            Math.abs(end.getX() + start.getX()) / 2,
            Math.abs(end.getY() + start.getY()) / 2
        ), Math.abs(end.getY() - start.getY()), Math.abs(end.getX() - start.getX())), borderType, color, effects, mirrors);
    }

    public CustomizeEllipse(Ellipse figure, Format format) {
        super(figure, format);
    }

    @Override
    protected CustomizeFigure getCopy(Figure figure, Format format) {
        return new CustomizeEllipse((Ellipse) figure, format);
    }

    @Override
    public void fill(Figure figure, GraphicsContext gc) {
        Ellipse fig = (Ellipse) figure;
        gc.strokeOval(fig.getCenterPoint().getX() - (fig.getHorizontalAxis() / 2),
            fig.getCenterPoint().getY() - (fig.getVerticalAxis() / 2), fig.getHorizontalAxis(),
            fig.getVerticalAxis());
        gc.fillOval(fig.getCenterPoint().getX() - (fig.getHorizontalAxis() / 2),
            fig.getCenterPoint().getY() - (fig.getVerticalAxis() / 2), fig.getHorizontalAxis(),
            fig.getVerticalAxis());
    }
    
}
