package com.tp.poo.backend;

import com.tp.poo.backend.model.figures.Figure;
import com.tp.poo.backend.model.figures.Point;
import com.tp.poo.backend.model.figures.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    @Test
    void testMagnifySmaller(){
        Rectangle rect = new Rectangle(new Point(0,-2), new Point(4,2));
        rect.magnify(0.5);

        // qvq que los puntos importantes "caigan" donde deben-->topLeft:(1,-1) , bottomRight:(3,1)
        Point newTopLeft = rect.getTopLeft();
        Point newBottomRight = rect.getBottomRight();

        assertEquals(1.0, newTopLeft.getX(), 0.0001); //capaz cambiar el 0.0001 con EPS..
        assertEquals(-1.0, newTopLeft.getY(), 0.0001);

        assertEquals(3.0, newBottomRight.getX(), 0.0001);
        assertEquals(1.0, newBottomRight.getY(), 0.0001);
    }

    @Test
    void testMagnifyBigger(){
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
        assertThrows(IllegalArgumentException.class, () -> rect.magnify(0.0));  //check..
        assertThrows(IllegalArgumentException.class, () -> rect.magnify(-1.0));
    }

    // Izq a Der
    @Test
    void testVerticalDivision_4Parts_shouldDivideVertically() {
        Rectangle original = new Rectangle(new Point(0, 0), new Point(40, 20));
        Set<Figure> parts = original.vDivision(4);
        assertEquals(4, parts.size());
        for (int i = 0; i < 4; i++) {
            Point expectedTopLeft = new Point(i * 10, 12.5);
            Point expectedBottomRight = new Point((i + 1) * 10, 17.5);
            Rectangle expected = new Rectangle(expectedTopLeft, expectedBottomRight);
            assertTrue(parts.contains(expected), "Falta el rectángulo: " + expected);
        }
    }
    
    @Test
    void testHorizontalDivision_3Parts_shouldDivideHorizontal() {
        Rectangle original = new Rectangle(new Point(10, 0), new Point(30, 30));
        Set<Figure> parts = original.hDivision(3);
        assertEquals(3, parts.size());
        System.out.println(parts);
        for (int i = 0; i < 3; i++) {
            Point expectedTopLeft = new Point(15 , i * 10);
            Point expectedBottomRight = new Point(25, (i + 1) * 10);
            Rectangle expected = new Rectangle(expectedTopLeft, expectedBottomRight);
            assertTrue(parts.contains(expected), "Falta el rectángulo: " + expected);
        }
    }

    @Test
    void testMultiplyBy4() {
        Rectangle original = new Rectangle(new Point(0, 10), new Point(10, 0));
        Set<Figure> multiplied = original.multiply(4);

        // Se generan 3 figuras nuevas (el original NO se incluye)
        assertEquals(3, multiplied.size());

        // Cada una debe estar desplazada hacia abajo y a la derecha (X+ y Y+)
        final int offset = 5;

        for (int i = 1; i <= 3; i++) {
            double expectedTopLeftX = i * offset; // 0 + i * offset
            double expectedTopLeftY = 10 + (i * offset);
            double expectedBottomRightX = 10 + (i * offset);
            double expectedBottomRightY = i * offset;

            boolean found = multiplied.stream().anyMatch(f -> {
                Rectangle r = (Rectangle) f;
                return r.getTopLeft().equals(new Point(expectedTopLeftX, expectedTopLeftY)) &&
                r.getBottomRight().equals(new Point(expectedBottomRightX, expectedBottomRightY));
            });

            assertTrue(found, "No se encontró la figura desplazada en la posición esperada con offset " + (i * offset));
        }
    }
}

