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
import javafx.scene.layout.*;

public class PaintPane extends BorderPane {

    private static final double CANVAS_WIDTH = 800;
    private static final double CANVAS_HEIGHT = 600;
    private static final int HORIZONTAL_SPACING = 10;
    private static final int VERTICAL_SPACING = HORIZONTAL_SPACING;
    private static final int PADDING = 5;
    private static final int EFFECTS_PADDING_LEFT = 120;
    private static final int EFFECTS_BAR_HEIGHT = 20;
    private static final int SIDEBAR_WIDTH = 100;

    private static final String SIDEBAR_STYLE = UIConstants.SIDEBAR_STYLE;
    private static final String EFFECTS_BAR_STYLE = UIConstants.EFFECTS_BAR_STYLE;

    private final CanvasState<CustomizeFigure> canvasState;

    private final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private final ToggleButton selectionButton = UIComponentFactory.createSelectButton();
    private final ToggleButton rectangleButton = UIComponentFactory.createRectangleButton();
    private final ToggleButton circleButton = UIComponentFactory.createCircleButton();
    private final ToggleButton squareButton = UIComponentFactory.createSquareButton();
    private final ToggleButton ellipseButton = UIComponentFactory.createEllipseButton();
    private final ToggleButton deleteButton = UIComponentFactory.createDeleteButton();

    private final Button divideHButton = UIComponentFactory.createDivideHButton();
    private final Button divideVButton = UIComponentFactory.createDivideVButton();
    private final Button multiplyButton = UIComponentFactory.createMultiplyButton();
    private final Button transferButton = UIComponentFactory.createTransferButton();

    private final ComboBox<BorderType> borderTypeCombo = UIComponentFactory.createBorderTypeComboBox();
    private final Button copyFormatButton = UIComponentFactory.createCopyFormatButton();
    private final Button pasteFormatButton = UIComponentFactory.createPasteFormatButton();
    private final ColorPicker fillColorPicker = UIComponentFactory.createColorPicker();
    private CustomizeFigure.Format copiedFormat = null;

    private Point startPoint;
    private CustomizeFigure selectedFigure;
    private Point lastDragPoint;

    private final StatusPane statusPane;

    private final CheckBox shadowButton = UIComponentFactory.createShadowCheckBox();
    private final CheckBox brightenButton = UIComponentFactory.createBrightenCheckBox();
    private final CheckBox horizontalMirrorButton = UIComponentFactory.createHorizontalMirrorCheckBox();
    private final CheckBox verticalMirrorButton = UIComponentFactory.createVerticalMirrorCheckBox();

    private final Map<Effects, CheckBox> effectsCheckBoxes = new EnumMap<>(Effects.class);
    private final Map<Mirrors, CheckBox> mirrorsCheckBoxes = new EnumMap<>(Mirrors.class);
    private final Map<ToggleButton, CustomizeFigureBuilder> builders = new HashMap<>();
    private ToggleButton currentToggle;

    public PaintPane(CanvasState<CustomizeFigure> canvasState, StatusPane statusPane) {
        this.canvasState = canvasState;
        this.statusPane = statusPane;
        setupEffectsBar();
        initializeVisuals();
        setupOperationButtons();
        setupVisualsCheckBoxes();

        setupFormatButtons();
        setupCanvasEvents();
        setupDeleteButton();

        setLeft(createSidebar());
        setRight(canvas);
    }

    // esto lo hacemos para asociar efecto con boton
    private void initializeVisuals() {
        effectsCheckBoxes.put(Effects.SHADOW, shadowButton);
        effectsCheckBoxes.put(Effects.BRIGHTENING, brightenButton);
        mirrorsCheckBoxes.put(Mirrors.HMIRROR, horizontalMirrorButton);
        mirrorsCheckBoxes.put(Mirrors.VMIRROR, verticalMirrorButton);
    }

    private void setupEffectsBar() {
        Label effectsLabel = new Label(UIConstants.EFFECTS_LABEL_TEXT);
        HBox buttonsBar = new HBox(HORIZONTAL_SPACING);
        List<CheckBox> effectButtons = List.of(shadowButton, brightenButton, horizontalMirrorButton,
                verticalMirrorButton);
        buttonsBar.getChildren().add(effectsLabel);
        buttonsBar.getChildren().addAll(effectButtons);
        buttonsBar.setPadding(new Insets(PADDING, PADDING, PADDING, EFFECTS_PADDING_LEFT));
        buttonsBar.setStyle(EFFECTS_BAR_STYLE);
        buttonsBar.setPrefHeight(EFFECTS_BAR_HEIGHT);
        setTop(buttonsBar);
    }

