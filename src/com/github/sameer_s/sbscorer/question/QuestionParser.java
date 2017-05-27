package com.github.sameer_s.sbscorer.question;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

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
		TextInputDialog dialog = new TextInputDialog(
				"https://science.energy.gov/~/media/wdts/nsb/pdf/HS-Sample-Questions/Sample-Set-1/round1.pdf");
		dialog.setTitle("Get PDF");
		dialog.setHeaderText("What is the PDF's URL?");
		dialog.setContentText("");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
			String url = result.get();
			QuestionRenderer qr = new QuestionRenderer();
			try
			{
				qr.init(new URL(url).openStream());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			getChildren().add(qr);

			// try
			// {
			// ContentHandler handler = new ToXMLContentHandler();
			//
			// AutoDetectParser parser = new AutoDetectParser();
			// Metadata metadata = new Metadata();
			// try (InputStream stream = new URL(url).openStream())
			// {
			// parser.parse(stream, handler, metadata);
			// getChildren().add(new QuestionRenderer(handler.toString()));
			// }
			// // System.out
			// // .println(tika.parseToString(new URL(uri).openStream()));
			// }
			// catch (Exception e)
			// {
			// throw new RuntimeException(e);
			// }
		}
	}
}
