package com.tp.poo.backend.model.figures;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, radius * 2, radius * 2);
    }

    @Override
    public String toString() {
        return String.format("Circle [Center: %s, Radius: %.2f]", centerPoint, getRadius());
    }

    @Override
    public boolean isContained(Point pt) {
        return Point.getDistance(centerPoint, pt) < getRadius();
    }

    public double getRadius() {
        return getHorizontalAxis() / 2.0;
    }

}
