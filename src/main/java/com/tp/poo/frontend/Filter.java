package com.tp.poo.frontend;
import com.tp.poo.backend.model.figures.*;

import javafx.scene.paint.Color;

public enum Filter {

    NO_FILTER(Color.rgb(0,0,0), 0.0),
    SHADOW(Color.rgb(0, 0, 0), 0.3),
    BRIGHTENING(Color.rgb(255, 255, 255), 0.7);

    private final Color filterColor;
    private final double opacity;

    Filter(Color filterColor, double opacity) {
        this.filterColor = filterColor;
        this.opacity = opacity;
    }

    public Color applyEffect(Color color) {
        return color.interpolate(filterColor, opacity);
    }
    
}

