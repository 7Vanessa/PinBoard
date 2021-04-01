package pobj.pinboard.editor.commands;

import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements Command {
	
	private double x, y;
	private EditorInterface editor;
	private ClipRect clRect;
	
	public CommandMove(EditorInterface editor, ClipRect clRect, double x, double y) {
		this.editor = editor;
		this.clRect = clRect;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		editor.getBoard().removeClip(clRect);
		clRect.move(x, y);
		editor.getBoard().addClip(clRect);
	}

	@Override
	public void undo() {
		clRect.move(-1*x, -1*y);
		editor.getBoard().removeClip(clRect);
		editor.getBoard().addClip(clRect);
	}

}
