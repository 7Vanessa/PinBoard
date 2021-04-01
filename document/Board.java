package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {
	
	private List<Clip> conteneur;
	
	public Board() {
		conteneur = new ArrayList<Clip>();
	}
	
	public List<Clip> getContents() {
		return conteneur;
	}
	
	public void addClip(Clip clip) {
		conteneur.add(clip);
	}
	
	public void addClip(List<Clip> clip) {
		conteneur.addAll(clip);
	}
	
	public void removeClip(Clip clip) {
		conteneur.remove(clip);
	}
	
	public void removeClip(List<Clip> clip) {
		conteneur.removeAll(clip);
	}
	
	 public void draw(GraphicsContext gc) {
		 gc.setFill(Color.WHITE);
		 gc.fillRect(0, 0,gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		 for(Clip clip : conteneur)
			 clip.draw(gc);
	 }

}
