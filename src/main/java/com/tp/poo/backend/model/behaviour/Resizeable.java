package com.tp.poo.backend.model.behaviour;

import com.tp.poo.frontend.UIConstants;

public interface Resizeable {

    default void checkMagnificationRate(double magnificationRate) {
        if(magnificationRate <= 0) {
            throw new IllegalArgumentException(UIConstants.INVALID_MAGNIFICATION_RATE_MESSAGE);
        }
    }

    void magnify(double magnificationRate);
    
}
