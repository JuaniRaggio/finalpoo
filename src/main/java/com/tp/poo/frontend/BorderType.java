package com.tp.poo.frontend;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineCap;

public enum BorderType {

    SOLID("Solid", null, 1, null), PIXELATED("Pixelated", StrokeLineCap.BUTT, 5, 1, 1),
    DOTTED_THIN("Dotted Thin", StrokeLineCap.ROUND, 1, 2, 6),
    DOTTED_COMPLEX("Dotted Complex", StrokeLineCap.SQUARE, 3, 25, 10, 15, 10);

    private final String description;
    private final StrokeLineCap strokeLineCap;
    private final double pxWidth;
    private final double[] dashes;

    private BorderType(String description, StrokeLineCap strokeLineCap, double pxWidth, double... dashes) {
        this.description = description;
        this.strokeLineCap = strokeLineCap;
        this.pxWidth = pxWidth;
        this.dashes = dashes;
    }

    public void applyBorder(GraphicsContext gc) {
        gc.setLineWidth(pxWidth);
        gc.setLineCap(strokeLineCap);
        gc.setLineDashes(dashes);
    }

    @Override
    public String toString() {
        return description;
    }

}
