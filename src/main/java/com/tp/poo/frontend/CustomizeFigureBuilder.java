package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.Figure;
import javafx.scene.paint.Color;
import java.util.EnumSet;

public class CustomizeFigureBuilder {
    private Figure figure;
    private BorderType borderType = BorderType.SOLID;
    private Color color = Color.YELLOW;
    private EnumSet<Effects> effects = EnumSet.noneOf(Effects.class);
    private boolean hMirror = false;
    private boolean vMirror = false;
    
    public CustomizeFigureBuilder withFigure(Figure figure) {
        this.figure = figure;
        return this;
    }
    
    public CustomizeFigureBuilder withBorderType(BorderType borderType) {
        this.borderType = borderType;
        return this;
    }
    
    public CustomizeFigureBuilder withColor(Color color) {
        this.color = color;
        return this;
    }
    
    public CustomizeFigureBuilder withEffect(Effects effect) {
        this.effects.add(effect);
        return this;
    }
    
    public CustomizeFigureBuilder withEffects(EnumSet<Effects> effects) {
        this.effects = EnumSet.copyOf(effects);
        return this;
    }
    
    public CustomizeFigureBuilder withHorizontalMirror(boolean enabled) {
        this.hMirror = enabled;
        return this;
    }
    
    public CustomizeFigureBuilder withVerticalMirror(boolean enabled) {
        this.vMirror = enabled;
        return this;
    }
    
    public CustomizeFigureBuilder withBrightening(boolean enabled) {
        if (enabled) {
            this.effects.add(Effects.BRIGHTENING);
        } else {
            this.effects.remove(Effects.BRIGHTENING);
        }
        return this;
    }
    
    public CustomizeFigureBuilder withShadow(boolean enabled) {
        if (enabled) {
            this.effects.add(Effects.SHADOW);
        } else {
            this.effects.remove(Effects.SHADOW);
        }
        return this;
    }
    
    public CustomizeFigure build() {
        if (figure == null) {
            throw new IllegalStateException("Figure must be set before building");
        }
        
        return new CustomizeFigure(figure, borderType, color,
                effects.contains(Effects.BRIGHTENING),
                effects.contains(Effects.SHADOW),
                hMirror, vMirror);
    }
    
    public static CustomizeFigureBuilder create() {
        return new CustomizeFigureBuilder();
    }
} 
