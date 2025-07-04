package com.tp.poo.backend.model.figures;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.tp.poo.backend.model.behaviour.*;

public abstract class Figure implements Movable, Resizeable, Mirrorable, Multiplicable, Divisible {

    public Figure() {
    }

    // Fijarse si es el mejor lugar para poner esta funcion
    public static void checkFactor(int factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Invalid factor value");
        }
    }

    @Override
    public List<Figure> multiply(int factor) {
        double offset = 5.0;
        return operate(this, figure -> figure.moveD(offset, offset), factor);
    }

    public abstract boolean isContained(Point pt);

    public abstract String toString();

    public abstract Figure copy();

}
