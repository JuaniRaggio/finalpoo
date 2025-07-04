package com.tp.poo.backend.model.behaviour;

import java.util.List;

import com.tp.poo.backend.model.figures.Figure;

public interface Multiplicable extends Operation {

    List<Figure> multiply(int factor);

}
