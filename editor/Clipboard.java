package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard {
	
	private static Clipboard clipboard = new Clipboard();
	private List<Clip> pressePapier;
	private List<ClipboardListener> clipListener;
	
	private Clipboard() {
		pressePapier = new ArrayList<Clip>();
		clipListener = new ArrayList<ClipboardListener>();
	}
	
	public static Clipboard getInstance() {
		return clipboard;
	}
	
	public void copyToClipboard(List<Clip> clips) {
		for(Clip clip : clips)
			pressePapier.add(clip.copy());
		updateListener();
	}
	
	public List<Clip> copyFromClipboard() {
		List<Clip> res = new ArrayList<Clip>();
		for(Clip clip : pressePapier) 
			res.add(clip.copy());
		updateListener();
		return res;
	}
	
	public void clear() {
		pressePapier.clear();
		updateListener();
	}
	
	public boolean isEmpty() {
		if(pressePapier.isEmpty())
			return true;
		return false;
	}
	
	public void addListener(ClipboardListener listener) {
		clipListener.add(listener);
	}
	
	public void removeListener(ClipboardListener listener) {
		if(clipListener.contains(listener))
			clipListener.remove(listener);
	}
	
	public void updateListener() {
		for(ClipboardListener cbListener : clipListener)
			cbListener.clipboardChanged();
	}

}
