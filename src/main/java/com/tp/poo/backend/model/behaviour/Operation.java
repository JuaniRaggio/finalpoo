package com.tp.poo.backend.model.behaviour;

import com.tp.poo.backend.model.figures.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface Operation {

    private static void checkFactor(int factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Invalid factor value");
        }
    }

    default List<Figure> operate(Figure baseCase, Function<Figure, Figure> step, int factor) {
        checkFactor(factor);
        Figure next = baseCase;
        List<Figure> toReturn = new ArrayList<>();
        toReturn.add(next);
        for (int i = 1; i < factor; i++) {
            next = step.apply(next);
            toReturn.add(next);
        }
        return toReturn;
    }

}
