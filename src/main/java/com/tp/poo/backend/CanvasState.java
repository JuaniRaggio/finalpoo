package com.tp.poo.backend;

import java.util.ArrayList;
import java.util.List;

import com.tp.poo.backend.model.figures.Figure;

public class CanvasState {

    private List<Figure> figures = new ArrayList<>();

    public void addFigure(Figure figure) {
        figures.add(figure);
    }
    
    public void deleteFigure(Figure figure) {
        figures.remove(figure);
    }
    
    public List<Figure> figures() {
        return new ArrayList<>(figures);
    }
    
}
