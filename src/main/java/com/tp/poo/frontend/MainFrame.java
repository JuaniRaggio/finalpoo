package com.tp.poo.frontend;

import com.tp.poo.backend.CanvasState;
import com.tp.poo.frontend.Figures.CustomizeFigure;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {

    public MainFrame(CanvasState<CustomizeFigure> canvasState) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        getChildren().add(new PaintPane(canvasState, statusPane));
        getChildren().add(statusPane);
    }

}
