package com.github.sameer_s.sbscorer;

import com.github.sameer_s.sbscorer.question.QuestionParser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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
		VBox root = new VBox();

		QuestionParser qp = new QuestionParser();
		qp.init();

		root.getChildren().addAll(qp);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}