package com.tp.poo.backend.model;

public class MovablePoint extends Point implements Movable {

    public MovablePoint(Point pt) {
        super(pt.getX(), pt.getY());
    }

    private static void validPosition(double posX, double posY) {
        if(posX < 0 || posY < 0) {
            throw new IllegalArgumentException("Invalid position");
        }
    }

    @Override
    public void transfer(double posX, double posY) {
        validPosition(posX, posY);
        this.x = posX;
        this.y = posY;
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
