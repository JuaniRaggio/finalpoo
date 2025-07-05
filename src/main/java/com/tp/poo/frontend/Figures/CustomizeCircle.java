package com.tp.poo.frontend.Figures;

import com.tp.poo.backend.model.figures.*;

import com.tp.poo.frontend.BorderType;
import com.tp.poo.frontend.Effects;
import com.tp.poo.frontend.Mirrors;
import javafx.scene.paint.Color;

import java.util.EnumSet;

public class CustomizeCircle extends CustomizeEllipse {

    public CustomizeCircle(Point start, Point end, BorderType borderType, Color color, EnumSet<Effects> effects,
                           EnumSet<Mirrors> mirrors) {
        super(new Circle(start, Math.abs(end.getX() - start.getX())), borderType, color, effects,mirrors);
    }

}
