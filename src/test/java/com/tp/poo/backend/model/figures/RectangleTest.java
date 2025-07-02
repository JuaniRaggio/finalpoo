package com.tp.poo.backend.model.figures;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    @Test
    void testMagnifySmaller() {
        Rectangle rect = new Rectangle(new Point(0, -2), new Point(4, 2));
        rect.magnify(0.5);
        Point newTopLeft = rect.getTopLeft();
        Point newBottomRight = rect.getBottomRight();
        assertEquals(1.0, newTopLeft.getX(), 0.0001); // capaz cambiar el 0.0001 con EPS..
        assertEquals(-1.0, newTopLeft.getY(), 0.0001);
        assertEquals(3.0, newBottomRight.getX(), 0.0001);
        assertEquals(1.0, newBottomRight.getY(), 0.0001);
    }

    @Test
    void testMagnifyBigger() {
        Rectangle rect = new Rectangle(new Point(1, 1), new Point(3, 3));
        rect.magnify(2.0);
        Point newTopLeft = rect.getTopLeft();
        Point newBottomRight = rect.getBottomRight();
        assertEquals(0.0, newTopLeft.getX(), 0.0001);
        assertEquals(0.0, newTopLeft.getY(), 0.0001);
        assertEquals(4.0, newBottomRight.getX(), 0.0001);
        assertEquals(4.0, newBottomRight.getY(), 0.0001);
    }

    @Test
    void testMagnifyInvalidParam() {
        Rectangle rect = new Rectangle(new Point(0, 0), new Point(1, 1));
        assertThrows(IllegalArgumentException.class, () -> rect.magnify(0.0)); // check..
        assertThrows(IllegalArgumentException.class, () -> rect.magnify(-1.0));
    }

    // Izq a Der
    @Test
    void testVerticalDivision_4Parts_shouldDivideVertically() {
        Rectangle original = new Rectangle(new Point(0, 0), new Point(40, 20));
        List<Figure> parts = original.vDivision(4);
        assertEquals(4, parts.size());
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            Point expectedTopLeft = new Point(i * 10, 7.5);
            Point expectedBottomRight = new Point((i + 1) * 10, 12.5);
            Rectangle expected = new Rectangle(expectedTopLeft, expectedBottomRight);
            for (Figure r : parts) {
                if (Double.compare(((Rectangle) r).getTopLeft().getX() , expectedTopLeft.getX()) == 0
                        && Double.compare(((Rectangle) r).getTopLeft().getY() , expectedTopLeft.getY()) == 0
                        && Double.compare(((Rectangle) r).getBottomRight().getX() , expectedBottomRight.getX()) == 0
                        && Double.compare(((Rectangle) r).getBottomRight().getY() , expectedBottomRight.getY()) == 0)
                    found = true;
                    break;
            }
            assertTrue(found, "Falta el rectángulo: " + expected);
        }
    }

    @Test
    void testHorizontalDivision_3Parts_shouldDivideHorizontal() {
        Rectangle original = new Rectangle(new Point(10, 0), new Point(30, 30));
        List<Figure> parts = original.hDivision(3);
        assertEquals(3, parts.size());
        boolean found = false;
        for (int i = 0; i < 3; i++) {
            Point expectedTopLeft = new Point(50.0 / 3.0, i * 10);
            Point expectedBottomRight = new Point(70.0 / 3.0, (i + 1) * 10);
            Rectangle expected = new Rectangle(expectedTopLeft, expectedBottomRight);

            // for (Figure r : parts) {
            //     if (Double.compare(((Rectangle) r).getTopLeft().getX() , expectedTopLeft.getX()) == 0
            //             && Double.compare(((Rectangle) r).getTopLeft().getY() , expectedTopLeft.getY()) == 0
            //             && Double.compare(((Rectangle) r).getBottomRight().getX() , expectedBottomRight.getX()) == 0
            //             && Double.compare(((Rectangle) r).getBottomRight().getY() , expectedBottomRight.getY()) == 0)
            //         found = true;
            //         break;
            // }

            assertTrue(parts.contains(expected), "Falta el rectángulo: " + expected);
        }
    }

    @Test
    void testMultiplyBy4() {
        Rectangle original = new Rectangle(new Point(0, 10), new Point(10, 0));
        List<Figure> multiplied = original.multiply(4);
        assertEquals(3, multiplied.size());
        final int offset = 5;
        for (int i = 1; i <= 3; i++) {
            double expectedTopLeftX = i * offset;
            double expectedTopLeftY = 10 + (i * offset);
            double expectedBottomRightX = 10 + (i * offset);
            double expectedBottomRightY = i * offset;
            boolean found = multiplied.stream().anyMatch(f -> {
                Rectangle r = (Rectangle) f;
                return Double.compare(r.getTopLeft().getX(), expectedTopLeftX) == 0
                && Double.compare(r.getTopLeft().getY(), expectedTopLeftY) == 0
                && Double.compare(r.getBottomRight().getX(), expectedBottomRightX) == 0
                && Double.compare(r.getBottomRight().getY(), expectedBottomRightY) == 0;
            });
            assertTrue(found, "No se encontró la figura desplazada en la posición esperada con offset " + (i * offset));
        }
    }

    // me creo los metodos auxiliares que me van a dejar calcular el centro del rect
    private static double getCenterX(Rectangle rect) {
        return Math.abs(rect.getTopLeft().getX() + rect.getBottomRight().getX()) / 2;
    }

    private static double getCenterY(Rectangle rect) {
        return Math.abs(rect.getTopLeft().getY() + rect.getBottomRight().getY()) / 2;
    }

    @Test
    void testTransfer() {
        Rectangle rect = new Rectangle(new Point(0, 0), new Point(40, 20));

        // qvq el centro de rect este en (0,0) (caso origen)
        rect.transfer(0, 0);

        assertEquals(0.0, getCenterX(rect), 0.001,
            "El centro X de la figura debería ser 0.0 después del transfer");
        assertEquals(0.0, getCenterY(rect), 0.001,
            "El centro Y de la figura debería ser 0.0 después del transfer");

        // qvq centro-->(5,3) (caso positivo)
        rect.transfer(50.0, 30.0);

        assertEquals(50.0, getCenterX(rect), 0.001, "El centro X de la figura debería ser 50.0 después del transfer");
        assertEquals(30.0, getCenterY(rect), 0.001, "El centro Y debería estar en 30.0");

        // qvq centro-->(-2.5,-4) (caso negativo) NOTE: recordar que fuera del lienzo no
        // se tiene que ver la figura
        try {
            rect.transfer(-2.5, -4.0);
        } catch (IllegalArgumentException ex) {
            ex.getMessage();
        }
    }

}
