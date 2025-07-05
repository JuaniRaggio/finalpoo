package com.tp.poo.backend.model.behaviour;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.tp.poo.backend.model.figures.Figure;

public interface Divisible extends Operation{

    default List<Figure> division(Figure original, int factor, Consumer<Figure> firstStep,
            Function<Figure, Figure> step) {
        firstStep.accept(original);
        return operate(original, step, factor);
    }

    List<Figure> vDivision(int factor);

    List<Figure> hDivision(int factor);

}
