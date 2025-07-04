package com.tp.poo.frontend;
import java.util.Collections;
import java.util.List;

//esta OK

public enum Operations {
    MULTIPLY("Multiply", "Enter value for N:") {
        @Override
        public List<CustomizeFigure> execute(CustomizeFigure figure, String input) {
            return figure.multiply(getN(input));
        }
    },
    DIVIDE_H("Divide H.", "Enter value for N:") {
        @Override
        public List<CustomizeFigure> execute(CustomizeFigure figure, String input) {
            return figure.hDivision(getN(input));
        }
    },
    DIVIDE_V("Divide V.", "Enter value for N:"){
        @Override
        public List<CustomizeFigure> execute(CustomizeFigure figure, String input) {
            return figure.vDivision(getN(input));
        }
    },
    TRANSFER("Transfer", "Enter coordinates (x,y):") {
        @Override
        public List<CustomizeFigure> execute(CustomizeFigure figure, String param) {
            int[] coordinates = getCoordinates(param);
            figure.transferFigure(coordinates[0], coordinates[1]); // modifica directamente
            return Collections.emptyList(); // no se agregan figuras nuevas
        }
    };

    private final String description, instructions;

    Operations(String description, String instructions) {
        this.description = description;
        this.instructions = instructions;
    }

    public String getDescription() { return description; }

    public String getInstructions() { return instructions; }

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

        if(parts.length != 2) {
            throw new IllegalArgumentException("Invalid amount of parameters");
        }

        String value1 = parts[0].trim();
        String value2 = parts[1].trim();

        if(!isInteger(value1) || !isInteger(value2)) {
            throw new NumberFormatException("Not an integer");
        }

        return new int[] {Integer.parseInt(value1), Integer.parseInt(value2)};
    }


    public int getN(String param) {
        try {
          return Integer.parseInt(param.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Not an integer");
        }
    }

}
