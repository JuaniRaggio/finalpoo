package com.tp.poo.backend.model.behaviour;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.tp.poo.backend.model.figures.Figure;

public interface Divisible {

    default Set<Figure> division(Figure baseCase, int factor, Consumer<Figure> firstStep,
            Function<Figure, Figure> step) {
        Set<Figure> returnSet = new HashSet<>();
        firstStep.accept(baseCase);
        returnSet.add(baseCase);
        Figure toAdd = baseCase.copy();
        for (int i = 1; i < factor; ++i) {
            toAdd = step.apply(toAdd).copy();
            returnSet.add(toAdd);
        }
        return returnSet;
    }

    Set<Figure> vDivision(int factor);

    Set<Figure> hDivision(int factor);

}
