package com.tp.poo.frontend;

import javafx.scene.paint.Color;
//Enum para aplicar los filtros correspondientes.
public enum Effects {
    SHADOW()

    , BRIGHTENING();

    private final Color color;

    //capaz que acepte como parametro la clase q va a contener la figura encapsulada(A crear) y aplique el effect??--> CHECK LOGICA
    public Color applyEffect(Color color);
    /// La otra opcion--> public abstract Color applyEffect(); -->q recibe el mismo parametro que la otra alternativa.

}
