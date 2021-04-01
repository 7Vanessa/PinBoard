package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class ToolSelection implements Tool {
	
	private double posX, posY;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		if(e.isShiftDown())
			i.getSelection().toggleSelect(i.getBoard(), e.getX(), e.getY());
		else
			i.getSelection().select(i.getBoard(), e.getX(), e.getY());
		
		posX = e.getX();
		posY = e.getY();
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		
		for(Clip clip : i.getSelection().getContents())
			clip.move(e.getX()-posX, e.getY()-posY);
		
		posX = e.getX();
		posY = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getSelection().drawFeedback(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Selection";
	}

}
