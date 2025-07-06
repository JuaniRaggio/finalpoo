package com.tp.poo.frontend.Figures;

import com.tp.poo.backend.model.figures.Point;
import com.tp.poo.backend.model.figures.Square;
import javafx.scene.paint.Color;

import com.tp.poo.frontend.*;

import java.util.EnumSet;

public class CustomizeSquare extends CustomizeRectangle {
    public CustomizeSquare(Point start, Point end, BorderType borderType, Color color, EnumSet<Effects> effects, EnumSet<Mirrors> mirrors) {
        super(makeSquare(start, end), borderType, color,effects, mirrors);
    }

    private static Square makeSquare(Point start, Point end) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double side = Math.min(dx, dy);
        return new Square(start, side);
    }

}
