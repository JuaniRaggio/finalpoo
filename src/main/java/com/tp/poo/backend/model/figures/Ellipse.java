package com.tp.poo.backend.model.figures;

import java.util.List;

public class Ellipse extends Figure {

    protected MovablePoint centerPoint;
    protected double verticalAxis, horizontalAxis;

    public Ellipse(Point centerPoint, double verticalAxis, double horizontalAxis) {
        this.centerPoint = MovablePoint.promote(centerPoint);
        this.verticalAxis = verticalAxis;
        this.horizontalAxis = horizontalAxis;
    }

    @Override
    public Figure copy() {
        return new Ellipse(centerPoint.copy(), verticalAxis, horizontalAxis);
    }

    @Override
    public List<Figure> vDivision(int factor) {
        return division(this, factor,
                (figure) -> {
                    figure.moveX(horizontalAxis * (1.0 / (2.0 * factor) - 1.0 / 2.0));
                    figure.magnify(1.0 / (double) factor);
                },
                (figure) -> figure.vMirror());
    }

    @Override
    public List<Figure> hDivision(int factor) {
        return division(this, factor,
                (figure) -> {
                    figure.moveY(verticalAxis * (1.0 / (2.0 * factor) - 1.0 / 2.0));
                    figure.magnify(1.0 / (double) factor);
                },
                (figure) -> figure.hMirror());
    }

    @Override
    public Figure vMirror() {
        return mirror(this, (figure) -> figure.moveX(horizontalAxis));
    }

    @Override
    public Figure hMirror() {
        return mirror(this, (figure) -> figure.moveY(verticalAxis));
    }

    @Override
    public void moveX(double delta) {
        centerPoint.moveX(delta);
    }

    @Override
    public void moveY(double delta) {
        centerPoint.moveY(delta);
    }

    private void setAxes(double vertical, double horizontal) {
        if (vertical <= 0 || horizontal <= 0) {
            throw new IllegalArgumentException("Incompatible vertical and horizontal axes");
        }
        verticalAxis = vertical;
        horizontalAxis = horizontal;
    }

    @Override
    public void magnify(double magnificationRate) {
        checkMagnificationRate(magnificationRate);
        setAxes(verticalAxis * magnificationRate, horizontalAxis * magnificationRate);
    }

    @Override
    public boolean isContained(Point pt) {
        return Double.compare((Math.pow(pt.getX() - centerPoint.getX(), 2)
                / Math.pow(horizontalAxis, 2)) +
                (Math.pow(pt.getY() - centerPoint.getY(), 2)
                        / Math.pow(verticalAxis, 2)),
                0.30) <= 0;
    }

    @Override
    public void transfer(double posX, double posY) {
        validPosition(posX, posY);
        centerPoint.transfer(posX, posY);
    }

    @Override
    public String toString() {
        return String.format("Ellipse [Center: %s, Horizontal Axis: %.2f, Vertical Axis: %.2f]", centerPoint,
                verticalAxis,
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

}
