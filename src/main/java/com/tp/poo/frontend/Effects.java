package com.tp.poo.frontend;

import javafx.scene.paint.Color;

public enum Effects {

    SHADOW(UIConstants.SHADOW_BUTTON_TEXT, Color.rgb(0, 0, 0), 0.3),
    BRIGHTENING(UIConstants.BRIGHTEN_BUTTON_TEXT, Color.rgb(255, 255, 255), 0.7);

    private final Color filterColor;
    private final double opacity;
    private final String description;

    Effects(String description, Color filterColor, double opacity) {
        this.description = description;
        this.filterColor = filterColor;
        this.opacity = opacity;
    }

    public Color applyEffect(Color color) {
        return color.interpolate(filterColor, opacity);
    }

    @Override
    public String toString() {
        return description;
    }

}
