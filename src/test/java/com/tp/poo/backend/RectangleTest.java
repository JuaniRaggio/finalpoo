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
        //rectangle with topLeft:(0,-2), bottomRight:(4,2) --> center:(0,2)
        Rectangle rect = new Rectangle(new Point(0,-2), new Point(4,2));
        rect.magnify(0.5); //lo achico a la mitad

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
        // Rectángulo de (1,1) a (3,3) → centro (2,2)
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
//
//    @Test
//    void testDivision(){
//        Rectangle rect = new Rectangle(new Point(0,-2), new Point(4,2));
//
//    }

    @Test
    void testMultiplyBy4() {
        Rectangle original = new Rectangle(new Point(0, 10), new Point(10, 0));
        Set<Figure> multiplied = original.multiply(4);

        // Se generan 3 figuras nuevas (el original NO se incluye)
        assertEquals(3, multiplied.size());

        // Cada una debe estar desplazada hacia abajo y a la derecha (X+ y Y+)
        int offset = 5;

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
