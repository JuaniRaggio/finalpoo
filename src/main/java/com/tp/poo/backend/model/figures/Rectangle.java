package com.tp.poo.backend.model.figures;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Rectangle extends Figure {

    private MovablePoint topLeft, bottomRight;

    private static MovablePoint promote(Point p) {
        return (p instanceof MovablePoint mp) ? mp : new MovablePoint(p);
    }

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = promote(topLeft);
        this.bottomRight = promote(bottomRight);
    }

    private Set<Figure> division(int factor, BiConsumer<Point, Point> movement) {
        checkFactor(factor);
        Set<Figure> returnSet = new HashSet<>();
        Point tlAux = topLeft;
        Point brAux = bottomRight;
        this.magnify(1.0 / factor);
        movement.accept(tlAux, brAux);
        returnSet.add(this);
        for (int i = 1; i < factor; ++i) {
            Figure toAdd = this.copy();
            toAdd.moveX(1 / factor * (i + 1));
            returnSet.add(toAdd);
        }
        return returnSet;
    }

    @Override
    public Set<Figure> vDivision(int factor) {
        return division(factor, (tlAux, brAux) -> this.moveX(-Point.getDistance(tlAux.getX(), brAux.getX()) / 2
                + Point.getDistance(this.topLeft.x, this.bottomRight.x) / 2));
    }

    @Override
    public Set<Figure> hDivision(int factor) {
        return division(factor, (tlAux, brAux) -> this.moveY(-Point.getDistance(tlAux.getY(), brAux.getY()) / 2
                + Point.getDistance(this.topLeft.y, this.bottomRight.y) / 2));
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
