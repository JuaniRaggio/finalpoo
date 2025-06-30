package com.tp.poo.backend.model;

public class RMCircle extends Circle implements Movable, Resizeable {

    public RMCircle(Point centerPoint, double radius) {
        super(new MovablePoint(centerPoint), radius);
    }

    public void magnify(double magnificationRate) {
    }
    
}
