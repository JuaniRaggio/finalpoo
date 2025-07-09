package com.tp.poo.frontend;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.tp.poo.backend.model.figures.Point;
import com.tp.poo.frontend.Figures.CustomizeFigure;
import com.tp.poo.backend.CanvasState;

import com.tp.poo.frontend.Figures.CustomizeFigureBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class PaintPane extends BorderPane {

    private final static double CANVAS_WIDTH = UIConstants.DEFAULT_CANVAS_WIDTH;
    private final static double CANVAS_HEIGHT = UIConstants.DEFAULT_CANVAS_HEIGHT;
    private final static int HORIZONTAL_SPACING = UIConstants.DEFAULT_HORIZONTAL_SPACING;
    private final static int VERTICAL_SPACING = UIConstants.DEFAULT_VERTICAL_SPACING;
    private final static int PADDING = UIConstants.DEFAULT_PADDING;
    private final static int EFFECTS_PADDING_LEFT = UIConstants.DEFAULT_EFFECTS_PADDING_LEFT;
    private final static int EFFECTS_BAR_HEIGHT = UIConstants.DEFAULT_EFFECTS_BAR_HEIGHT;
    private final static int SIDEBAR_WIDTH = UIConstants.DEFAULT_SIDEBAR_WIDTH;

    private final static String SIDEBAR_STYLE = UIConstants.SIDEBAR_STYLE;
    private final static String EFFECTS_BAR_STYLE = UIConstants.EFFECTS_BAR_STYLE;

    private final CanvasState<CustomizeFigure> canvasState;
    private final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private final ToggleButton selectionButton = UIComponentFactory.createSelectButton();

    private final ToggleButton rectangleButton = UIComponentFactory
            .createFigureButton(CustomizeFigureBuilder.RECTANGLE);
    private final ToggleButton circleButton = UIComponentFactory.createFigureButton(CustomizeFigureBuilder.CIRCLE);
    private final ToggleButton squareButton = UIComponentFactory.createFigureButton(CustomizeFigureBuilder.SQUARE);
    private final ToggleButton ellipseButton = UIComponentFactory.createFigureButton(CustomizeFigureBuilder.ELLIPSE);

    private final ToggleButton deleteButton = UIComponentFactory.createDeleteButton();

    private final Button divideHButton = UIComponentFactory.createOperationButton(Operations.DIVIDE_H);
    private final Button divideVButton = UIComponentFactory.createOperationButton(Operations.DIVIDE_V);
    private final Button multiplyButton = UIComponentFactory.createOperationButton(Operations.MULTIPLY);
    private final Button transferButton = UIComponentFactory.createOperationButton(Operations.TRANSFER);

    private final ComboBox<BorderType> borderTypeCombo = UIComponentFactory.createBorderTypeComboBox();
    private final Button copyFormatButton = UIComponentFactory.createFormatButton(UIConstants.COPY_FORMAT_BUTTON_TEXT);
    private final Button pasteFormatButton = UIComponentFactory
            .createFormatButton(UIConstants.PASTE_FORMAT_BUTTON_TEXT);
    private final ColorPicker fillColorPicker = UIComponentFactory.createColorPicker();
    private CustomizeFigure.Format copiedFormat = null;

    private Point startPoint;
    private CustomizeFigure selectedFigure;
    private Point lastDragPoint;
    private final StatusPane statusPane;

    private final CheckBox shadowButton = UIComponentFactory.createCheckBox(Effects.SHADOW);
    private final CheckBox brightenButton = UIComponentFactory.createCheckBox(Effects.BRIGHTENING);
    private final CheckBox horizontalMirrorButton = UIComponentFactory.createCheckBox(Mirrors.HMIRROR);
    private final CheckBox verticalMirrorButton = UIComponentFactory.createCheckBox(Mirrors.VMIRROR);

    private final Map<Effects, CheckBox> effectsCheckBoxes = Map.of(
            Effects.SHADOW, shadowButton,
            Effects.BRIGHTENING, brightenButton);

    private final Map<Mirrors, CheckBox> mirrorsCheckBoxes = Map.of(
            Mirrors.HMIRROR, horizontalMirrorButton,
            Mirrors.VMIRROR, verticalMirrorButton);

    private final Map<ToggleButton, CustomizeFigureBuilder> builders = Map.of(
            rectangleButton, CustomizeFigureBuilder.RECTANGLE,
            squareButton, CustomizeFigureBuilder.SQUARE,
            ellipseButton, CustomizeFigureBuilder.ELLIPSE,
            circleButton, CustomizeFigureBuilder.CIRCLE);

    private ToggleGroup toolsGroup = new ToggleGroup();

    public PaintPane(CanvasState<CustomizeFigure> canvasState, StatusPane statusPane) {
        this.canvasState = canvasState;
        this.statusPane = statusPane;
        setupHandlerEvents();
        setupLayout();
    }

    private void setupLayout() {
        setupEffectsBar();
        setLeft(createSidebar());
        setRight(canvas);
    }

    private void setupHandlerEvents() {
        setupOperationButtons();
        setupVisualsCheckBoxes();
        setupFormatButtons();
        setupCanvasEvents();
        setupDeleteButton();
    }

    private void setupEffectsBar() {
        Label effectsLabel = new Label(UIConstants.EFFECTS_LABEL_TEXT);
        HBox buttonsBar = new HBox(HORIZONTAL_SPACING);
        buttonsBar.getChildren().add(effectsLabel);
        buttonsBar.getChildren().addAll(shadowButton, brightenButton, horizontalMirrorButton, verticalMirrorButton);
        buttonsBar.setPadding(new Insets(PADDING, PADDING, PADDING, EFFECTS_PADDING_LEFT));
        buttonsBar.setStyle(EFFECTS_BAR_STYLE);
        buttonsBar.setPrefHeight(EFFECTS_BAR_HEIGHT);
        setTop(buttonsBar);
    }

    private VBox createSidebar() {
        List<ToggleButton> toolsList = List.of(selectionButton, rectangleButton, circleButton, squareButton,
                ellipseButton, deleteButton);
        List<Button> operationsList = List.of(divideHButton, divideVButton, multiplyButton, transferButton);

        configureToggleButtons(toolsList, toolsGroup);

        Label operationsLabel = new Label(UIConstants.OPERATIONS_LABEL_TEXT);
        VBox buttonsBox = new VBox(VERTICAL_SPACING);
        buttonsBox.getChildren().addAll(toolsList);
        buttonsBox.getChildren().addAll(borderTypeCombo, fillColorPicker, copyFormatButton, pasteFormatButton);
        buttonsBox.getChildren().add(operationsLabel);
        buttonsBox.getChildren().addAll(operationsList);
        buttonsBox.setPadding(new Insets(PADDING));
        buttonsBox.setStyle(SIDEBAR_STYLE);
        buttonsBox.setPrefWidth(SIDEBAR_WIDTH);
        return buttonsBox;
    }

    private boolean isFigureNonNull(CustomizeFigure figure) {
        return figure != null;
    }

    private void configureToggleButtons(List<ToggleButton> buttons, ToggleGroup group) {
        for (ToggleButton button : buttons) {
            button.setToggleGroup(group);
        }
    }

    private void setupOperationButtons() {
        divideHButton.setOnAction(event -> executeOperation(Operations.DIVIDE_H));
        divideVButton.setOnAction(event -> executeOperation(Operations.DIVIDE_V));
        multiplyButton.setOnAction(event -> executeOperation(Operations.MULTIPLY));
        transferButton.setOnAction(event -> executeOperation(Operations.TRANSFER));
    }

    private void setupFormatButtons() {

        pasteFormatButton.setOnAction(e -> {
            if (isFigureNonNull(selectedFigure) && copiedFormat != null) {
                selectedFigure.setFormat(copiedFormat);
                redrawCanvas();
            }
        });

        setupFormatAction(copyFormatButton::setOnAction, figure -> copiedFormat = figure.getFormatCopy());
        setupFormatAction(fillColorPicker::setOnAction, figure -> figure.changeColor(fillColorPicker.getValue()));
        setupFormatAction(borderTypeCombo::setOnAction, figure -> figure.setBorderType(borderTypeCombo.getValue()));
    }

    private void setupFormatAction(Consumer<EventHandler<ActionEvent>> eventHandler, Consumer<CustomizeFigure> action) {
        eventHandler.accept(e -> {
            if (isFigureNonNull(selectedFigure)) {
                action.accept(selectedFigure);
                redrawCanvas();
            }
        });
    }

    private void setupCanvasEvents() {

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

        canvas.setOnMouseMoved(this::handleMouseMoved);
        canvas.setOnMouseClicked(this::handleMousePressed);
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);

    }

    private void handleMouseMoved(MouseEvent event) {
        Point eventPoint = new Point(event.getX(), event.getY());
        StringBuilder label = new StringBuilder();
        actOnSelection(eventPoint, label, (fig) -> {}, (pt) -> {},
            () -> statusPane.updateStatus(eventPoint.toString()));
    }

    private void handleMouseDragged(MouseEvent event) {
        handleMouseMoved(event);
        if (isFigureNonNull(selectedFigure)) {
            selectedFigure.moveD(event.getX() - lastDragPoint.getX(), event.getY() - lastDragPoint.getY());
            lastDragPoint = new Point(event.getX(), event.getY());
            updateStatus(selectedFigure);
            redrawCanvas();
        }
    }

    private void handleMousePressed(MouseEvent event) {
        startPoint = new Point(event.getX(), event.getY());
        if (!selectionButton.isSelected()) {
            selectedFigure = null;
            return;
        }
        StringBuilder label = new StringBuilder(UIConstants.SELECTED_LOG_TEXT);
        actOnSelection(startPoint, label, (fig) -> {
            this.selectedFigure = fig;
            updateStatus(fig);
        },
                (lastSeen) -> this.lastDragPoint = lastSeen, () -> {
                    this.selectedFigure = null;
                    this.lastDragPoint = null;
                    statusPane.updateStatus(UIConstants.NO_FIGURE_FOUND_MESSAGE);
                });
        redrawCanvas();
    }

    private <E> void setupToggleCheckBoxes(Map<E, CheckBox> map, BiConsumer<E, Boolean> toggleFunc) {
        for (Map.Entry<E, CheckBox> entry : map.entrySet()) {
            E key = entry.getKey();
            CheckBox cb = entry.getValue();
            cb.setOnAction(e -> {
                if (isFigureNonNull(selectedFigure)) {
                    toggleFunc.accept(key, cb.isSelected());
                    redrawCanvas();
                }
            });
        }
    }

    private void setupVisualsCheckBoxes() {
        setupToggleCheckBoxes(effectsCheckBoxes, (effect, enabled) -> selectedFigure.setFilter(effect, enabled));
        setupToggleCheckBoxes(mirrorsCheckBoxes, (mirror, enabled) -> selectedFigure.setMirror(mirror, enabled));
    }

    private void setupDeleteButton() {
        deleteButton.setOnAction(event -> {
            this.canvasState.remove(selectedFigure);
            selectedFigure = null;
            redrawCanvas();
        });
    }

    private void executeOperation(Operations operation) {
        if (!isFigureNonNull(selectedFigure)) {
            return;
        }
        Optional<String> input = showInputDialog(operation.toString(), operation.getInstructions());
        input.ifPresent(parameters -> {
            try {
                List<CustomizeFigure> result = operation.execute(selectedFigure, parameters);
                canvasState.addAll(result);
                selectedFigure = null;
                redrawCanvas();
            } catch (IllegalArgumentException e) {
                statusPane.updateStatus(UIConstants.OUTPUT_ERROR_PREFIX + e.getMessage());
            }
        });
    }

    private boolean isValidDrawing(Point start, Point end) {
        return start != null && end.getX() >= start.getX() && end.getY() >= start.getY();
    }

    private <T extends Enum<T>> EnumSet<T> getCurrentVisuals(Class<T> enumType, Map<T, CheckBox> visuals) {
        return visuals.entrySet().stream().filter((entry) -> entry.getValue().isSelected()).map(Map.Entry::getKey)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(enumType)));
    }

    private CustomizeFigure createFigure(Point startPoint, Point endPoint) {
        try {
            Toggle selectedToggle = toolsGroup.getSelectedToggle();
            CustomizeFigureBuilder builder = builders.get(selectedToggle);
            return builder.constructor(startPoint,
                    endPoint,
                    borderTypeCombo.getValue(),
                    fillColorPicker.getValue(),
                    getCurrentVisuals(Effects.class, effectsCheckBoxes),
                    getCurrentVisuals(Mirrors.class, mirrorsCheckBoxes));
        } catch (NullPointerException ex) {
            return null;
        }
    }

    private Optional<String> showInputDialog(String title, String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(title);
        dialog.setContentText(contentText);
        return dialog.showAndWait();
    }

    private void actionIfFound(Point eventPoint, CustomizeFigure figure, StringBuilder label,
            Consumer<CustomizeFigure> selected,
            Consumer<Point> lastSeen) {
        selected.accept(figure);
        lastSeen.accept(eventPoint);
        label.append(figure);
        statusPane.updateStatus(label.toString());
    }

    private void actOnSelection(Point eventPoint, StringBuilder label, Consumer<CustomizeFigure> selected,
            Consumer<Point> lastSeen, Runnable ifNotFound) {
        canvasState.stream().filter((figure) -> figure.figureBelongs(eventPoint)).findFirst()
                .ifPresentOrElse((figure) -> actionIfFound(eventPoint, figure, label, selected, lastSeen),
                        ifNotFound);
    }

    private void redrawCanvas() {
        gc.clearRect(UIConstants.UPPER_LEFT_X, UIConstants.UPPER_LEFT_Y, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(UIConstants.DEFAULT_FIGURE_LINE_WIDTH);
        for (CustomizeFigure figure : canvasState) {
            figure.format(gc, selectedFigure);
        }
    }

    private void updateComboBoxes(CustomizeFigure figure) {
        fillColorPicker.setValue(figure.getOriginalColor());
        borderTypeCombo.setValue(figure.getBorderType());
    }

    private void updateCheckBoxes(CustomizeFigure figure) {
        syncCheckBoxes(mirrorsCheckBoxes, figure.getMirrors().keySet());
        syncCheckBoxes(effectsCheckBoxes, figure.getFilters());
    }

    private void updateStatus(CustomizeFigure fig) {
        updateComboBoxes(fig);
        updateCheckBoxes(fig);
    }

    private <E extends Enum<E>> void syncCheckBoxes(Map<E, CheckBox> map, Collection<E> active) {
        map.values().forEach(cb -> cb.setSelected(UIConstants.TURNED_OFF));
        for (E keys : active) {
            CheckBox cb = map.get(keys);
            if (cb != null) {
                cb.setSelected(UIConstants.TURNED_ON);
            }
        }
    }

}
