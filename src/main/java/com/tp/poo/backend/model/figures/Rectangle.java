package com.tp.poo.backend.model.figures;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

public class Rectangle extends Figure {

    private MovablePoint topLeft, bottomRight;

    private static MovablePoint promote(Point p) {
        return (p instanceof MovablePoint mp) ? mp : new MovablePoint(p);
    }

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = promote(topLeft);
        this.bottomRight = promote(bottomRight);
    }

    @Override
    public Set<Figure> vDivision(int factor) {
        return division(this, factor,
                (figure) -> figure.moveX(-Point.getDistance(topLeft.getX(), bottomRight.getX()) / 2
                        + Point.getDistance(((Rectangle) figure).topLeft.getY(),
                                ((Rectangle) figure).bottomRight.getX()) / 2.0),
                (figure, distance) -> figure.moveY(distance));
    }

    @Override
    public Set<Figure> hDivision(int factor) {
        return division(this, factor, (figure) -> figure.moveY(
                -Point.getDistance(topLeft.getY(), bottomRight.getY()) / 2
                        + Point.getDistance(((Rectangle) figure).topLeft.getY(),
                                ((Rectangle) figure).bottomRight.getY() / 2.0)),
                (figure, distance) -> figure.moveX(distance));
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
                new MovablePoint(bottomRight.getX() + Point.getDistance(topLeft.getX(), bottomRight.getX()),
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
        return (a - b) / 2.0;
    }

    @Override
    public void transfer(double posX, double posY) {
        double auxX = atomicSignedGap(bottomRight.getX(), topLeft.getX());
        double auxY = atomicSignedGap(topLeft.getY(), bottomRight.getY());
        topLeft.transfer(posX - auxX, posY + auxY);
        bottomRight.transfer(posX + auxX, posY - auxY);
    }

    @Override
    public Set<Figure> multiply(int factor) {
        Set<Figure> toReturn = new HashSet<>();
        toReturn.add(this);
        for(int i = 1 ; i < factor ; i++) {
            toReturn.add(new Rectangle(new MovablePoint(topLeft.getX()+i, topLeft.getY()+i),
                    new MovablePoint(bottomRight.getX()+i, bottomRight.getY()+i)));
        }
        return toReturn;
    }
}
