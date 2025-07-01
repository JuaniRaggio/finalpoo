package com.tp.poo.backend.model.behaviour;

import java.util.Set;

import com.tp.poo.backend.model.figures.Figure;

public interface Divisible {

    default void checkFactor(int factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Invalid factor value");
        }
    }

    Set<Figure> vDivision(int factor);

    Set<Figure> hDivision(int factor);
    
}
