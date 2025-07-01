package com.tp.poo.backend.model.figures;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

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
        return new Rectangle(topLeft.copy(), bottomRight.copy());
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
    public Figure hMirror() {
        return mirror(this,
                (figure) -> ((Rectangle) figure).moveY(Point.getDistance(((Rectangle) figure).getBottomRight().getY(),
                        ((Rectangle) figure).getTopLeft().getY())));
    }

    @Override
    public Figure vMirror() {
        return mirror(this,
                (figure) -> ((Rectangle) figure).moveX(Point.getDistance(((Rectangle) figure).getBottomRight().getX(),
                        ((Rectangle) figure).getTopLeft().getX())));
    }

    private void magnifyAndMove(Rectangle figure, int factor, Function<Point, Double> getter) {
        double backMovement = Point.getDistance(getter.apply(figure.getTopLeft()),
                getter.apply(figure.getBottomRight())) / 2.0;
        figure.magnify(1.0 / (double) factor);
        double frontMovement = Point.getDistance(getter.apply((figure.getTopLeft())),
                getter.apply(figure.getBottomRight())) / 2.0;
        figure.moveX(frontMovement - backMovement);
    }

    // "Corto la figura horizontalmente"
    @Override
    public Set<Figure> hDivision(int factor) {
        return division(this, factor,
                (figure) -> magnifyAndMove((Rectangle) figure, factor, (pt) -> pt.getY()),
                (figure) -> ((Rectangle) figure).hMirror());
    }

    // "Corto la figura verticalmente"
    @Override
    public Set<Figure> vDivision(int factor) {
        return division(this, factor,
                (figure) -> magnifyAndMove((Rectangle) figure, factor, (pt) -> pt.getX()),
                (figure) -> ((Rectangle) figure).vMirror());
    }

    @Override
    public Set<Figure> multiply(int factor) {
        return genericMultiplication(factor,
                (i, offset) -> new Rectangle(
                        new MovablePoint(topLeft.getX() + (i * offset), topLeft.getY() + (i * offset)),
                        new MovablePoint(bottomRight.getX() + (i * offset), bottomRight.getY() + (i * offset))));
    }

}
