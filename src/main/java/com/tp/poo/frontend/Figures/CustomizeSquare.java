package com.tp.poo.frontend.Figures;

import com.tp.poo.backend.model.figures.Point;
import javafx.scene.paint.Color;

import com.tp.poo.frontend.*;

import java.util.EnumSet;

public class CustomizeSquare extends CustomizeRectangle {

    public CustomizeSquare(Point start, Point end, BorderType borderType, Color color, EnumSet<Effects> effects,
                           EnumSet<Mirrors> mirrors) {
        super(start, end, borderType, color,effects, mirrors);
    }

}
