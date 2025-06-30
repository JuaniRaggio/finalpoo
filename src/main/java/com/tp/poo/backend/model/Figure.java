package com.tp.poo.backend.model;

public abstract class Figure implements Movable, Resizeable, Mirrorable {

    public Figure() {}

    public abstract String toString();

    public abstract Figure copy();

}
