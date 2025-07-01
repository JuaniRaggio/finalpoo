package com.tp.poo.backend.model.figures;

import com.tp.poo.backend.model.behaviour.Movable;
import com.tp.poo.backend.model.behaviour.Resizeable;
import com.tp.poo.backend.model.behaviour.Operable;

public abstract class Figure implements Movable, Resizeable, Operable {

    public Figure() {}

    public abstract String toString();

    public abstract Figure copy();

}
