package com.tp.poo.backend.model.figures;

import java.util.Objects;
import java.util.Set;

public class Ellipse extends Figure {

    protected MovablePoint centerPoint;
    protected double verticalAxis, horizontalAxis;

    public Ellipse(Point centerPoint, double verticalAxis, double horizontalAxis) {
        this.centerPoint = new MovablePoint(centerPoint);
        this.verticalAxis = verticalAxis;
        this.horizontalAxis = horizontalAxis;
    }

    @Override
    public Figure copy() {
        return new Ellipse(centerPoint.copy(), verticalAxis, horizontalAxis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerPoint, verticalAxis, horizontalAxis);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Ellipse o &&
                o.centerPoint.equals(this.centerPoint) &&
                Double.compare(o.verticalAxis, this.verticalAxis) == 0 &&
                Double.compare(o.horizontalAxis, this.horizontalAxis) == 0;
    }

    @Override
    public Set<Figure> vDivision(int factor) {
        return division(this, factor,
                (figure) -> ((Ellipse) figure).moveY(verticalAxis * (1.0 / factor - 1.0 / 2.0)),
                (figure) -> ((Ellipse) figure).vMirror());
    }

    @Override
    public Set<Figure> hDivision(int factor) {
        return division(this, factor,
                (figure) -> ((Ellipse) figure).moveX(horizontalAxis * (1.0 / factor - 1.0 / 2.0)),
                (figure) -> ((Ellipse) figure).hMirror());
    }

    @Override
    public Figure vMirror() {
        return mirror(this, (figure) -> ((Ellipse) figure).moveX(horizontalAxis));
    }

    @Override
    public Figure hMirror() {
        return mirror(this, (figure) -> ((Ellipse) figure).moveY(-verticalAxis));
    }

    @Override
    public void moveX(double delta) {
        centerPoint.moveX(delta);
    }

    @Override
    public void moveY(double delta) {
        centerPoint.moveY(delta);
    }

    private void setAxes(double mayor, double minor) {
        if (mayor <= minor || mayor <= 0 || minor <= 0) {
            throw new IllegalArgumentException("Incompatible mayor and minor axes");
        }
        verticalAxis = mayor;
        horizontalAxis = minor;
    }

    @Override
    public void magnify(double magnificationRate) {
        checkMagnificationRate(magnificationRate);
        setAxes(verticalAxis * magnificationRate, horizontalAxis * magnificationRate);
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, verticalAxis,
            horizontalAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getVerticalAxis() {
        return verticalAxis;
    }

    public double getHorizontalAxis() {
        return horizontalAxis;
    }

    @Override
    public void transfer(double posX, double posY) {
        centerPoint.transfer(posX, posY);
    }

    @Override
    public Set<Figure> multiply(int factor) {
        return genericMultiplication(factor,
        (i, offset) -> new Ellipse(
                new MovablePoint(centerPoint.getX() + (i * offset), centerPoint.getY() + (i * offset)),
                verticalAxis, horizontalAxis));
    }

}
