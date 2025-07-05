package com.tp.poo.frontend.Figures;

import com.tp.poo.backend.model.figures.Point;
import javafx.scene.paint.Color;

import com.tp.poo.frontend.*;

public class CustomizeSquare extends CustomizeRectangle {

    public CustomizeSquare(Point start, Point end, BorderType borderType, Color color, boolean brighten,
                           boolean shadow, boolean hMirror, boolean vMirror) {
        super(start, end, borderType, color, brighten, shadow, hMirror, vMirror);
    }

}
