package com.tp.poo.frontend.Figures;

import com.tp.poo.frontend.*;
import com.tp.poo.backend.model.figures.*;
import javafx.scene.paint.Color;

public enum CustomizeFigureBuilder {

    RECTANGLE {

        @Override
        public CustomizeFigure constructor(Point start, Point end, BorderType borderType,
            Color color,
            boolean brighten, boolean shadow,
            boolean hMirror, boolean vMirror) {
            return new CustomizeRectangle(start, end, borderType, color, brighten, shadow, hMirror, vMirror);
        }

    },
    SQUARE {

        @Override
        public CustomizeFigure constructor(Point start, Point end, BorderType borderType,
            Color color,
            boolean brighten, boolean shadow,
            boolean hMirror, boolean vMirror) {
            return new CustomizeSquare(start, end, borderType, color, brighten, shadow, hMirror, vMirror);
        }

    },
    ELLIPSE {

        @Override
        public CustomizeFigure constructor(Point start, Point end, BorderType borderType, Color color, boolean brighten,
            boolean shadow, boolean hMirror, boolean vMirror) {
            return new CustomizeEllipse(start, end, borderType, color, brighten, shadow, hMirror, vMirror);
        }

    },
    CIRCLE {

        @Override
        public CustomizeFigure constructor(Point start, Point end, BorderType borderType, Color color, boolean brighten,
            boolean shadow, boolean hMirror, boolean vMirror) {
            return new CustomizeCircle(start, end, borderType, color, brighten, shadow, hMirror, vMirror);
        }

    };

    public abstract CustomizeFigure constructor(Point start, Point end, BorderType borderType,
            Color color,
            boolean brighten, boolean shadow,
            boolean hMirror, boolean vMirror);

    // public CustomizeFigureBuilder withFigure(Figure figure) {
    //     this.figure = figure;
    //     return this;
    // }
    //
    // public CustomizeFigureBuilder withBorderType(BorderType borderType) {
    //     this.borderType = borderType;
    //     return this;
    // }
    //
    // public CustomizeFigureBuilder withColor(Color color) {
    //     this.color = color;
    //     return this;
    // }
    //
    // public CustomizeFigureBuilder withEffect(Effects effect) {
    //     this.effects.add(effect);
    //     return this;
    // }
    //
    // public CustomizeFigureBuilder withEffects(EnumSet<Effects> effects) {
    //     this.effects = EnumSet.copyOf(effects);
    //     return this;
    // }
    //
    // public CustomizeFigureBuilder withHorizontalMirror(boolean enabled) {
    //     this.hMirror = enabled;
    //     return this;
    // }
    //
    // public CustomizeFigureBuilder withVerticalMirror(boolean enabled) {
    //     this.vMirror = enabled;
    //     return this;
    // }
    //
    // public CustomizeFigureBuilder withBrightening(boolean enabled) {
    //     if (enabled) {
    //         this.effects.add(Effects.BRIGHTENING);
    //     } else {
    //         this.effects.remove(Effects.BRIGHTENING);
    //     }
    //     return this;
    // }
    //
    // public CustomizeFigureBuilder withShadow(boolean enabled) {
    //     if (enabled) {
    //         this.effects.add(Effects.SHADOW);
    //     } else {
    //         this.effects.remove(Effects.SHADOW);
    //     }
    //     return this;
    // }
    //
    // public CustomizeFigure build() {
    //     if (figure == null) {
    //         throw new IllegalStateException("Figure must be set before building");
    //     }
    // }
    //
    // public static CustomizeFigureBuilder create() {
    //     return new CustomizeFigureBuilder();
    // }

}
