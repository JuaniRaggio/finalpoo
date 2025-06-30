package com.tp.poo.backend.model;

public interface Movable {

    default void moveD(double deltaX, double deltaY) {
        moveX(deltaX);
        moveY(deltaY);
    }

    void moveX(double delta);

    void moveY(double delta);

}
