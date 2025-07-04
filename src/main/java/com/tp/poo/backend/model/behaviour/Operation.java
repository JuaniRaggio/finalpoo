package com.tp.poo.backend.model.behaviour;

import com.tp.poo.backend.model.figures.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface Operation {

    default List<Figure> operate(Figure original, Consumer<Figure> step, int factor) {
        Figure.checkFactor(factor);
        List<Figure> toReturn = new ArrayList<>();
        for (int i = 1; i < factor; i++) {
            Figure next = original.copy();
            step.accept(next);
            toReturn.add(next);
        }
        return toReturn;
    }

}
