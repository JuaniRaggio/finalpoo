package com.tp.poo.backend.model;

public class MovablePoint extends Point implements Movable {

    public MovablePoint(Point pt) {
        super(pt.getX(), pt.getY());
    }

    public MovablePoint(double x, double y) {
        super(x, y);
    }

    public void moveX(double delta) {
        x += delta;
    }

    public void moveY(double delta) {
        y += delta;
    }

}
