package com.tp.poo.backend.model.figures;

import java.util.List;

import com.tp.poo.backend.model.behaviour.*;

public abstract class Figure implements Movable, Resizeable, Mirrorable, Multiplicable, Divisible {

    public Figure() {
    }

    @Override
    public List<Figure> multiply(int factor) {
        double offset = 5.0;
        return operate(this, figure -> {
            figure = figure.copy();
            figure.moveD(offset, offset);
            return figure;
        }, factor);
    }

    public abstract boolean isContained(Point pt);

    public abstract String toString();

    public abstract Figure copy();

}
