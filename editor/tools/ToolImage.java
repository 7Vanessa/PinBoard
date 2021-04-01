package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolImage implements Tool {
	
	private File file;
	private ClipImage clipImage;
	
	public ToolImage(File file) {
		this.file = file;
	}

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		clipImage = new ClipImage(e.getX(), e.getY(), file);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		clipImage.setGeometry(e.getX(), e.getY(), clipImage.getWidth(), clipImage.getHeight());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		//clipImage.setGeometry(e.getX(), e.getY(), clipImage.getWidth(), clipImage.getHeight());
		//i.getBoard().addClip(clipImage);
		CommandAdd cAdd = new CommandAdd(i, clipImage);
		cAdd.execute();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.drawImage(clipImage.getImage(), 0, 0);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Image";
	}

}
