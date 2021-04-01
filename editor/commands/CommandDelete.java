package pobj.pinboard.editor.commands;

import javafx.scene.canvas.Canvas;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.Selection;

public class CommandDelete implements Command {
	
	private Board board;
	private Canvas canvas;
	private Selection selection;
	
	public CommandDelete(Board board, Canvas canvas, Selection selection) {
		this.board = board;
		this.canvas = canvas;
		this.selection = selection;
	}
	
	@Override
	public void execute() {
		board.removeClip(selection.getContents());
		board.draw(canvas.getGraphicsContext2D());
		
	}

	@Override
	public void undo() {
		board.addClip(selection.getContents());
		board.draw(canvas.getGraphicsContext2D());
	}

}
