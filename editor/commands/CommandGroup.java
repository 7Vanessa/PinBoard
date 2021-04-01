package pobj.pinboard.editor.commands;

import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command {
	
	private EditorInterface editor;
	private ClipGroup clGroup;
	
	public CommandGroup(EditorInterface editor, List<Clip> toAdd) {
		this.editor = editor;
		clGroup = new ClipGroup();
	}

	@Override
	public void execute() {
		for(Clip clip : clGroup.getClips()) {
			editor.getBoard().removeClip(clip);
		}
		editor.getBoard().addClip(clGroup);
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(clGroup);
		for(Clip clip : clGroup.getClips()) {
			editor.getBoard().addClip(clip);
		}
	}

}
