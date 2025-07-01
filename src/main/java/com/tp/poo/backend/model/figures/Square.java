package com.tp.poo.backend.model.figures;

public class Square extends Rectangle {

    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    // TODO
    // Resize

    @Override
    public String toString() {
        return String.format("Cuadrado %s", stringAux());
    }

}
