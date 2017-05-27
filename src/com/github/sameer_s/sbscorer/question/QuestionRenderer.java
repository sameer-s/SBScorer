package com.github.sameer_s.sbscorer.question;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.pdfbox.tools.PDFToImage;

import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class QuestionRenderer extends VBox
{

	public QuestionRenderer()
	{

	}
	// public QuestionRenderer(String src)
	// {
	// HTMLEditor html = new HTMLEditor();
	// html.setHtmlText(src);
	// getChildren().add(html);
	//
	// WebView view = new WebView();
	// WebEngine engine = view.getEngine();
	// engine.loadContent(src);
	// getChildren().add(view);
	// }

	public void init(InputStream stream)
	{
		try
		{
			Path temp = Files.createTempDirectory("SBScorer");
			Path file = temp.resolve("file.pdf");
			Files.copy(stream, file, StandardCopyOption.REPLACE_EXISTING);
			PDFToImage.main(new String[] { file.toAbsolutePath().toString() });

			System.out.println("a");
			int len = temp.toFile().listFiles((dir, name) -> name.matches("file*.jpg")).length;
			System.out.println(len);

			Pagination pagination = new Pagination(len, 0);
			System.out.println("b");
			pagination.setPageFactory(new Callback<Integer, Node>()
			{
				@Override
				public Node call(Integer pageIndex)
				{
					ImageView view = new ImageView();
					view.setImage(new Image(temp.resolve("file" + pageIndex + ".jpg").toString()));
					return view;
				}
			});

			getChildren().add(pagination);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}