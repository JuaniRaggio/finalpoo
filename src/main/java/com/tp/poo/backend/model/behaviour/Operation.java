package com.tp.poo.backend.model.behaviour;

import com.tp.poo.backend.model.figures.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface Operation {

    default List<Figure> operate(Figure baseCase, Function<Figure, Figure> step, int factor) {
        Figure.checkFactor(factor);
        Figure next = baseCase;
        List<Figure> toReturn = new ArrayList<>();
        for (int i = 1; i < factor; i++) {
            next = next.copy();
            toReturn.add(step.apply(next));
        }
        return toReturn;
    }

}
