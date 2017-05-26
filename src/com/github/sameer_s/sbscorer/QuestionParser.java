package com.github.sameer_s.sbscorer;

import java.net.URL;
import java.util.Optional;

import org.apache.tika.Tika;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

public class QuestionParser extends VBox
{
	public QuestionParser()
	{
		super();
	}

	public void init()
	{
		Button getPdf = new Button("Get PDF");
		getPdf.setOnAction(event -> popup());

		this.getChildren().add(getPdf);
	}

	private void popup()
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Get PDF");
		dialog.setHeaderText("What is the PDF's URI?");
		dialog.setContentText("");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
			String uri = result.get();
			try
			{
				Tika tika = new Tika();
				System.out
						.println(tika.parseToString(new URL(uri).openStream()));
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}
