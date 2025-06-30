package com.tp.poo.backend.model;

import java.util.Objects;

public class Rectangle extends Figure {

    private MovablePoint topLeft, bottomRight;

    private static MovablePoint promote(Point p) {
        return (p instanceof MovablePoint mp) ? mp : new MovablePoint(p);
    }

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = promote(topLeft);
        this.bottomRight = promote(bottomRight);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public Figure copy() {
        return new Rectangle(topLeft, bottomRight);
    }

    protected String stringAux() {
        return String.format("[ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public String toString() {
        return String.format("Rectángulo %s", stringAux());
    }

    @Override
    public Figure hMirror() {
        return new Rectangle(new MovablePoint(topLeft.getX(), bottomRight.getY()),
                new MovablePoint(bottomRight.getX(), bottomRight.getY() + Point.getDistance(bottomRight.getY(),
                        topLeft.getY())));
    }

    @Override
    public Figure vMirror() {
        return new Rectangle(new MovablePoint(bottomRight.getX(), topLeft.getY()),
                new MovablePoint(bottomRight.getX() + MovablePoint.getDistance(topLeft.getX(), bottomRight.getX()),
                        bottomRight.getY()));
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
        double oldYLength = Point.getDistance(topLeft.getY(), bottomRight.getY());
        double newYLength = oldYLength * magnificationRate;
        double oldXLength = Point.getDistance(topLeft.getX(), bottomRight.getX());
        double newXLength = oldXLength * magnificationRate;
        topLeft.moveD(atomicSignedGap(oldXLength, newXLength), atomicSignedGap(oldYLength, newYLength));
        bottomRight.moveD(atomicSignedGap(newXLength, oldXLength), atomicSignedGap(newYLength, oldYLength));
    }

    private static double atomicSignedGap(double a, double b) {
        return (a - b) / 2;
    }

    @Override
    public void transfer(double posX, double posY) {
        double auxX = atomicSignedGap(bottomRight.getX(), topLeft.getX());
        double auxY = atomicSignedGap(topLeft.getY(), bottomRight.getY());
        topLeft.transfer(posX - auxX, posY + auxY);
        bottomRight.transfer(posX + auxX, posY - auxY);
    }

}
