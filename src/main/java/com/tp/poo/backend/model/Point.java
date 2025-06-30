package com.tp.poo.backend.model;

public class Point {

    protected double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double getDistance(double a, double b) {
        return Math.pow(a - b, 2);
    }

    public static double getDistance(Point a, Point b) {
        return Math.sqrt(getDistance(a.getX(), b.getX()) + getDistance(a.getY(), b.getY()));
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
