package com.tp.poo.backend.model.figures;

public class Point {

    protected double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point copy() {
        return new Point(x, y);
    }

    public boolean isBetween(Point other, Point a) {
        return isBetween(other.x, a.x) && isBetween(other.y, a.y);
    }

    public boolean isBetween(double other, double a) {
        return Double.compare(x, a) <= 0 && Double.compare(a, other) <= 0;
    }

    public static double getDistance(double a, double b) {
        return Math.abs(a - b);
    }

    public static double squaredDifference(double a, double b) {
        return Math.pow(a - b, 2);
    }

    public static double getDistance(Point a, Point b) {
        return Math.sqrt(squaredDifference(a.getX(), b.getX()) + squaredDifference(a.getY(), b.getY()));
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

}
