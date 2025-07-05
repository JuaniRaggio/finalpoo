package com.tp.poo.frontend;

import com.tp.poo.backend.model.behaviour.Mirrorable;
import com.tp.poo.backend.model.figures.*;
import java.util.function.Function;

public enum Mirrors {

    VMIRROR((figure) -> figure.vMirror()),
    HMIRROR((figure) -> figure.hMirror());

    private Function<Figure, Figure> mirrorType;

    private Mirrors(Function<Figure, Figure> mirrorType) {
        this.mirrorType = mirrorType;
    }

    public Figure mirror(Figure figure) {
        return mirrorType.apply(figure);
    }
    
}
