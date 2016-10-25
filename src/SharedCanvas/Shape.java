package SharedCanvas;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Shape {
	
	public static Shape recognize(ArrayList<Point> line) {
		return null;
	}
	
	public void draw(Graphics2D g2) {}
	
	public String encode() {
		return null;
	}
	
	public static Shape decode(String data) {
		return null;
	}
	
	/*public String encodeUndo() {
		return null;
	}
	
	public static Shape decodeUndo(String data) {
		return null;
	}
	
	public String encodeRedo() {
		return null;
	}
	
	public static Shape decodeRedo(String data) {
		return null;
	}*/
}
