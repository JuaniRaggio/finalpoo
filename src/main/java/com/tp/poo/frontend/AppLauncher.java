package com.tp.poo.frontend;

import com.tp.poo.backend.CanvasState;
import com.tp.poo.frontend.Figures.CustomizeFigure;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		CanvasState<CustomizeFigure> canvasState = new CanvasState<>();
		MainFrame frame = new MainFrame(canvasState);
		Scene scene = new Scene(frame);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> System.exit(0));
	}

}
