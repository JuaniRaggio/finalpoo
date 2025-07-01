package com.tp.poo.backend.model.behaviour;

import java.util.function.Consumer;

import com.tp.poo.backend.model.figures.Figure;

public interface Mirrorable {

    default Figure mirror(Figure base, Consumer<Figure> distanceAplication) {
        Figure entity = base.copy();
        distanceAplication.accept(entity);
        return entity;
    }
    
    Figure vMirror();

    Figure hMirror();
    
}
