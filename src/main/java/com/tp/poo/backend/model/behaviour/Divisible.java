package com.tp.poo.backend.model.behaviour;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.tp.poo.backend.model.figures.Figure;

public interface Divisible {

    default Set<Figure> division(Figure baseCase, int factor, Consumer<Figure> firstStep,
            BiConsumer<Figure, Double> step) {
        Figure.checkFactor(factor);
        Set<Figure> returnSet = new HashSet<>();
        baseCase.magnify(1.0 / factor);
        firstStep.accept(baseCase);
        returnSet.add(baseCase);
        for (int i = 1; i < factor; ++i) {
            Figure toAdd = baseCase.copy();
            step.accept(toAdd, 1.0 / factor * (i + 1));
            returnSet.add(toAdd);
        }
        return returnSet;
    }

    Set<Figure> vDivision(int factor);

    Set<Figure> hDivision(int factor);
    
}
