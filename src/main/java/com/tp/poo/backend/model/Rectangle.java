package com.tp.poo.backend.model;

import java.util.Objects;

public class Rectangle extends Figure {

    private MovablePoint topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = new MovablePoint(topLeft);
        this.bottomRight = new MovablePoint(bottomRight);
    }

    @Override
    public Figure copy() {
        return new Rectangle(topLeft, bottomRight);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    protected String stringAux() {
        return String.format("[ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public String toString() {
        return String.format("Rectángulo %s", stringAux());
    }

    @Override
    public void moveX(double delta) {
        topLeft.moveX(delta);
        bottomRight.moveX(delta);
    }

    @Override
    public void moveY(double delta) {
        topLeft.moveY(delta);
        bottomRight.moveY(delta);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Rectangle o &&
                o.getTopLeft().equals(this.topLeft) &&
                o.getBottomRight().equals(this.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, bottomRight);
    }

    @Override
    public void magnify(double magnificationRate) {
        checkMagnificationRate(magnificationRate);

    }

    private void setBottomRight() {

    }

}
