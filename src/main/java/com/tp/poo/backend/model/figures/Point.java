package com.tp.poo.backend.model.figures;

import java.util.Objects;

public class Point {

    protected double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double getDistance(double a, double b) {
        return Math.abs(a - b);
    }

    public static double squaredDiference(double a, double b) {
        return Math.pow(a - b, 2);
    }

    public static double getDistance(Point a, Point b) {
        return Math.sqrt(squaredDiference(a.getX(), b.getX()) + squaredDiference(a.getY(), b.getY()));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Point pt &&
        Double.compare(pt.x, this.x) == 0 &&
        Double.compare(pt.y, this.y) == 0;
    }

}
