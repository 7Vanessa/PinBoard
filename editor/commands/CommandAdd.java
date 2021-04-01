package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandAdd implements Command {
	
	private List<Clip> listeClip;
	private EditorInterface editor;
	
	public CommandAdd(EditorInterface editor, Clip toAdd) {
		listeClip = new ArrayList<Clip>();
		this.editor = editor;
		listeClip.add(toAdd);
	}
	
	public CommandAdd(EditorInterface editor, List<Clip> toAdd) {
		listeClip = toAdd;
		this.editor = editor;
	}
	
	@Override public void execute() {
		editor.getBoard().addClip(listeClip);
	}
	
	@Override public void undo() {
		editor.getBoard().removeClip(listeClip);
	}

}
