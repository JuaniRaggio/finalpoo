package com.tp.poo.frontend;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.effect.Effect;
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
    private final Button divideHButton = new Button("Divide H.");
    private final Button divideVButton = new Button("Divide V.");
    private final Button multiplyButton = new Button("Multiply");
    private final Button transferButton = new Button("Transfer");

    // Agrego los nuevos controles de la barra lateral izq.
    private final ComboBox<BorderType> borderTypeCombo = new ComboBox<>();
    // Button ejecuta una acción al hacer clic, mientras que ToggleButton mantiene
    // un estado (activado/desactivado) --> info chat

    private final Button copyFormatButton = new Button("Copy format");
    private final Button pasteFormatButton = new Button("Paste format");

    private CustomizeFigure.Format copiedFormat = null;

    private Map<Effects, CheckBox> buttons = new EnumMap<>(Effects.class);

    // Tipo operacion
    private final Map<Operations, Button> operationButtons = new EnumMap<>(Operations.class);

    private final ComboBox<BorderType> borderTypeTopCombo = new ComboBox<>();

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

    private final CheckBox shadowButton = new CheckBox("Darken");
    private final CheckBox brightenButton = new CheckBox("Brighten");
    private final CheckBox horizontalMirrorButton = new CheckBox("Horizontal Mirror");
    private final CheckBox verticalMirrorButton = new CheckBox("Vertical Mirror");

    private EnumSet<Effects> currentEffects;

    // Tipo operacion
    private final EnumMap<Operations, String> operationsMap = new EnumMap<>(Operations.class);

    public PaintPane(CanvasState<CustomizeFigure> canvasState, StatusPane statusPane) {
        this.canvasState = canvasState;
        this.statusPane = statusPane;

        Label effectsLabel = new Label("Effects:");

        HBox buttonsBar = new HBox(10); // espacio horizontal entre controles

        List<CheckBox> buttons = List.of(shadowButton, brightenButton, horizontalMirrorButton, verticalMirrorButton);
        // TODO: Optimizar buttons.put(Effects.SHADOW, shadowButton);

        for (CheckBox effect : buttons) {
            effect.setMinWidth(90);
            effect.setCursor(Cursor.HAND);
        }

        buttonsBar.getChildren().add(effectsLabel);
        buttonsBar.getChildren().addAll(buttons);

        buttonsBar.setPadding(new Insets(5, 5, 5, 120));
        buttonsBar.setStyle("-fx-background-color: #999;");
        buttonsBar.setPrefHeight(20); // altura fija para la barra superior

        setTop(buttonsBar);

        /*
         * private void setCheckBox(CheckBox checkBox, Consumer<Boolean> state) {
         * checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
         * state.accept(newVal);
         * });
         * }
         *
         * private void setCheckBox(CheckBox checkBox, Runnable checkOn, Runnable
         * checkOff) {
         * checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
         * if (newVal) {
         * checkOn.run();
         * } else {
         * checkOff.run();
         * }
         * });
         * }
         *
         */

        // i.e: setCheckbox(shadowButton, this::aplicarSombra, this::removerSombra);

        List<ToggleButton> toolsArr = List.of(selectionButton, rectangleButton, circleButton, squareButton,
                ellipseButton, deleteButton);
        ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }

        List<Button> operationsArr = List.of(divideHButton, divideVButton, multiplyButton, transferButton);
        for (Button tool : operationsArr) {
            tool.setMinWidth(90);
            tool.setCursor(Cursor.HAND);
        }

        // TODO. Mapeamos las operaciones a los botones --> Ver como OPTIMIZARLO
        operationButtons.put(Operations.DIVIDE_H, divideHButton);
        operationButtons.put(Operations.DIVIDE_V, divideVButton);
        operationButtons.put(Operations.MULTIPLY, multiplyButton);
        operationButtons.put(Operations.TRANSFER, transferButton);

        // "Activamos" las operaciones
        for (Map.Entry<Operations, Button> entry : operationButtons.entrySet()) {
            Operations operation = entry.getKey();
            Button button = entry.getValue();
            button.setOnAction(event -> {
                if (selectedFigure == null) {
                    return; // si la figura no esta seleccionada, no hacemos nada
                }
                Optional<String> input = showInputDialog(operation.getDescription(), operation.getInstructions());
                input.ifPresent(parameters -> {
                    try {
                        List<CustomizeFigure> result = operation.execute(selectedFigure, parameters);

                        canvasState.addAll(result);

                        redrawCanvas();
                    } catch (Exception e) {
                        statusPane.updateStatus("Error: " + e.getMessage());
                    }
                });
            });
        }

        Label operationsLabel = new Label("Operations:");
        VBox buttonsBox = new VBox(10);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().add(borderTypeCombo);
        buttonsBox.getChildren().add(fillColorPicker);
        buttonsBox.getChildren().addAll(copyFormatButton, pasteFormatButton); // Agrego los nuevos controles a la barra
        buttonsBox.getChildren().add(operationsLabel);
        buttonsBox.getChildren().addAll(operationsArr);

        // lateral
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);

        borderTypeCombo.getItems().addAll(BorderType.values());
        borderTypeCombo.setValue(BorderType.SOLID);

        copyFormatButton.setMinWidth(90);
        pasteFormatButton.setMinWidth(90);
        borderTypeCombo.setMinWidth(90);

        shadowButton.setOnAction(e -> {
            if (selectedFigure != null) {
                if (shadowButton.isSelected()) {
                    selectedFigure.addFilter(Effects.SHADOW);
                } else {
                    selectedFigure.removeFilter(Effects.SHADOW);
                }
            }
            if (shadowButton.isSelected()) {
                currentEffects.add(Effects.SHADOW);
            } else {
                currentEffects.remove(Effects.SHADOW);
            }
            redrawCanvas();
        });

        brightenButton.setOnAction(e -> {
            if (brightenButton.isSelected()) {
                currentEffects.add(Effects.BRIGHTENING);
                if (selectedFigure != null) {
                    selectedFigure.addFilter(Effects.BRIGHTENING);
                }
            } else {
                if (selectedFigure != null) {
                    selectedFigure.removeFilter(Effects.BRIGHTENING);
                }
                currentEffects.remove(Effects.BRIGHTENING);
            }
            redrawCanvas();
        });

        horizontalMirrorButton.setOnAction(e -> {
            if (selectedFigure != null) {
                selectedFigure.setHorizontalMirror(horizontalMirrorButton.isSelected());
                redrawCanvas();
            }
            if (horizontalMirrorButton.isSelected()) {
            }
        });

        verticalMirrorButton.setOnAction(e -> {
            if (selectedFigure != null) {
                selectedFigure.setVerticalMirror(verticalMirrorButton.isSelected());
                redrawCanvas();
            }
            if (horizontalMirrorButton.isSelected()) {
            }
        });

        copyFormatButton.setOnAction(e -> {
            if (selectedFigure != null) {
                copiedFormat = selectedFigure.getFormatCopy();
            }
        });

        pasteFormatButton.setOnAction(e -> {
            if (selectedFigure != null && copiedFormat != null) {
                selectedFigure.setFormat(copiedFormat);
                redrawCanvas();
            }
        });

        fillColorPicker.setOnAction(e -> {
            if (selectedFigure != null) {
                selectedFigure.changeColor(fillColorPicker.getValue());
                redrawCanvas();
            }
        });

        borderTypeCombo.setOnAction(e -> {
            if (selectedFigure != null) {
                selectedFigure.setBorderType(borderTypeCombo.getValue());
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
                        fillColorPicker.getValue(),
                        brightenButton.isSelected(), shadowButton.isSelected(),
                        horizontalMirrorButton.isSelected(), verticalMirrorButton.isSelected());
            } else if (circleButton.isSelected()) {
                //
                // TODO: Relacionado con lo de arriba
                // Aca hace el valor absoluto pero para que si ya chequeo que endPoint.x sea
                // menor que startpoint?
                //
                double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                newFigure = new CustomizeFigure(new Circle(startPoint, circleRadius), borderTypeCombo.getValue(),
                        fillColorPicker.getValue(),
                        brightenButton.isSelected(), shadowButton.isSelected(),
                        horizontalMirrorButton.isSelected(), verticalMirrorButton.isSelected());
            } else if (squareButton.isSelected()) {
                double size = Math.abs(endPoint.getX() - startPoint.getX());
                newFigure = new CustomizeFigure(new Square(startPoint, size), borderTypeCombo.getValue(),
                        fillColorPicker.getValue(),
                        brightenButton.isSelected(), shadowButton.isSelected(),
                        horizontalMirrorButton.isSelected(), verticalMirrorButton.isSelected());
            } else if (ellipseButton.isSelected()) {
                Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2,
                        (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                double sHorizontalAxis = Math.abs(endPoint.getX() - startPoint.getX());
                double sVerticalAxis = Math.abs(endPoint.getY() - startPoint.getY());
                newFigure = new CustomizeFigure(new Ellipse(centerPoint, sVerticalAxis, sHorizontalAxis),
                        borderTypeCombo.getValue(), fillColorPicker.getValue(),
                        brightenButton.isSelected(), shadowButton.isSelected(),
                        horizontalMirrorButton.isSelected(),
                        verticalMirrorButton.isSelected());
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
            if (!selectionButton.isSelected()) {
                selectedFigure = null;
                return;
            }
            Point eventPoint = new Point(event.getX(), event.getY());
            StringBuilder label = new StringBuilder("Selected: ");
            actOnSelection(eventPoint, label, (fig) -> {
                this.selectedFigure = fig;
                updateStatus(fig);
            },
                    (lastSeen) -> this.lastDragPoint = lastSeen, () -> {
                        this.selectedFigure = null;
                        this.lastDragPoint = null;
                        statusPane.updateStatus("No figure found");
                    });
            redrawCanvas();
        });

        canvas.setOnMouseDragged(event -> {
            if (!selectionButton.isSelected() || selectedFigure == null || lastDragPoint == null) {
                selectedFigure = null;
                return;
            }
            Point eventPoint = new Point(event.getX(), event.getY());
            double diffX = eventPoint.getX() - lastDragPoint.getX();
            double diffY = eventPoint.getY() - lastDragPoint.getY();
            selectedFigure.moveD(diffX, diffY);
            lastDragPoint = eventPoint;
            redrawCanvas();
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

    private Optional<String> showInputDialog(String title, String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title); // -->Dividirh, dividirV, multiplicar, translate
        dialog.setHeaderText(title); // -->Dividirh, dividirV, multiplicar, translate
        dialog.setContentText(contentText); // -->Ingrese un valor de N /ingrese una coordenada

        return dialog.showAndWait();
    }

    private void actOnSelection(Point eventPoint, StringBuilder label, Consumer<CustomizeFigure> selected,
            Consumer<Point> lastSeen, Runnable ifNotFound) {
        boolean found = false;
        for (CustomizeFigure figure : canvasState) {
            if (figure.figureBelongs(eventPoint)) {
                found = true;
                selected.accept(figure);
                lastSeen.accept(eventPoint);
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

    private void updateStatus(CustomizeFigure fig) {
        updateComboBoxes(fig);
        updateCheckBoxes(fig);
    }

    private void updateComboBoxes(CustomizeFigure figure) {
        fillColorPicker.setValue(figure.getOriginalColor());
        borderTypeCombo.setValue(figure.getBorderType());
    }

    private void updateCheckBoxes(CustomizeFigure figure) {
        currentEffects = figure.getFilters();
        horizontalMirrorButton.setSelected(figure.isHMirror());
        verticalMirrorButton.setSelected(figure.isVMirror());
        brightenButton.setSelected(currentEffects.contains(Effects.BRIGHTENING));
        shadowButton.setSelected(currentEffects.contains(Effects.SHADOW));
    }

}
