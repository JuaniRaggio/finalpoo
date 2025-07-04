package com.tp.poo.backend.model.behaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.tp.poo.backend.model.figures.Figure;

public interface Divisible extends Operation{

    default List<Figure> division(Figure original, int factor, Consumer<Figure> firstStep,
            Consumer<Figure> step) {
        firstStep.accept(original);
        original.magnify(1.0/(double)factor);
        List<Figure> returnList = operate(original, step, factor);
        returnList.add(original);
        return returnList;
    }

    List<Figure> vDivision(int factor);

    List<Figure> hDivision(int factor);

}
