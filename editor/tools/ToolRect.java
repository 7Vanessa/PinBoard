package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolRect implements Tool {
	
	private double x, y;
	private ClipRect clipRect;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
 		clipRect = new ClipRect(e.getX(), e.getY(), e.getX(), e.getY(), Color.BLUE);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		if(x >= e.getX() &&  y >= e.getY())
			clipRect.setGeometry(e.getX(),e.getY(), x, y);
		
		if(y <= e.getX() && y <= e.getY()) 
			clipRect.setGeometry(x, y, e.getX(), e.getY());
		
		if(x >= e.getX() && y <= e.getY())
			clipRect.setGeometry(e.getX(), y, x ,e.getY());
		
		if(x <=e.getX() && y >= e.getY())
			clipRect.setGeometry(x, e.getY(),e.getX() , y);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		if(e.getX()< x && y < e.getY())
			clipRect.setGeometry(e.getX(), y, x, e.getY());
		
		if(x < e.getX() && y > e.getY())
			clipRect.setGeometry(x, e.getY(), e.getX(), y);
		
		CommandAdd cmd = new CommandAdd(i, clipRect);
		cmd.execute();
		
		//i.getBoard().addClip(clipRect);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.strokeRect(clipRect.getLeft(), clipRect.getTop(), clipRect.getWidth(), clipRect.getHeight());
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Box";
	}

}
