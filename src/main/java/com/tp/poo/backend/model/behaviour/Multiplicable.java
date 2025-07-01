package com.tp.poo.backend.model.behaviour;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import com.tp.poo.backend.model.figures.Figure;

public interface Multiplicable {

    default Set<Figure> genericMultiplication(int factor, BiFunction<Integer, Integer, Figure> step) {
        Figure.checkFactor(factor);
        final int offset = 5;
        Set<Figure> toReturn = new HashSet<>();
        for (int i = 1; i < factor; i++) {
            toReturn.add(step.apply(i, offset));
        }
        return toReturn;
    }

    Set<Figure> multiply(int factor);

}
