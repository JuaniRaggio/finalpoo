package com.tp.poo.frontend;

import javafx.scene.paint.Color;

public enum Effects {

    NO_FILTER(Color.rgb(0,0,0), 0.0),
    SHADOW(Color.rgb(255, 255, 255), 0.7),
    BRIGHTENING(Color.rgb(0, 0, 0), 0.3);

    private final Color filterColor;
    private final double opacity;

    Effects(Color filterColor, double opacity) {
        this.filterColor = filterColor;
        this.opacity = opacity;
    }

    public Color applyEffect(Color color) {
        return color.interpolate(filterColor, opacity);
    }

}
