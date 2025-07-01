package com.tp.poo.backend.model.figures;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
        return new Ellipse(centerPoint, verticalAxis, horizontalAxis);
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

    private Set<Figure> division(int factor, Consumer<Point> movement) {
        checkFactor(factor);

    }

    @Override
    public Set<Figure> vDivision(int factor) {
        checkFactor(factor);
    }

    @Override
    public Set<Figure> hDivision(int factor) {
        checkFactor(factor);
        Set<Figure> returnSet = new HashSet<>();
        // double oldCeil
        // Base case
        this.magnify(1.0/factor);
        returnSet.add(this);
        for (int i = 0; i < factor - 1; ++i) {
            returnSet.add();
        }
        return returnSet;
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
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, verticalAxis, horizontalAxis);
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
        centerPoint.transfer(posX,posY);
    }

}
