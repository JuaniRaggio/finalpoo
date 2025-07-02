package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.Figure;
import javafx.scene.paint.Color;
//Enum para aplicar los filtros correspondientes.
public enum Effects {
    SHADOW(Color.rgb(255, 255, 255),0.7) {
        @Override
        public void applyEffect(CostumiseFigure figure) {
            figure.getOriginalColor().interpolate(color, opacity);
        }
    },
    BRIGHTENING(Color.rgb(0, 0, 0), 0.3){
        @Override
        public void applyEffect(CostumiseFigure figure) {
            figure.getOriginalColor().interpolate(color, opacity);
        }
    };

    private final Color color;
    private final double opacity;

    Effects(Color color, double opacity) {
        this.color = color;
        this.opacity = opacity;
    }

    //capaz que acepte como parametro la clase q va a contener la figura encapsulada(A crear) y aplique el effect??--> CHECK LOGICA
    public abstract void applyEffect(CostumiseFigure figure);
    /// La otra opcion--> public abstract Color applyEffect(); -->q recibe el mismo parametro que la otra alternativa.

}
