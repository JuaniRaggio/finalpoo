package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.*;

import javafx.scene.paint.Color;

public class CustomizeCircle extends CustomizeEllipse {

    public CustomizeCircle(Point start, Point end, BorderType borderType, Color color, boolean brighten, boolean shadow,
            boolean hMirror, boolean vMirror) {
        super(new Circle(start, Math.abs(end.getX() - start.getX())), borderType, color, brighten, shadow, hMirror,
                vMirror);
    }

}
