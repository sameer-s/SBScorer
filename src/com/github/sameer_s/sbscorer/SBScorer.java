package com.github.sameer_s.sbscorer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SBScorer extends Application
{
	public static void main(String... args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Science Bowl Scorer");
		primaryStage.setMaximized(true);

		TilePane root = new TilePane();
		root.setHgap(8);
		root.setPrefColumns(3);

		root.setStyle("-fx-border-color: red;");

		QuestionRenderer qr = new QuestionRenderer(primaryStage.getWidth());

		primaryStage.widthProperty().addListener(
				(obs, old, current) -> qr.update(current.doubleValue()));

		VBox tools = new VBox();

		Timer matchTimer = new Timer();
		matchTimer.setTime(60 * 1000 * 8);
		matchTimer.setTextFill(Color.BLACK);
		matchTimer.setFont(Font.font("Monospace", 72));

		Timer questionTimer = new Timer();
		questionTimer.setTime(1000 * 10);
		questionTimer.setTextFill(Color.BLACK);
		questionTimer.setFont(Font.font("Monospace", 72));

		Button getPdf = new Button("Load PDF");
		getPdf.setOnAction(event -> qr.popup());
		getPdf.setAlignment(Pos.CENTER);

		tools.getChildren().addAll(getPdf, matchTimer);

		root.getChildren().addAll(tools, qr);

		matchTimer.start();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}