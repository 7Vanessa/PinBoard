package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup extends AbstractClip implements Composite {
	
	private double left, top, right, bottom;
	private Color color;
	private List<Clip> listeClip;
	
	public ClipGroup(Color color) {
		super(0, 0, 0, 0, color);
		listeClip = new ArrayList<Clip>();
	}
	
	public ClipGroup() {
		super(0, 0, 0, 0, Color.BLACK);
		listeClip = new ArrayList<Clip>();
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.strokeRect(getLeft(), getTop(), getWidth(), getHeight());
		for(Clip clip : listeClip) {
			clip.draw(ctx);
		}
	}

	@Override
	public Clip copy() {
		ClipGroup copy = new ClipGroup(this.color);
		for(Clip clip : listeClip) {
			copy.addClip(clip.copy());
		}
		return copy;
	}

	@Override
	public List<Clip> getClips() {
		return listeClip;
	}

	@Override
	public void addClip(Clip toAdd) {
		listeClip.add(toAdd);
		searchPos();
	}

	@Override
	public void removeClip(Clip toRemove) {
		if(listeClip.contains(toRemove)) {
			listeClip.remove(toRemove);
	
		}
		searchPos();
	}
	
	public void clear() {
		listeClip.clear();
	}
	
	public double getLeft() {
		return left;
	}
	
	public double getRight() {
		return right;
	}
	
	public double getTop() {
		return top;
	}
	
	public double getBottom() {
		return bottom;
	}
	
	public void searchPos() {
		
		top=2147483647;
		left=2147483647;
		right=0;
		bottom=0;
		
		for(Clip clip : listeClip) {
			
			if(left > clip.getLeft())
				left = clip.getLeft();
			
			if(top > clip.getTop())
				top = clip.getTop();
			
			if(right < clip.getRight())
				right = clip.getRight();
			
			if(bottom < clip.getBottom())
				bottom = clip.getBottom();
		}
	}
	
	public void setGeometry(double left, double top, double right, double bottom) {
		move(right-left, bottom-top);
	}
	
	public void move(double x, double y) {
		for(Clip clip : listeClip) {
			clip.move(x,  y);
		}
		searchPos();
	}

}
