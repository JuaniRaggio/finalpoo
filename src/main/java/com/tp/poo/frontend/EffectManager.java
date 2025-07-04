package com.tp.poo.frontend;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Consumer;

public class EffectManager {
    
    private final Map<Effects, Boolean> activeEffects = new EnumMap<>(Effects.class);
    private final Map<Effects, Consumer<CustomizeFigure>> effectHandlers = new EnumMap<>(Effects.class);
    
    public EffectManager() {
        setupEffectHandlers();
    }
    
    private void setupEffectHandlers() {
        effectHandlers.put(Effects.SHADOW, figure -> figure.addFilter(Effects.SHADOW));
        effectHandlers.put(Effects.BRIGHTENING, figure -> figure.addFilter(Effects.BRIGHTENING));
    }
    
    public void toggleEffect(Effects effect, boolean active, CustomizeFigure selectedFigure) {
        activeEffects.put(effect, active);
        
        if (selectedFigure != null) {
            if (active) {
                selectedFigure.addFilter(effect);
            } else {
                selectedFigure.removeFilter(effect);
            }
        }
    }
    
    public boolean isEffectActive(Effects effect) {
        return activeEffects.getOrDefault(effect, false);
    }
    
    public EnumSet<Effects> getActiveEffects() {
        EnumSet<Effects> active = EnumSet.noneOf(Effects.class);
        for (Map.Entry<Effects, Boolean> entry : activeEffects.entrySet()) {
            if (entry.getValue()) {
                active.add(entry.getKey());
            }
        }
        return active;
    }
    
    public void syncWithFigure(CustomizeFigure figure) {
        if (figure != null) {
            EnumSet<Effects> figureEffects = figure.getFilters();
            for (Effects effect : Effects.values()) {
                activeEffects.put(effect, figureEffects.contains(effect));
            }
        }
    }
} 