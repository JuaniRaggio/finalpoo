package com.tp.poo.backend.model.behaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.tp.poo.backend.model.figures.Figure;

public interface Divisible {

    default List<Figure> division(Figure baseCase, int factor, Consumer<Figure> firstStep,
            Function<Figure, Figure> step) {
        List<Figure> returnList = new ArrayList<>();
        firstStep.accept(baseCase);
        returnList.add(baseCase);
        Figure toAdd = baseCase.copy();
        for (int i = 1; i < factor; ++i) {
            toAdd = step.apply(toAdd).copy();
            returnList.add(toAdd);
        }
        return returnList;
    }

    List<Figure> vDivision(int factor);

    List<Figure> hDivision(int factor);

}
