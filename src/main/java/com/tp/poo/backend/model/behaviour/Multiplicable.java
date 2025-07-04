package com.tp.poo.backend.model.behaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.tp.poo.backend.model.figures.Figure;

public interface Multiplicable extends Operation{

    default List<Figure> genericMultiplication(int factor, BiFunction<Integer, Integer, Figure> step) {
        final int offset = 5;
        Figure.checkFactor(factor);
        List<Figure> toReturn = new ArrayList<>();
        for (int i = 1; i < factor; i++) {
            toReturn.add(step.apply(i, offset));
        }
        return toReturn;
    }

    List<Figure> multiply(int factor);

}
