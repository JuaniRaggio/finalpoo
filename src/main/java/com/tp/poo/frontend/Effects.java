package com.tp.poo.frontend;

//Enum para aplicar los filtros correspondientes.
public enum Effects {
    SHADOW, BRIGHTENING;

    //capaz que acepte como parametro la clase q va a contener la figura encapsulada(A crear) y aplique el effect??--> CHECK LOGICA
    public abstract void applyEffect();
    /// La otra opcion--> public abstract Color applyEffect(); -->q recibe el mismo parametro que la otra alternativa.

}
