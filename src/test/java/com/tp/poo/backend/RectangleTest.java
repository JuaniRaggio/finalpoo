package com.tp.poo.backend;

import com.tp.poo.backend.model.Point;
import com.tp.poo.backend.model.Rectangle;
import org.junit.jupiter.api.Test;

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
        Rectangle rect = new Rectangle(new Point(1, 3), new Point(3, 1));

        rect.magnify(2.0); // Duplico

        Point newTopLeft = rect.getTopLeft();
        Point newBottomRight = rect.getBottomRight();

        assertEquals(0.0, newTopLeft.getX(), 0.0001);
        assertEquals(4.0, newTopLeft.getY(), 0.0001);

        assertEquals(4.0, newBottomRight.getX(), 0.0001);
        assertEquals(4.0, newBottomRight.getY(), 0.0001);
    }

    @Test
    void testMagnifyInvalidParam() {
        Rectangle rect = new Rectangle(new Point(0, 0), new Point(1, 1));
        assertThrows(IllegalArgumentException.class, () -> rect.magnify(0.0));  //check..
        assertThrows(IllegalArgumentException.class, () -> rect.magnify(-1.0));
    }
}
