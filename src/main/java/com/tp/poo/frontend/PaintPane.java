package com.tp.poo.frontend;

import java.util.ArrayList;
import java.util.List;

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
        List<ToggleButton> toolsArr = List.of(selectionButton, rectangleButton, circleButton, squareButton,
                ellipseButton, deleteButton);
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
            //
            // TODO: Queremos que se dibuje en caso de que el endpoint este a la izquierda
            // del startpoint?
            // || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()
            //
            if (startPoint == null || endPoint == null) {
                return;
            }
            Figure newFigure = null;
            if (rectangleButton.isSelected()) {
                newFigure = new Rectangle(startPoint, endPoint);
            } else if (circleButton.isSelected()) {
                //
                // TODO: Relacionado con lo de arriba
                // Aca hace el valor absoluto pero para que si ya chequeo que endPoint.x sea
                // menor que startpoint?
                //
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

                // Se repite como arriba
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
                // hasta aca

                redrawCanvas();
            }
        });

        canvas.setOnMouseDragged(event -> {
            if (selectionButton.isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());

                // TODO: Metodo para el diferencia?
                double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
                double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
                selectedFigure.moveD(diffX, diffY);
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
            gc.setStroke(Color.BLACK);
            gc.setFill(fillColorPicker.getValue());
            fill(figure);
        }
        if (canvasState.figures().contains(selectedFigure)) {
            gc.setStroke(Color.RED);
        }
    }

    // TODO: Pensar una mejor solucion
    void fill(Figure figure) {
        if (figure instanceof Ellipse || figure instanceof Circle)
            fill((Ellipse) figure);
        else if (figure instanceof Rectangle || figure instanceof Square)
            fill((Rectangle) figure);
    }

    void fill(Ellipse ellipse) {
        gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                ellipse.getVerticalAxis());
        gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getHorizontalAxis() / 2),
                ellipse.getCenterPoint().getY() - (ellipse.getVerticalAxis() / 2), ellipse.getHorizontalAxis(),
                ellipse.getVerticalAxis());
    }

    void fill(Rectangle rectangle) {
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()),
                Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }

    boolean figureBelongs(Figure figure, Point eventPoint) {
        return figure.isContained(eventPoint);
    }

}
