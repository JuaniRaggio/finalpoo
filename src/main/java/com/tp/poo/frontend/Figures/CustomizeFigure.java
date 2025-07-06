package com.tp.poo.frontend.Figures;

import com.tp.poo.backend.model.figures.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import com.tp.poo.frontend.*;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class CustomizeFigure {

    private Format format;
    protected final Figure figure;

    //Cambie el value del enumMap (Figure->CustomizeFigure) porque o sino no se podria hacer nada sobre la figura
    protected EnumMap<Mirrors, CustomizeFigure> mirrors = new EnumMap<>(Mirrors.class);
    // mejora? : EnumMap.noneOf(Mirrors.class)

    public abstract void fill(GraphicsContext gc);

    protected abstract CustomizeFigure getCopy(Figure figure, Format format);

    public boolean isMirror(Mirrors mirrorType) {
        return mirrors.containsKey(mirrorType);
    }

    public void setMirror(Mirrors mirrorType, boolean shouldSet) {
        if (shouldSet) {
//          BEFORE:  mirrors.put(mirrorType, mirrorType.mirror(figure));
//            mirrors.put(mirrorType, getCopy(mirrorType.mirror(figure), format.copyOf())); -->esto esta mal porque si uso format.copyOf() o getFormatCopy() copia todos los filtros "prendidos"
            //entonces las figuras espejadas van a tener los mismos filtros aplicados a la original, lo cual creo q no es deseado.
            mirrors.put(mirrorType, getCopy(mirrorType.mirror(figure), new Format(format.getColor(), format.getBorderType() ))); //*-->es muy flashero?? CHECK
        } else {
            mirrors.remove(mirrorType);
        }
    }

    public EnumMap<Mirrors, CustomizeFigure> getMirrors() {
        return mirrors;
    }

    public static class Format {

        private final static Color selectedStrokeColor = Color.RED;
        private final static Color strokeColor = Color.BLACK;
        private Color color;
        private BorderType borderType;
        private EnumSet<Effects> filters = EnumSet.noneOf(Effects.class);

        public Format(Color color, BorderType borderType) {
            setFormat(color, borderType);
        }

        public Format(Color color, BorderType borderType, EnumSet<Effects> filters) {
            this(color, borderType);
            this.filters = EnumSet.copyOf(filters);
        }

        public void addFilter(Effects filter) {
            filters.add(filter);
        }

        public void removeFilter(Effects filter) {
            filters.remove(filter);
        }

        public Format copyOf() {
            return new Format(color, borderType, EnumSet.copyOf(filters));
        }

        public void setFormat(Color color, BorderType borderType) {
            setColor(color);
            setBorderType(borderType);
        }

        public Color getColor() {
            return color;
        }

        public BorderType getBorderType() {
            return borderType;
        }

        public boolean isFilterOn(Effects filter) { // esto nunca se usó... chequear si se puede borrar
            return filters.contains(filter);
        }

        public EnumSet<Effects> getFilters() {
            return filters;
        }


        public void setBorderType(BorderType borderType) {
            this.borderType = borderType;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        private Color getFilteredColor() {
            Color filteredColor = color;
            for (Effects effect : filters) {
                filteredColor = effect.applyEffect(filteredColor);
            }
            return filteredColor;
        }
    }
//se cambiaron params...y algunos chequeos
// BEFORE: public void applyFormat(GraphicsContext gc, Figure figure, CustomizeFigure selectedFigure) ;
    public void applyFormat(GraphicsContext gc, CustomizeFigure selectedFigure) {
        if (selectedFigure != null && this.figure == selectedFigure.getBaseFigure())
            gc.setStroke(Format.selectedStrokeColor);
        else
            gc.setStroke(Format.strokeColor);
        gc.setFill(format.getFilteredColor());
        format.borderType.applyBorder(gc);
        fill(gc);
        gc.setFill(format.color);
    }

    public CustomizeFigure(Figure figure, BorderType borderType, Color color, EnumSet<Effects> effects,
            EnumSet<Mirrors> mirrors) {
        this(figure, borderType, color);
//        this.mirrors = mirrors.stream().collect(
//                Collectors.toMap((mirror) -> mirror, (mirror) -> mirror.mirror(figure), (a, b) -> b,
//                        () -> new EnumMap<>(Mirrors.class)));  -->BEFORE

        this.mirrors = mirrors.stream().collect(
                Collectors.toMap(
                        mirror -> mirror,
                        mirror -> getCopy(mirror.mirror(figure), getFormatCopy()),
                        (a, b) -> b,
                        () -> new EnumMap<>(Mirrors.class)
                )
        );

        format.getFilters().addAll(effects);
    }

    public CustomizeFigure(Figure figure, BorderType borderType, Color color) {
        format = new Format(color, borderType);
        this.figure = figure;
    }

    public CustomizeFigure(Figure figure, Format format) {
        this.format = format;
        this.figure = figure;
    }

    public BorderType getBorderType() {
        return format.getBorderType();
    }

    public void addFilter(Effects filter) {
        format.addFilter(filter);
    }

    public void removeFilter(Effects filter) {
        format.removeFilter(filter);
    }

    public void setBorderType(BorderType borderType) {
        format.setBorderType(borderType);
    }

    public EnumSet<Effects> getFilters() {
        return format.getFilters();
    }

    public void setFormat(Format newFormat) {
        format = newFormat;
    }

    public void changeColor(Color color) {
        format.setColor(color);
    }

    public void changeColor(GraphicsContext gc) {
        changeColor((Color) gc.getFill());  //------->check si se usa...
    }

    public Color getOriginalColor() {
        return format.getColor();
    }

    public Format getFormatCopy() {
        return format.copyOf();
    }

    private List<CustomizeFigure> operate(Function<Figure, List<Figure>> operation) {
        return operation.apply(figure).stream()
                .map(f -> getCopy(f, format.copyOf()))
                .toList();
    }

    public List<CustomizeFigure> multiply(int n) {
        return operate((figure) -> figure.multiply(n));
    }

    public List<CustomizeFigure> hDivision(int n) {
        return operate((figure) -> figure.hDivision(n));
    }

    public List<CustomizeFigure> vDivision(int n) {
        return operate((figure) -> figure.vDivision(n));
    }

    public void transferFigure(double x, double y) {
        figure.transfer(x, y);
    }

    public boolean figureBelongs(Point point) {
        return figure.isContained(point);
    }

    private Figure getBaseFigure() {
        return figure;
    }

    public void moveD(double dx, double dy) {
        figure.moveD(dx, dy);
        for (CustomizeFigure fig : mirrors.values()) {
            fig.moveD(dx, dy);
        }
    }

    public void format(GraphicsContext gc, CustomizeFigure selected) {
        applyFormat(gc,selected);

        for (CustomizeFigure mirroredFigure : mirrors.values()) {
            mirroredFigure.applyFormat(gc,selected);
        }
    }

    @Override
    public String toString() {
        return figure.toString();
    }

}
