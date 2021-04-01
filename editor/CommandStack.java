package pobj.pinboard.editor;

import java.util.ArrayDeque;
import java.util.Deque;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {
	
	private Deque<Command> undo = new ArrayDeque<Command>();;
	private Deque<Command> redo = new ArrayDeque<Command>();;
	
	public void addCommand(Command cmd) {
		undo.push(cmd);
		redo.clear();
	}
	
	public void undo() {
		Command com = undo.pop();
		com.undo();
		redo.push(com);
	}
	
	public void redo() {
		Command com = redo.pop();
		com.execute();
		undo.push(com);
	}
	
	public boolean isUndoEmpty() {
		return undo.isEmpty();
	}
	
	public boolean isRedoEmpty() {
		return redo.isEmpty();
	}

}
