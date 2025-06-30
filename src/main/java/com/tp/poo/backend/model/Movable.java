package com.tp.poo.backend.model;

public interface Movable {

    default void moveX(MovablePoint pt, double delta) {
        pt.moveX(delta);
    }

    default void moveY(MovablePoint pt, double delta) {
        pt.moveY(delta);
    }

}
