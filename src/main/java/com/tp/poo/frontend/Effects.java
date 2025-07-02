package com.tp.poo.frontend;

import javafx.scene.paint.Color;

public enum Effects {

    SHADOW(Color.rgb(255, 255, 255), 0.7),
    BRIGHTENING(Color.rgb(0, 0, 0), 0.3);

    private final Color color;
    private final double opacity;

    Effects(Color color, double opacity) {
        this.color = color;
        this.opacity = opacity;
    }

    public void applyEffect(CustomizeFigure figure) {
        figure.getOriginalColor().interpolate(color, opacity);
    }

}
