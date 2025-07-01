package com.tp.poo.backend.model.figures;

import com.tp.poo.backend.model.behaviour.Movable;

public class MovablePoint extends Point implements Movable {

    public MovablePoint(Point pt) {
        super(pt.getX(), pt.getY());
    }

    public MovablePoint(double x, double y) {
        super(x, y);
    }

    public static MovablePoint promote(Point p) {
        return (p instanceof MovablePoint mp) ? mp : new MovablePoint(p);
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

    public void moveX(double delta) {
        x += delta;
    }

    public void moveY(double delta) {
        y += delta;
    }

}
