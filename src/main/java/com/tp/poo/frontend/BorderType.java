package com.tp.poo.frontend;

public enum BorderType {
    SOLID("Normal"),               // Línea continua: la implementación "normal"
    PIXELATED("Pixelado"),           // Borde con caracteres "|"
    DOTTED_THIN("Punt. Fino"),         // "- - - -" puntos simples, separados
    DOTTED_COMPLEX("Punt. Comp.")       // "-- - -- -" combinación de guiones simples y dobles

    public abstract void applyBorder();
}
