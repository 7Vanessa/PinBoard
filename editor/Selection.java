package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection {
	
	private List<Clip> listeClip;
	//Afin de faciliter la méthode drawFeedBack
	private List<Double> posLeft = new ArrayList<Double>();
	private List<Double> posRight = new ArrayList<Double>();
	private List<Double> posTop = new ArrayList<Double>();
	private List<Double> posBottom = new ArrayList<Double>();
	
	public Selection() {
		listeClip = new ArrayList<Clip>();
	}
	
	public void select(Board board, double x, double y) {
		listeClip.clear();
		posLeft.clear();
		posRight.clear();
		posTop.clear();
		posBottom.clear();
		
		for(Clip clip : board.getContents()) {
			if(clip.isSelected(x, y)) {
				listeClip.add(clip);
				posLeft.add(clip.getLeft());
				posRight.add(clip.getRight());
				posTop.add(clip.getTop());
				posBottom.add(clip.getBottom());
			}	
		}
	}
			
	
	public void toggleSelect(Board board, double x, double y) {
		for(Clip clip : board.getContents()) {
			if(clip.isSelected(x, y)) {
				if(listeClip.contains(clip)) {
					listeClip.remove(clip);
					posLeft.remove(clip.getLeft());
					posRight.remove(clip.getRight());
					posTop.remove(clip.getTop());
					posBottom.remove(clip.getBottom());
				}
				else {
					listeClip.add(clip);
					posLeft.add(clip.getLeft());
					posRight.add(clip.getRight());
					posTop.add(clip.getTop());
					posBottom.add(clip.getBottom());
				}
			}
		}
	}
	
	
	public void clear() {
		listeClip.clear();
	}
	
	
	public List<Clip> getContents() {
		return listeClip;
	}
	
	
	public List<Double> getPosLeft() {
		return posLeft;
	}
	
	
	public List<Double> getPosRight() {
		return posRight;
	}
	
	
	public List<Double> getPosTop() {
		return posTop;
	}
	
	
	public List<Double> getPosBottom() {
		return posBottom;
	}
	
	public void move(double x, double y) {
		posLeft.add(Collections.min(posLeft) + x);
		posRight.add(Collections.max(posRight) + x);
		posTop.add(Collections.min(posTop) + y);
		posBottom.add(Collections.max(posBottom) + y);
	}
	
	public void drawFeedback(GraphicsContext gc) {
		gc.strokeRect(Collections.min(posLeft), Collections.min(posTop), Collections.max(posRight)-Collections.min(posLeft), Collections.max(posBottom)-Collections.min(posTop));
	}
	
	public boolean isSelected(double x, double y) {
		System.out.println(x + " " + y);
		System.out.println(Collections.min(posLeft) + " " + Collections.min(posTop) + " " + Collections.max(posRight) + " " + Collections.max(posBottom));
		if ((x >= Collections.min(posLeft) && x <= Collections.max(posRight)) && (y >= Collections.min(posTop) && y <= Collections.max(posBottom))) {
			return true;
		}
		return false;
	}

}
