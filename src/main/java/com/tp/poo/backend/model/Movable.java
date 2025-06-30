package com.tp.poo.backend.model;

public interface Movable {

    void moveX(double delta);

    void moveY(double delta);

    default void moveD(double delta) {
        moveX(delta);
        moveY(delta);
    }

}
