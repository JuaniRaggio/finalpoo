package com.tp.poo.frontend;

import java.util.List;

import com.tp.poo.backend.CanvasState;
import com.tp.poo.backend.model.figures.Point;
import com.tp.poo.backend.model.figures.Rectangle;
import com.tp.poo.backend.model.figures.Circle;
import com.tp.poo.backend.model.figures.Square;
import com.tp.poo.backend.model.figures.Ellipse;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

    // BackEnd
    private final CanvasState<CustomizeFigure> canvasState;

    // Canvas y relacionados
    private final Canvas canvas = new Canvas(800, 600);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    // Botones Barra Izquierda
    private final ToggleButton selectionButton = new ToggleButton("Select");
    private final ToggleButton rectangleButton = new ToggleButton("Rectangle");
    private final ToggleButton circleButton = new ToggleButton("Circle");
    private final ToggleButton squareButton = new ToggleButton("Square");
    private final ToggleButton ellipseButton = new ToggleButton("Ellipse");
    private final ToggleButton deleteButton = new ToggleButton("Errase");

    // Agrego los nuevos controles de la barra lateral izq.
    private final ComboBox<BorderType> borderTypeCombo = new ComboBox<>();
    // Button ejecuta una acción al hacer clic, mientras que ToggleButton mantiene
    // un estado (activado/desactivado) --> info chat
    private final Button copyFormatButton = new Button("Copy format");
    private final Button pasteFormatButton = new Button("Paste format");

    private CustomizeFigure copiedFormat = null;

    // Selector de color de relleno
    private final ColorPicker fillColorPicker = new ColorPicker(Color.YELLOW);

    // Dibujar una figura
    private Point startPoint;

    // Seleccionar una figura
    private CustomizeFigure selectedFigure;

    // Para el drag de figuras
    private Point lastDragPoint;

    // StatusBar
    private final StatusPane statusPane;

    public PaintPane(CanvasState<CustomizeFigure> canvasState, StatusPane statusPane) {
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
        buttonsBox.getChildren().add(borderTypeCombo);
        buttonsBox.getChildren().add(fillColorPicker);
        buttonsBox.getChildren().addAll(copyFormatButton, pasteFormatButton); // Agrego los nuevos controles a la barra
        // lateral
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);

        borderTypeCombo.getItems().addAll(BorderType.values());
        borderTypeCombo.setValue(BorderType.SOLID);

        copyFormatButton.setMinWidth(90);
        pasteFormatButton.setMinWidth(90);
        borderTypeCombo.setMinWidth(90);

        copyFormatButton.setOnAction(e -> {
            if (selectedFigure != null) {
                // copiedFormat = selectedFigure.METODOPARACOPIAR();
            }
        });

        pasteFormatButton.setOnAction(e -> {
            if (selectedFigure != null && copiedFormat != null) {
                // selectedFigure.METODOPARAPEGAR(copiedFormat);
                redrawCanvas();
            }
        });

        canvas.setOnMousePressed(event -> {
            startPoint = new Point(event.getX(), event.getY());
        });

        canvas.setOnMouseReleased(event -> {
            Point endPoint = new Point(event.getX(), event.getY());
            if (startPoint == null || endPoint == null || endPoint.getX() < startPoint.getX()
                    || endPoint.getY() < startPoint.getY()) {
                return;
            }
            CustomizeFigure newFigure = null;
            if (rectangleButton.isSelected()) {
                newFigure = new CustomizeFigure(new Rectangle(startPoint, endPoint), borderTypeCombo.getValue(),
                        fillColorPicker.getValue());
            } else if (circleButton.isSelected()) {
                //
                // TODO: Relacionado con lo de arriba
                // Aca hace el valor absoluto pero para que si ya chequeo que endPoint.x sea
                // menor que startpoint?
                //
                double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                newFigure = new CustomizeFigure(new Circle(startPoint, circleRadius), borderTypeCombo.getValue(),
                        fillColorPicker.getValue());
            } else if (squareButton.isSelected()) {
                double size = Math.abs(endPoint.getX() - startPoint.getX());
                newFigure = new CustomizeFigure(new Square(startPoint, size), borderTypeCombo.getValue(),
                        fillColorPicker.getValue());
            } else if (ellipseButton.isSelected()) {
                Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2,
                        (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                double sHorizontalAxis = Math.abs(endPoint.getX() - startPoint.getX());
                double sVerticalAxis = Math.abs(endPoint.getY() - startPoint.getY());
                newFigure = new CustomizeFigure(new Ellipse(centerPoint, sVerticalAxis, sHorizontalAxis),
                        borderTypeCombo.getValue(), fillColorPicker.getValue());
            } else {
                return;
            }
            this.canvasState.add(newFigure);
            startPoint = null;
            redrawCanvas();
        });

        canvas.setOnMouseMoved(event -> {
            Point eventPoint = new Point(event.getX(), event.getY());
            StringBuilder label = new StringBuilder();
            if(eventNotInFigure(eventPoint, label)){
                statusPane.updateStatus(eventPoint.toString());
            }
            /*
            boolean found = false;
            StringBuilder label = new StringBuilder();
            for (CustomizeFigure figure : canvasState) {
                if (figure.figureBelongs(eventPoint)) {
                    found = true;
                    label.append(figure.toString());
                }
            }
            if (found) {
                statusPane.updateStatus(label.toString());
            } else {
                statusPane.updateStatus(eventPoint.toString());
            }*/
        });

        canvas.setOnMouseClicked(event -> {
            if (selectionButton.isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                StringBuilder label = new StringBuilder("Selected: ");
                if(eventNotInFigure(eventPoint, label)){
                    selectedFigure = null;
                    lastDragPoint = null;
                    statusPane.updateStatus("No figure found");
                }
                redrawCanvas();
            }
                /*
                Point eventPoint = new Point(event.getX(), event.getY());
                boolean found = false;
                StringBuilder label = new StringBuilder("Selected: ");
                // TODO
                // Se repite como arriba
                for (CustomizeFigure figure : this.canvasState) {
                    if (figure.figureBelongs(eventPoint)) {
                        found = true;
                        selectedFigure = figure;
                        lastDragPoint = eventPoint;
                        label.append(figure.toString());
                    }
                }
                if (found) {
                    statusPane.updateStatus(label.toString());
                } else {
                    selectedFigure = null;
                    lastDragPoint = null;
                    statusPane.updateStatus("No figure found");
                }
                // hasta aca
                redrawCanvas();
            }*/
        });

        canvas.setOnMouseDragged(event -> {
            if (selectionButton.isSelected() && selectedFigure != null && lastDragPoint != null) {
                Point eventPoint = new Point(event.getX(), event.getY());
                double diffX = eventPoint.getX() - lastDragPoint.getX();
                double diffY = eventPoint.getY() - lastDragPoint.getY();
                selectedFigure.moveD(diffX, diffY);
                lastDragPoint = eventPoint;
                redrawCanvas();
            }
        });

        deleteButton.setOnAction(event -> {
            if (selectedFigure != null) {
                this.canvasState.remove(selectedFigure);
                selectedFigure = null;
                redrawCanvas();
            }
        });

        setLeft(buttonsBox);
        setRight(canvas);
    }

    private void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1);
        for (CustomizeFigure figure : canvasState) {
            figure.format(gc, selectedFigure);
        }
    }
    private boolean eventNotInFigure(Point eventPoint, StringBuilder label){
        boolean found = false;
        for (CustomizeFigure figure : canvasState) {
            if (figure.figureBelongs(eventPoint)) {
                found = true;
                label.append(figure.toString());
            }
        }
        if (found) {
            statusPane.updateStatus(label.toString());
        }
        return (!found);
    }

}
