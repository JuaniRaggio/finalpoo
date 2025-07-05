package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class CustomizeSquare extends CustomizeRectangle {

    public CustomizeSquare(Point start, Point end, BorderType borderType, Color color, boolean brighten,
                           boolean shadow, boolean hMirror, boolean vMirror) {
        super(start, end, borderType, color, brighten, shadow, hMirror, vMirror);
    }

}