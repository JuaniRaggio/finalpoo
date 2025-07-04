package com.tp.poo.frontend;

import com.tp.poo.backend.model.figures.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.EnumSet;
import java.util.Optional;

import com.tp.poo.backend.model.figures.Ellipse;
import com.tp.poo.backend.model.figures.Figure;
import com.tp.poo.backend.model.figures.Rectangle;

public class CustomizeFigure {

    private Format format;
    private final Figure figure;
    private Figure vMirror;
    private Figure hMirror;

    public boolean toggleHorizontalMirror() {
        if (hMirror == null) {
            hMirror = figure.hMirror();
        // hMirror = Optional.of(hMirror.isPresent() ? figure.hMirror():null);
            return true;
        } else {
            hMirror = null;
            return false;
        }
    }

    public boolean toggleVerticalMirror() {
        if (vMirror == null) {
            vMirror = figure.vMirror();
            return true;
        } else {
            vMirror = null;
            return false;
        }
    }

    public class Format {

        private final static Color selectedStrokeColor = Color.RED;
        private final static Color strokeColor = Color.BLACK;
        private Color color;
        private BorderType borderType;
        private EnumSet<Effects> filters = EnumSet.of(Effects.NO_FILTER);

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

        public boolean isFilterOn(Effects filter) {
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

        public void applyFormat(GraphicsContext gc, Figure figure, CustomizeFigure selectedFigure) {
            if (selectedFigure != null && figure == selectedFigure.getBaseFigure())
                gc.setStroke(selectedStrokeColor);
            else
                gc.setStroke(strokeColor);
            gc.setFill(getFilteredColor());
            borderType.applyBorder(gc);
            fill(figure, gc);
            gc.setFill(color);
            fill(vMirror, gc);
            fill(hMirror, gc);
        }

        public static void fill(Figure figure, GraphicsContext gc) {
            if (figure == null)
                return;
            if (figure instanceof Ellipse)
                fill(gc, (Ellipse) figure);
            else if (figure instanceof Rectangle)
                fill(gc, (Rectangle) figure);
        }

        private static void fill(GraphicsContext gc, Ellipse ellipse) {
            gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                    ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                    ellipse.getVerticalAxis());
            gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                    ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                    ellipse.getVerticalAxis());
        }

        private static void fill(GraphicsContext gc, Rectangle rectangle) {
            gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                    Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                    Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
            gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                    Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                    Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        }

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

    public void setFormat(Format newFormat) {
        format = newFormat;
    }

    public void changeColor(Color color) {
        format.setColor(color);
    }

    public void changeColor(GraphicsContext gc) {
        changeColor((Color) gc.getFill());
    }

    public Color getOriginalColor() {
        return format.getColor();
    }

    // Las nuevas figuras tienen las mismas propiedades que las anteriores
    public CustomizeFigure(Figure figure, BorderType borderType, Color color) {
        format = new Format(color, borderType);
        this.figure = figure;
    }

    public CustomizeFigure(Figure figure, Format format) {
        this.format = format;
        this.figure = figure;
    }

    public Format getFormatCopy() {
        return format.copyOf();
    }


    public boolean figureBelongs(Point point) {
        return figure.isContained(point);
    }

    public Figure getBaseFigure() {
        return figure;
    }

    public void moveD(double dx, double dy) {
        figure.moveD(dx, dy);
        if (vMirror != null)
            vMirror.moveD(dx, dy);

        if (hMirror != null)
            hMirror.moveD(dx, dy);
    }

    public void format(GraphicsContext gc, CustomizeFigure selected) {
        format.applyFormat(gc, figure, selected);
    }

    @Override
    public String toString() {
        return figure.toString();
    }

}
