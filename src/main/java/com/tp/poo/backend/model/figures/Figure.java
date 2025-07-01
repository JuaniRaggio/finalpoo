package com.tp.poo.backend.model.figures;

import com.tp.poo.backend.model.behaviour.Mirrorable;
import com.tp.poo.backend.model.behaviour.Movable;
import com.tp.poo.backend.model.behaviour.Resizeable;
import com.tp.poo.backend.model.behaviour.Operable;

public abstract class Figure implements Movable, Resizeable, Mirrorable, Operable {

    public Figure() {}

    // Fijarse si es el mejor lugar para poner esta funcion
    public static void checkFactor(int factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Invalid factor value");
        }
    }

    public abstract String toString();

    public abstract Figure copy();

}
