package com.tp.poo.backend.model;

public interface Resizeable {

    default void checkMagnificationRate(double magnificationRate) {
        if(magnificationRate <= 0) {
            throw new IllegalArgumentException("Invalid magnification rate");
        }
    }

    void magnify(double magnificationRate);
    
}
