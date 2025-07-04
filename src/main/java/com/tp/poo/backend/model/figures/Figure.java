package com.tp.poo.backend.model.figures;

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

    public abstract boolean isContained(Point pt);

    public abstract String toString();

    public abstract Figure copy();

}
