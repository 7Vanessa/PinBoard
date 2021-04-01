package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolEllipse implements Tool {

	private double x, y;
	private ClipEllipse clipEllipse;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
 		clipEllipse = new ClipEllipse(e.getX(), e.getY(), e.getX(), e.getY(), Color.RED);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		if(e.getX() < x )
			clipEllipse.setGeometry(e.getX(), e.getY(), x, y);
		else
			clipEllipse.setGeometry(x, y, e.getX(), e.getY());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		if(e.getX() < x && y < e.getY())
			clipEllipse.setGeometry(e.getX(), y, x, e.getY());
		
		if(x < e.getX() && y > e.getY())
			clipEllipse.setGeometry(x, e.getY() , e.getX(), y);
		
		CommandAdd cmd = new CommandAdd(i, clipEllipse);
		cmd.execute();
		
		//i.getBoard().addClip(clipEllipse);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		gc.strokeOval(clipEllipse.getLeft(), clipEllipse.getTop(), clipEllipse.getWidth(), clipEllipse.getHeight());
	}

	@Override
	public String getName(EditorInterface editor) {
		return "Ellipse";
	}
	
}
