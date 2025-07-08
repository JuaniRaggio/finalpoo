package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.*;

import java.util.function.Function;

public enum Mirrors {

    VMIRROR(UIConstants.VERTICAL_MIRROR_BUTTON_TEXT,(figure) -> figure.vMirror()),
    HMIRROR(UIConstants.HORIZONTAL_MIRROR_BUTTON_TEXT,(figure) -> figure.hMirror());

    private final Function<Figure, Figure> mirrorType;
    private final String description;

    Mirrors(String description,Function<Figure, Figure> mirrorType) {
        this.description = description;
        this.mirrorType = mirrorType;
    }

    public Figure mirror(Figure figure) {
        return mirrorType.apply(figure);
    }

    @Override
    public String toString() {
        return description;
    }

}
