package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.*;
import java.util.function.BiFunction;

public enum FigureType {
    RECTANGLE((start, end) -> new Rectangle(start, end)),
    CIRCLE((start, end) -> new Circle(start, Math.abs(end.getX() - start.getX()))),
    SQUARE((start, end) -> new Square(start, Math.abs(end.getX() - start.getX()))),
    ELLIPSE((start, end) -> {
        Point centerPoint = new Point(
            Math.abs(end.getX() + start.getX()) / 2,
            Math.abs(end.getY() + start.getY()) / 2
        );
        double horizontalAxis = Math.abs(end.getX() - start.getX());
        double verticalAxis = Math.abs(end.getY() - start.getY());
        return new Ellipse(centerPoint, verticalAxis, horizontalAxis);
    });

    private final BiFunction<Point, Point, Figure> figureCreator;

    FigureType(BiFunction<Point, Point, Figure> figureCreator) {
        this.figureCreator = figureCreator;
    }

    public Figure createFigure(Point start, Point end) {
        return figureCreator.apply(start, end);
    }
} 