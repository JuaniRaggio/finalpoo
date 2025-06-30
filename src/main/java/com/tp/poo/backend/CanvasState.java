package com.tp.poo.backend;

import java.util.HashSet;
import java.util.Set;

import com.tp.poo.backend.model.Figure;

public class CanvasState {

    private Set<Figure> set = new HashSet<>();

    public void addFigure(Figure figure) {
        set.add(figure);
    }
    
    public void deleteFigure(Figure figure) {
        set.remove(figure);
    }
    
    public Iterable<Figure> figures() {
        return Set.copyOf(set);
    }
    
}
