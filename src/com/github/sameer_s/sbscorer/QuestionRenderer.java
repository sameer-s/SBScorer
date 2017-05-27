package com.github.sameer_s.sbscorer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.pdfbox.tools.PDFToImage;

import javafx.scene.control.Pagination;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class QuestionRenderer extends VBox
{
	private Pagination page;
	private Path temp = null;

	public QuestionRenderer(double stageWidth)
	{
		// Button getPdf = new Button("Load PDF");
		// getPdf.setOnAction(event -> popup());
		// getPdf.setAlignment(Pos.CENTER);
		// this.getChildren().addAll(getPdf);
		page = new Pagination(1, 0);
		update(stageWidth);
	}

	public void update(double stageWidth)
	{
		double adjusted = (stageWidth * .38);
		if (temp != null)
		{
			page.setPageFactory(pageIndex ->
			{
				ImageView view = new ImageView();
				Image img = null;
				try
				{
					img = new Image(
							new FileInputStream(temp
									.resolve("file" + (pageIndex + 1) + ".jpg")
									.toFile()),
							adjusted, 1056 * (adjusted / 816), true, true);
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}

				view.setImage(img);
				return view;
			});
		}
		else
		{
			page.setPageFactory(pageIndex ->
			{
				Rectangle r = new Rectangle(adjusted, 1056 * (adjusted / 816));
				r.setFill(Color.WHITE);
				return r;
			});

			addPage();
		}
	}

	/* package-private */ void popup()
	{
		TextInputDialog dialog = new TextInputDialog(
				"https://science.energy.gov/~/media/wdts/nsb/pdf/HS-Sample-Questions/Sample-Set-1/round1.pdf");
		dialog.setTitle("Load PDF");
		dialog.setHeaderText("What is the PDF's URL?");
		dialog.setContentText("");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
			String url = result.get();
			try
			{
				init(new URL(url).openStream());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void init(InputStream stream)
	{
		try
		{
			temp = Files.createTempDirectory("SBScorer");
			Path file = temp.resolve("file.pdf");
			Files.copy(stream, file, StandardCopyOption.REPLACE_EXISTING);
			PDFToImage.main(new String[]
				{ file.toAbsolutePath().toString() });

			int len = temp.toFile()
					.listFiles((dir, name) -> name.endsWith("jpg")).length;
			System.out.println(len);

			page = new Pagination(len, 0);
			// page.setPageFactory(pageIndex ->

			update(this.getScene().getWindow().getWidth());
			addPage();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void addPage()
	{
		getChildren().removeAll(
				getChildren().stream().filter(n -> n instanceof Pagination)
						.collect(Collectors.toList()));
		getChildren().add(page);
	}
}