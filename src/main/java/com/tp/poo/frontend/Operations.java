package com.tp.poo.frontend;

import com.tp.poo.frontend.Figures.*;

import java.util.Collections;
import java.util.List;

public enum Operations {

    MULTIPLY(UIConstants.MULTIPLY_BUTTON_TEXT, UIConstants.ASKING_FOR_N_TEXT) {
        @Override
        public List<CustomizeFigure> execute(CustomizeFigure figure, String input) {
            return figure.multiply(getN(input));
        }
    },
    DIVIDE_H(UIConstants.DIVIDE_H_BUTTON_TEXT, UIConstants.ASKING_FOR_N_TEXT) {
        @Override
        public List<CustomizeFigure> execute(CustomizeFigure figure, String input) {
            return figure.hDivision(getN(input));
        }
    },
    DIVIDE_V(UIConstants.DIVIDE_V_BUTTON_TEXT, UIConstants.ASKING_FOR_N_TEXT) {
        @Override
        public List<CustomizeFigure> execute(CustomizeFigure figure, String input) {
            return figure.vDivision(getN(input));
        }
    },
    TRANSFER(UIConstants.TRANSFER_BUTTON_TEXT, UIConstants.ASKING_FOR_COORDS_TEXT) {
        @Override
        public List<CustomizeFigure> execute(CustomizeFigure figure, String param) {
            int[] coordinates = getCoordinates(param);
            figure.transferFigure(coordinates[0], coordinates[1]);
            return Collections.emptyList();
        }
    };

    private final String description, instructions;

    Operations(String description, String instructions) {
        this.description = description;
        this.instructions = instructions;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public abstract List<CustomizeFigure> execute(CustomizeFigure fig, String param);

    private static boolean isInteger(String stringInt) {
        try {
            Integer.parseInt(stringInt);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int[] getCoordinates(String param) {
        String[] parts = param.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException(UIConstants.INVALID_AMOUNT_OF_PARAMETERS_MESSAGE);
        }

        String value1 = parts[0].trim();
        String value2 = parts[1].trim();

        if (!isInteger(value1) || !isInteger(value2)) {
            throw new NumberFormatException(UIConstants.NOT_AN_INTEGER_MESSAGE);
        }

        return new int[] { Integer.parseInt(value1), Integer.parseInt(value2) };
    }

    public int getN(String param) {
        try {
            return Integer.parseInt(param.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(UIConstants.NOT_AN_INTEGER_MESSAGE);
        }
    }

}
