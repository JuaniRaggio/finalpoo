package com.tp.poo.frontend;

import com.tp.poo.backend.CanvasState;
import com.tp.poo.backend.model.figures.Point;
import com.tp.poo.backend.model.figures.Figure;
import com.tp.poo.backend.model.figures.Rectangle;
import com.tp.poo.backend.model.figures.Circle;
import com.tp.poo.backend.model.figures.Square;
import com.tp.poo.backend.model.figures.Ellipse;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class PaintPane extends BorderPane {

    // BackEnd
    private final CanvasState canvasState;

    // Canvas y relacionados
    private final Canvas canvas = new Canvas(800, 600);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    // Botones Barra Izquierda
    private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
    private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
    private final ToggleButton circleButton = new ToggleButton("Círculo");
    private final ToggleButton squareButton = new ToggleButton("Cuadrado");
    private final ToggleButton ellipseButton = new ToggleButton("Elipse");
    private final ToggleButton deleteButton = new ToggleButton("Borrar");

    // Selector de color de relleno
    private final ColorPicker fillColorPicker = new ColorPicker(Color.YELLOW);

    // Dibujar una figura
    private Point startPoint;

    // Seleccionar una figura
    private Figure selectedFigure;

    // StatusBar
    private final StatusPane statusPane;

    public PaintPane(CanvasState canvasState, StatusPane statusPane) {
        this.canvasState = canvasState;
        this.statusPane = statusPane;
        ToggleButton[] toolsArr = { selectionButton, rectangleButton, circleButton, squareButton, ellipseButton,
                deleteButton };
        ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        VBox buttonsBox = new VBox(10);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().add(fillColorPicker);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);

        canvas.setOnMousePressed(event -> {
            startPoint = new Point(event.getX(), event.getY());
        });

        canvas.setOnMouseReleased(event -> {
            Point endPoint = new Point(event.getX(), event.getY());
            if (startPoint == null) {
                return;
            }
            if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
                return;
            }
            Figure newFigure = null;
            if (rectangleButton.isSelected()) {
                newFigure = new Rectangle(startPoint, endPoint);
            } else if (circleButton.isSelected()) {
                double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                newFigure = new Circle(startPoint, circleRadius);
            } else if (squareButton.isSelected()) {
                double size = Math.abs(endPoint.getX() - startPoint.getX());
                newFigure = new Square(startPoint, size);
            } else if (ellipseButton.isSelected()) {
                Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2,
                        (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                double sHorizontalAxis = Math.abs(endPoint.getX() - startPoint.getX());
                double sVerticalAxis = Math.abs(endPoint.getY() - startPoint.getY());
                newFigure = new Ellipse(centerPoint, sVerticalAxis, sHorizontalAxis);
            } else {
                return;
            }
            canvasState.addFigure(newFigure);
            startPoint = null;
            redrawCanvas();
        });

        canvas.setOnMouseMoved(event -> {
            Point eventPoint = new Point(event.getX(), event.getY());
            boolean found = false;
            StringBuilder label = new StringBuilder();
            for (Figure figure : canvasState.figures()) {
                if (figureBelongs(figure, eventPoint)) {
                    found = true;
                    label.append(figure.toString());
                }
            }
            if (found) {
                statusPane.updateStatus(label.toString());
            } else {
                statusPane.updateStatus(eventPoint.toString());
            }
        });

        canvas.setOnMouseClicked(event -> {
            if (selectionButton.isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                boolean found = false;
                StringBuilder label = new StringBuilder("Se seleccionó: ");
                for (Figure figure : canvasState.figures()) {
                    if (figureBelongs(figure, eventPoint)) {
                        found = true;
                        selectedFigure = figure;
                        label.append(figure.toString());
                    }
                }
                if (found) {
                    statusPane.updateStatus(label.toString());
                } else {
                    selectedFigure = null;
                    statusPane.updateStatus("Ninguna figura encontrada");
                }
                redrawCanvas();
            }
        });

        canvas.setOnMouseDragged(event -> {
            if (selectionButton.isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
                double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
                if (selectedFigure instanceof Rectangle rectangle) {
                    rectangle.moveD(diffX, diffY);
                } else if (selectedFigure instanceof Circle circle) {
                    circle.moveD(diffX, diffY);
                } else if (selectedFigure instanceof Square square) {
                    square.moveD(diffX, diffY);
                } else if (selectedFigure instanceof Ellipse ellipse) {
                    ellipse.moveD(diffX, diffY);
                }
                redrawCanvas();
            }
        });

        deleteButton.setOnAction(event -> {
            if (selectedFigure != null) {
                canvasState.deleteFigure(selectedFigure);
                selectedFigure = null;
                redrawCanvas();
            }
        });

        setLeft(buttonsBox);
        setRight(canvas);
    }

    void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1);
        for (Figure figure : canvasState.figures()) {
            if (figure == selectedFigure) {
                gc.setStroke(Color.RED);
            } else {
                gc.setStroke(Color.BLACK);
            }
            gc.setFill(fillColorPicker.getValue());
            if (figure instanceof Rectangle rectangle) {
                gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                        Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                        Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
                gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                        Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                        Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
            } else if (figure instanceof Circle circle) {
                double diameter = circle.getRadius() * 2;
                gc.fillOval(circle.getCenterPoint().getX() - circle.getRadius(),
                        circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
                gc.strokeOval(circle.getCenterPoint().getX() - circle.getRadius(),
                        circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
            } else if (figure instanceof Square square) {
                gc.fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                        Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()),
                        Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
                gc.strokeRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                        Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()),
                        Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
            } else if (figure instanceof Ellipse ellipse) {
                gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                        ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                        ellipse.getVerticalAxis());
                gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                        ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                        ellipse.getVerticalAxis());
            }
        }
    }

    boolean figureBelongs(Figure figure, Point eventPoint) {
        boolean found = false;
        if (figure instanceof Rectangle rectangle) {
            found = eventPoint.getX() > rectangle.getTopLeft().getX()
                    && eventPoint.getX() < rectangle.getBottomRight().getX() &&
                    eventPoint.getY() > rectangle.getTopLeft().getY()
                    && eventPoint.getY() < rectangle.getBottomRight().getY();
        } else if (figure instanceof Circle circle) {
            found = Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
                    Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getRadius();
        } else if (figure instanceof Square square) {
            found = eventPoint.getX() > square.getTopLeft().getX() && eventPoint.getX() < square.getBottomRight().getX()
                    &&
                    eventPoint.getY() > square.getTopLeft().getY()
                    && eventPoint.getY() < square.getBottomRight().getY();
        } else if (figure instanceof Ellipse ellipse) {
            // Nota: Fórmula aproximada. No es necesario corregirla.
            found = ((Math.pow(eventPoint.getX() - ellipse.getCenterPoint().getX(), 2)
                    / Math.pow(ellipse.getHorizontalAxis(), 2)) +
                    (Math.pow(eventPoint.getY() - ellipse.getCenterPoint().getY(), 2)
                            / Math.pow(ellipse.getVerticalAxis(), 2))) <= 0.30;
        }
        return found;
    }

}
