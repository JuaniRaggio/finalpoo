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

    protected EnumMap<Mirrors, Figure> mirrors = new EnumMap<>(Mirrors.class);

    public abstract void fill(Figure figure, GraphicsContext gc);

    protected abstract CustomizeFigure getCopy(Figure figure, Format format);

    private <E extends Enum<E>> void setEffect(boolean should, Runnable add, Runnable remove) {
        if (should) {
            add.run();
        } else {
            remove.run();
        }
    }

    public void setMirror(Mirrors mirrorType, boolean shouldSet) {
        setEffect(shouldSet, () -> mirrors.put(mirrorType, mirrorType.mirror(figure)),
                () -> mirrors.remove(mirrorType));
    }

    public void setFilter(Effects filter, boolean shouldSet) {
        setEffect(shouldSet, () -> format.addFilter(filter), () -> format.removeFilter(filter));
    }

    public void clearMirrors() {
        mirrors.clear();
    }

    public EnumMap<Mirrors, Figure> getMirrors() {
        return mirrors;
    }

    public static class Format {

        private final static Color selectedStrokeColor = UIConstants.DEFAULT_SELECTED_STROKE_COLOR;
        private final static Color strokeColor = UIConstants.DEFAULT_STROKE_COLOR;
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

        public Format nonFilteredCopy() {
            return new Format(color, borderType);
        }

        public void setFormat(Color color, BorderType borderType) {
            setColor(color);
            setBorderType(borderType);
        }

        public Color getColor() {
            return color;
        }

        public void addFilter(Effects filter) {
            filters.add(filter);
        }

        public void removeFilter(Effects filter) {
            filters.remove(filter);
        }

        public BorderType getBorderType() {
            return borderType;
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

    private void applyFormat(Figure figure, GraphicsContext gc, Color strokeColor, Color fillColor) {
        gc.setStroke(strokeColor);
        gc.setFill(fillColor);
        format.borderType.applyBorder(gc);
        fill(figure, gc);
        gc.setFill(format.color);
    }

    public CustomizeFigure(Figure figure, BorderType borderType, Color color, EnumSet<Effects> effects,
            EnumSet<Mirrors> mirrors) {
        this(figure, borderType, color);

        this.mirrors = mirrors.stream().collect(
                Collectors.toMap(
                        mirror -> mirror,
                        mirror -> mirror.mirror(figure),
                        (a, b) -> b,
                        () -> new EnumMap<>(Mirrors.class)));
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
        format.setFormat(newFormat.color, newFormat.borderType);
    }

    public void changeColor(Color color) {
        format.setColor(color);
    }

    public Color getOriginalColor() {
        return format.getColor();
    }

    public Format getFormatCopy() {
        return format.nonFilteredCopy();
    }

    private List<CustomizeFigure> operate(Function<Figure, List<Figure>> operation) {
        clearMirrors();
        return operation.apply(figure).stream()
                .map(f -> getCopy(f, new Format(format.getColor(), format.getBorderType(), format.getFilters())))
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
        for (Figure fig : mirrors.values()) {
            fig.moveD(dx, dy);
        }
    }

    private static Color getStrokeColorForFigure(CustomizeFigure figure, CustomizeFigure selected) {
        if (selected == null || selected.getBaseFigure() != figure.getBaseFigure()) {
            return Format.strokeColor;
        }
        return Format.selectedStrokeColor;
    }

    public void format(GraphicsContext gc, CustomizeFigure selected) {
        for (Figure mirroredFigure : mirrors.values()) {
            applyFormat(mirroredFigure, gc, Format.strokeColor, format.getColor());
        }
        applyFormat(figure, gc, getStrokeColorForFigure(this, selected),
                format.getFilteredColor());
    }

    @Override
    public String toString() {
        return figure.toString();
    }

}
