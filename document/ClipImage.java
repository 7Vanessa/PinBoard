package pobj.pinboard.document;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClipImage extends AbstractClip implements Clip {

	private File file;
	private Image image;
	
	public  ClipImage(double left, double top, File file){
		super(left, top, 0, 0, null);
		image = new Image("file://" + file.getAbsolutePath());
		setGeometry(left, top, image.getWidth(), image.getHeight());
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.drawImage(image, getLeft(), getTop());
	}

	@Override
	public ClipImage copy() {
		return new ClipImage(this.getLeft(), this.getTop(), file);
	}
	
	public Image getImage() {
		return image;
	}

}
