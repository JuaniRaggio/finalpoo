package com.tp.poo.frontend;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.tp.poo.backend.CanvasState;
import com.tp.poo.backend.model.figures.Point;
import com.tp.poo.backend.model.figures.Rectangle;
import com.tp.poo.backend.model.figures.Circle;
import com.tp.poo.backend.model.figures.Square;
import com.tp.poo.backend.model.figures.Ellipse;
import com.tp.poo.backend.model.figures.Figure;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

    // Botones de la toolbar superior
    private final CheckBox shadowButton = new CheckBox("Darken");
    private final CheckBox brightenButton = new CheckBox("Brighten");
    private final CheckBox horizontalMirrorButton = new CheckBox("Horizontal Mirror");
    private final CheckBox verticalMirrorButton = new CheckBox("Vertical Mirror");

    private final ComboBox<BorderType> borderTypeTopCombo = new ComboBox<>();

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

        Label effectsLabel = new Label("Effects:");

        HBox buttonsBar = new HBox(10); // espacio horizontal entre controles
        List<CheckBox> checkArr = List.of(shadowButton, brightenButton, horizontalMirrorButton,verticalMirrorButton);
        for (CheckBox effect : checkArr) {
            effect.setMinWidth(90);
            effect.setCursor(Cursor.HAND);
        }

        buttonsBar.getChildren().add(effectsLabel);
        buttonsBar.getChildren().addAll(checkArr);

        buttonsBar.setPadding(new Insets(5, 5, 5, 120));
        buttonsBar.setStyle("-fx-background-color: #999;");
        buttonsBar.setPrefHeight(20);  // altura fija para la barra superior

        setTop(buttonsBar);

        /*private void setCheckBox(CheckBox checkBox, Consumer<Boolean> state) {
            checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                state.accept(newVal);
            });
        }

        private void setCheckBox(CheckBox checkBox, Runnable checkOn, Runnable checkOff) {
            checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    checkOn.run();
                } else {
                    checkOff.run();
                }
            });
        }

         */

        //i.e: setCheckbox(shadowButton, this::aplicarSombra, this::removerSombra);

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

        fillColorPicker.setOnAction(e -> {
            if (selectedFigure != null) {
                selectedFigure.changeColor(fillColorPicker.getValue());
                redrawCanvas();
            }
        });

        borderTypeCombo.setOnAction(e -> {;
            if (selectedFigure != null) {
                selectedFigure.setBorderType(borderTypeCombo.getValue());
                redrawCanvas();
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
            actOnSelection(eventPoint, label, (fig) -> {}, (pt) -> {},
                    () -> statusPane.updateStatus(eventPoint.toString()));
        });

        canvas.setOnMouseClicked(event -> {
            if (selectionButton.isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                StringBuilder label = new StringBuilder("Selected: ");
                actOnSelection(eventPoint, label, (fig) -> this.selectedFigure = fig,
                        (lastSeen) -> this.lastDragPoint = lastSeen, () -> {
                            this.selectedFigure = null;
                            this.lastDragPoint = null;
                            statusPane.updateStatus("No figure found");
                        });
                redrawCanvas();
            }
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

    private void actOnSelection(Point eventPoint, StringBuilder label, Consumer<CustomizeFigure> selected,
                                Consumer<Point> lastSeen, Runnable ifNotFound) {
        boolean found = false;
        for (CustomizeFigure figure : canvasState) {
            if (figure.figureBelongs(eventPoint)) {
                found = true;
                selected.accept(figure);
                lastSeen.accept(lastDragPoint);
                label.append(figure.toString());
            }
        }
        if (found) {
            statusPane.updateStatus(label.toString());
        } else {
            ifNotFound.run();
        }
    }

    private void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1);
        for (CustomizeFigure figure : canvasState) {
            figure.format(gc, selectedFigure);
        }
    }

}
