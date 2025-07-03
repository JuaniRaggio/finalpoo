package com.tp.poo.frontend;

import javafx.scene.control.TextInputDialog;

import javax.sound.midi.Soundbank;
import java.util.Optional;

public enum Operations {
    MULTIPLY("Multiply") {
        @Override
        public void applyOperation(CustomizeFigure figure, String content) {
               figure.getBaseFigure().multiply(getN(content));
        }
    },
    DIVIDE_HORIZONTAL("Divide H."){
        @Override
        public void applyOperation(CustomizeFigure figure, String content) {
            figure.getBaseFigure().hDivision(getN(content));
        }
    },
    DIVIDE_VERTICAL("Divide V.") {
        @Override
        public void applyOperation(CustomizeFigure figure, String content) {
            figure.getBaseFigure().vDivision(getN(content));
        }
    },
    TRANSFER("Transfer") {
        @Override
        public void applyOperation(CustomizeFigure figure, String content) {
            int[] coordenates = getCoordenates(content);
            figure.getBaseFigure().transfer(coordenates[0], coordenates[1]);
        }
    };

    private final String description;

    Operations(String description) {
        this.description = description;
    }

    public abstract void applyOperation(CustomizeFigure figure, String content);


    private static boolean isInteger(String stringInt) {
        try {
            Integer.parseInt(stringInt);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int[] getCoordenates(String content) {
        String[] parts = content.split(",");

        if(parts.length != 2) {
            System.out.println("invalid argument");
        }

        String value1 = parts[0].trim();
        String value2 = parts[1].trim();


        if(isInteger(value1) && isInteger(value2)) {
            System.out.println("not an integer on coordenates");
        }

        return new int[] {Integer.parseInt(value1), Integer.parseInt(value2)};
    }


    public int getN(String content) {
        if(isInteger(content)) {
            System.out.println("not an integer on N");
        }

        return Integer.parseInt(content);
    }

    //en los parametros de las operaciones se reciben int segun el enunciado pero nosotros pusimos doubles
    //para poder ser mas flexibles en el lienzo chequearlo bien!!
}
