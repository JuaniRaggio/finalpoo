package com.tp.poo.frontend.Figures;

import com.tp.poo.frontend.*;
import com.tp.poo.backend.model.figures.*;
import javafx.scene.paint.Color;

import java.util.EnumSet;

public enum CustomizeFigureBuilder {

    RECTANGLE {

        @Override
        public CustomizeFigure constructor(Point start, Point end, BorderType borderType, Color color,
                EnumSet<Effects> effects,
                EnumSet<Mirrors> mirrors) {
            return new CustomizeRectangle(start, end, borderType, color, effects, mirrors);
        }

    },
    SQUARE {

        @Override
        public CustomizeFigure constructor(Point start, Point end, BorderType borderType, Color color,
                EnumSet<Effects> effects,
                EnumSet<Mirrors> mirrors) {
            return new CustomizeSquare(start, end, borderType, color, effects, mirrors);
        }

    },
    ELLIPSE {

        @Override
        public CustomizeFigure constructor(Point start, Point end, BorderType borderType, Color color,
                EnumSet<Effects> effects,
                EnumSet<Mirrors> mirrors) {
            return new CustomizeEllipse(start, end, borderType, color, effects, mirrors);
        }

    },
    CIRCLE {

        @Override
        public CustomizeFigure constructor(Point start, Point end, BorderType borderType, Color color,
                EnumSet<Effects> effects,
                EnumSet<Mirrors> mirrors) {
            return new CustomizeCircle(start, end, borderType, color, effects, mirrors);
        }

    };

    public abstract CustomizeFigure constructor(Point start, Point end, BorderType borderType, Color color,
            EnumSet<Effects> effects,
            EnumSet<Mirrors> mirrors);

}
