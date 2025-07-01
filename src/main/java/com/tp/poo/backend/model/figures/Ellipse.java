package com.tp.poo.backend.model.figures;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Ellipse extends Figure {

    protected MovablePoint centerPoint;
    protected double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = new MovablePoint(centerPoint);
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public Figure copy() {
        return new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Ellipse o &&
                o.centerPoint.equals(this.centerPoint) &&
                Double.compare(o.sMayorAxis, this.sMayorAxis) == 0 &&
                Double.compare(o.sMinorAxis, this.sMinorAxis) == 0;
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
        sMayorAxis = mayor;
        sMinorAxis = minor;
    }

    @Override
    public void magnify(double magnificationRate) {
        checkMagnificationRate(magnificationRate);
        setAxes(sMayorAxis * magnificationRate, sMinorAxis * magnificationRate);
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public void transfer(double posX, double posY) {
        centerPoint.transfer(posX,posY);
    }

}
