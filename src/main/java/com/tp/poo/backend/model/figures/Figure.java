package com.tp.poo.backend.model.figures;

import java.util.List;

import com.tp.poo.backend.model.behaviour.*;

public abstract class Figure implements Movable, Resizeable, Mirrorable, Multiplicable, Divisible {
    private static final double OFFSET = 5.0;

    @Override
    public List<Figure> multiply(int factor) {
        return operate(this, figure -> {
            figure = figure.copy();
            figure.moveD(OFFSET, OFFSET);
            return figure;
        }, factor);
    }

    protected static void validPosition(double posX, double posY) {
        if (posX < 0 || posY < 0) {
            throw new IllegalArgumentException("Invalid position");
        }
    }

    public abstract boolean isContained(Point pt);

    public abstract String toString();

    public abstract Figure copy();

}
