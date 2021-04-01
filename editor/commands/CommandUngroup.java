package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command {
	
	private EditorInterface editor;
	private ClipGroup clGroup;
	
	public CommandUngroup(EditorInterface editor, ClipGroup toAdd) {
		this.editor = editor;
		clGroup = toAdd;
	}
	
	@Override
	public void execute() {
		editor.getBoard().removeClip(clGroup);
		for(Clip clip : clGroup.getClips()) {
			editor.getBoard().addClip(clip);
		}
	}

	@Override
	public void undo() {
		for(Clip clip : clGroup.getClips()) {
			editor.getBoard().removeClip(clip);
		}
		editor.getBoard().addClip(clGroup);
	}

}
