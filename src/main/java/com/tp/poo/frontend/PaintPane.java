package com.tp.poo.frontend;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.tp.poo.backend.CanvasState;
import com.tp.poo.backend.model.figures.*;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

    private static final double CANVAS_WIDTH = 800;
    private static final double CANVAS_HEIGHT = 600;
    private static final int BUTTON_MIN_WIDTH = 90;
    private static final int HORIZONTAL_SPACING = 10;
    private static final int VERTICAL_SPACING = HORIZONTAL_SPACING;
    private static final int PADDING = 5;
    private static final int EFFECTS_PADDING_LEFT = 120;
    private static final int EFFECTS_BAR_HEIGHT = 20;
    private static final int SIDEBAR_WIDTH = 100;
    private static final String SIDEBAR_STYLE = "-fx-background-color: #999";
    private static final String EFFECTS_BAR_STYLE = SIDEBAR_STYLE;

    private final CanvasState<CustomizeFigure> canvasState;

    private final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

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

    private final ComboBox<BorderType> borderTypeCombo = new ComboBox<>();
    private final Button copyFormatButton = new Button("Copy format");
    private final Button pasteFormatButton = new Button("Paste format");
    private final ColorPicker fillColorPicker = new ColorPicker(Color.YELLOW);

    private CustomizeFigure.Format copiedFormat = null;

    private final Map<Operations, Button> operationButtons = new EnumMap<>(Operations.class);

    private Point startPoint;
    private CustomizeFigure selectedFigure;
    private Point lastDragPoint;

    private final StatusPane statusPane;

    private final CheckBox shadowButton = new CheckBox("Darken");
    private final CheckBox brightenButton = new CheckBox("Brighten");
    private final CheckBox horizontalMirrorButton = new CheckBox("Horizontal Mirror");
    private final CheckBox verticalMirrorButton = new CheckBox("Vertical Mirror");

    private final EffectManager effectManager = new EffectManager();
    private final MirrorManager mirrorManager = new MirrorManager();

    public PaintPane(CanvasState<CustomizeFigure> canvasState, StatusPane statusPane) {
        this.canvasState = canvasState;
        this.statusPane = statusPane;
        setupEffectsBar();
        setupSidebar();
        setupOperationButtons();
        setupEffectCheckBoxes();
        setupMirrorCheckBoxes();
        setupFormatButtons();
        setupCanvasEvents();
        setupDeleteButton();
        setLeft(createSidebar());
        setRight(canvas);
    }

    private void setupEffectsBar() {
        Label effectsLabel = new Label("Effects:");
        HBox buttonsBar = new HBox(HORIZONTAL_SPACING);
        List<CheckBox> effectButtons = List.of(shadowButton, brightenButton, horizontalMirrorButton,
                verticalMirrorButton);
        configureButtons(effectButtons);
        buttonsBar.getChildren().add(effectsLabel);
        buttonsBar.getChildren().addAll(effectButtons);
        buttonsBar.setPadding(new Insets(PADDING, PADDING, PADDING, EFFECTS_PADDING_LEFT));
        buttonsBar.setStyle(EFFECTS_BAR_STYLE);
        buttonsBar.setPrefHeight(EFFECTS_BAR_HEIGHT);
        setTop(buttonsBar);
    }

    private void setupSidebar() {
        borderTypeCombo.getItems().addAll(BorderType.values());
        borderTypeCombo.setValue(BorderType.SOLID);
        configureButtons(List.of(copyFormatButton, pasteFormatButton, borderTypeCombo));
    }

    private void setupOperationButtons() {
        operationButtons.put(Operations.DIVIDE_H, divideHButton);
        operationButtons.put(Operations.DIVIDE_V, divideVButton);
        operationButtons.put(Operations.MULTIPLY, multiplyButton);
        operationButtons.put(Operations.TRANSFER, transferButton);
        for (Map.Entry<Operations, Button> entry : operationButtons.entrySet()) {
            Operations operation = entry.getKey();
            Button button = entry.getValue();
            button.setOnAction(event -> executeOperation(operation));
        }
    }

    private void setupEffectCheckBoxes() {
        setupEffectCheckBox(shadowButton, Effects.SHADOW);
        setupEffectCheckBox(brightenButton, Effects.BRIGHTENING);
    }

    private void setupMirrorCheckBoxes() {
        setupMirrorCheckBox(horizontalMirrorButton, true);
        setupMirrorCheckBox(verticalMirrorButton, false);
    }

    private void setupFormatButtons() {
        copyFormatButton.setOnAction(e -> {
            if (isFigureNonNull(selectedFigure)) {
                copiedFormat = selectedFigure.getFormatCopy();
            }
        });

        pasteFormatButton.setOnAction(e -> {
            if (isFigureNonNull(selectedFigure) && copiedFormat != null) {
                selectedFigure.setFormat(copiedFormat);
                redrawCanvas();
            }
        });

        setupFormatAction(fillColorPicker, figure -> figure.changeColor(fillColorPicker.getValue()));
        setupFormatAction(borderTypeCombo, figure -> figure.setBorderType(borderTypeCombo.getValue()));
    }

    private void setupFormatAction(Control control, Consumer<CustomizeFigure> action) {
        if (control instanceof ColorPicker) {
            ((ColorPicker) control).setOnAction(e -> {
                if (isFigureNonNull(selectedFigure)) {
                    action.accept(selectedFigure);
                    redrawCanvas();
                }
            });
        } else if (control instanceof ComboBox) {
            ((ComboBox<?>) control).setOnAction(e -> {
                if (isFigureNonNull(selectedFigure)) {
                    action.accept(selectedFigure);
                    redrawCanvas();
                }
            });
        }
    }

    private boolean isFigureNonNull(CustomizeFigure figure) {
        return figure != null;
    }

    private void setupCanvasEvents() {
        canvas.setOnMousePressed(event -> {
            startPoint = new Point(event.getX(), event.getY());
        });

        canvas.setOnMouseReleased(event -> {
            Point endPoint = new Point(event.getX(), event.getY());
            if (!isValidDrawing(startPoint, endPoint)) {
                return;
            }

            CustomizeFigure newFigure = createFigure(startPoint, endPoint);
            if (isFigureNonNull(newFigure)) {
                this.canvasState.add(newFigure);
                startPoint = null;
                redrawCanvas();
            }
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
    }

    private void setupDeleteButton() {
        deleteButton.setOnAction(event -> {
            if (isFigureNonNull(selectedFigure)) {
                this.canvasState.remove(selectedFigure);
                selectedFigure = null;
                redrawCanvas();
            }
        });
    }

    private void configureButtons(List<? extends Control> buttons) {
        for (Control button : buttons) {
            button.setMinWidth(BUTTON_MIN_WIDTH);
            button.setCursor(Cursor.HAND);
        }
    }

    private void configureToggleButtons(List<ToggleButton> buttons, ToggleGroup group) {
        for (ToggleButton button : buttons) {
            button.setMinWidth(BUTTON_MIN_WIDTH);
            button.setToggleGroup(group);
            button.setCursor(Cursor.HAND);
        }
    }

    private void setupEffectCheckBox(CheckBox checkBox, Effects effect) {
        checkBox.setOnAction(e -> {
            effectManager.toggleEffect(effect, checkBox.isSelected(), selectedFigure);
            redrawCanvas();
        });
    }

    private void setupMirrorCheckBox(CheckBox checkBox, boolean isHorizontal) {
        MirrorManager.MirrorType mirrorType = isHorizontal ? MirrorManager.MirrorType.HORIZONTAL
                : MirrorManager.MirrorType.VERTICAL;

        checkBox.setOnAction(e -> {
            mirrorManager.toggleMirror(mirrorType, checkBox.isSelected(), selectedFigure);
            redrawCanvas();
        });
    }

    private void executeOperation(Operations operation) {
        if (selectedFigure == null) {
            return;
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
    }

    private boolean isValidDrawing(Point start, Point end) {
        return start != null && end.getX() >= start.getX() && end.getY() >= start.getY();
    }

    private CustomizeFigure createFigure(Point startPoint, Point endPoint) {
        FigureType figureType = getSelectedFigureType();
        if (figureType == null) {
            return null;
        }

        Figure figure = figureType.createFigure(startPoint, endPoint);
        return new CustomizeFigure(figure,
                borderTypeCombo.getValue(), fillColorPicker.getValue(),
                brightenButton.isSelected(), shadowButton.isSelected(),
                horizontalMirrorButton.isSelected(), verticalMirrorButton.isSelected());
    }

    private FigureType getSelectedFigureType() {
        if (rectangleButton.isSelected())
            return FigureType.RECTANGLE;
        if (circleButton.isSelected())
            return FigureType.CIRCLE;
        if (squareButton.isSelected())
            return FigureType.SQUARE;
        if (ellipseButton.isSelected())
            return FigureType.ELLIPSE;
        return null;
    }

    private VBox createSidebar() {
        List<ToggleButton> toolsArr = List.of(selectionButton, rectangleButton, circleButton, squareButton,
                ellipseButton, deleteButton);
        ToggleGroup tools = new ToggleGroup();
        configureToggleButtons(toolsArr, tools);

        List<Button> operationsArr = List.of(divideHButton, divideVButton, multiplyButton, transferButton);
        configureButtons(operationsArr);

        Label operationsLabel = new Label("Operations:");
        VBox buttonsBox = new VBox(VERTICAL_SPACING);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().add(borderTypeCombo);
        buttonsBox.getChildren().add(fillColorPicker);
        buttonsBox.getChildren().addAll(copyFormatButton, pasteFormatButton);
        buttonsBox.getChildren().add(operationsLabel);
        buttonsBox.getChildren().addAll(operationsArr);

        buttonsBox.setPadding(new Insets(PADDING));
        buttonsBox.setStyle(SIDEBAR_STYLE);
        buttonsBox.setPrefWidth(SIDEBAR_WIDTH);

        return buttonsBox;
    }

    private Optional<String> showInputDialog(String title, String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(title);
        dialog.setContentText(contentText);
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
                label.append(figure);
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
        effectManager.syncWithFigure(figure);
        mirrorManager.syncWithFigure(figure);
        horizontalMirrorButton.setSelected(mirrorManager.isMirrorActive(MirrorManager.MirrorType.HORIZONTAL));
        verticalMirrorButton.setSelected(mirrorManager.isMirrorActive(MirrorManager.MirrorType.VERTICAL));
        brightenButton.setSelected(effectManager.isEffectActive(Effects.BRIGHTENING));
        shadowButton.setSelected(effectManager.isEffectActive(Effects.SHADOW));
    }
}