    private void setupOperationButtons() {
        divideHButton.setOnAction(event -> executeOperation(Operations.DIVIDE_H));
        divideVButton.setOnAction(event -> executeOperation(Operations.DIVIDE_V));
        multiplyButton.setOnAction(event -> executeOperation(Operations.MULTIPLY));
        transferButton.setOnAction(event -> executeOperation(Operations.TRANSFER));
    }

    private void setupBuilderButtons() {
    
    }
    // creemos con ivonne que no lo vamos a usar

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

            // Esto pasa solo si tenes algun ToggleButton prendido
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
            actOnSelection(eventPoint, label, (fig) -> {
            }, (pt) -> {
            },
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

    private void configureToggleButtons(List<ToggleButton> buttons, ToggleGroup group) {
        for (ToggleButton button : buttons) {
            button.setToggleGroup(group);
        }
    }

    private <E> void setupToggleCheckBoxes(Map<E, CheckBox> map, BiConsumer<E, Boolean> toggleFunc) {
        for (Map.Entry<E, CheckBox> entry : map.entrySet()) {
            E key = entry.getKey();
            CheckBox cb = entry.getValue();
            cb.setOnAction(e -> {
                toggleFunc.accept(key, cb.isSelected());
                redrawCanvas();
            });
        }
    }

    private void setupVisualsCheckBoxes() {
        setupToggleCheckBoxes(effectsCheckBoxes, (effect, enabled) -> {
            if (isFigureNonNull(selectedFigure)) {
                if (enabled)
                    selectedFigure.addFilter(effect);
                else
                    selectedFigure.removeFilter(effect);
            }
        });
        setupToggleCheckBoxes(mirrorsCheckBoxes, (mirror, enabled) -> {
            if (isFigureNonNull(selectedFigure)) {
                selectedFigure.setMirror(mirror, enabled);
            }
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

    private void setupToggleButtons(ToggleButton figureBuilders, CustomizeFigureBuilder builder) {
    }

    private <T extends Enum<T>> EnumSet<T> getCurrentVisuals(Class<T> enumType, Map<T, CheckBox> visuals) {
        return visuals.entrySet().stream().filter((entry) -> entry.getValue().isSelected()).map(Map.Entry::getKey)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(enumType)));
    }

    private CustomizeFigure createFigure(Point startPoint, Point endPoint) {
        if (currentToggle == null) {
            return null;
        }
        System.out.println("No retorne null aca gordo");
        return builders.get(currentToggle).constructor(startPoint, endPoint, borderTypeCombo.getValue(),
                fillColorPicker.getValue(), getCurrentVisuals(Effects.class, effectsCheckBoxes),
                getCurrentVisuals(Mirrors.class, mirrorsCheckBoxes));
    }

    private VBox createSidebar() {
        List<ToggleButton> toolsArr = List.of(selectionButton, rectangleButton, circleButton, squareButton,
                ellipseButton, deleteButton);
        ToggleGroup tools = new ToggleGroup();
        configureToggleButtons(toolsArr, tools);

        List<Button> operationsArr = List.of(divideHButton, divideVButton, multiplyButton, transferButton);

        Label operationsLabel = new Label(UIConstants.OPERATIONS_LABEL_TEXT);
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

    private void updateComboBoxes(CustomizeFigure figure) {
        fillColorPicker.setValue(figure.getOriginalColor());
        borderTypeCombo.setValue(figure.getBorderType());
    }

    private void updateStatus(CustomizeFigure fig) {
        updateComboBoxes(fig);
        updateCheckBoxes(fig);
    }

    private <E extends Enum<E>> void syncCheckBoxes(Map<E, CheckBox> map, Collection<E> active) {
        map.values().forEach(cb -> cb.setSelected(false));
        for (E keys : active) {
            CheckBox cb = map.get(keys);
            if (cb != null) {
                cb.setSelected(true);
            }
        }
    }

    private void updateCheckBoxes(CustomizeFigure figure) {
        syncCheckBoxes(mirrorsCheckBoxes, figure.getMirrors().keySet());
        syncCheckBoxes(effectsCheckBoxes, figure.getFilters());
    }

}
