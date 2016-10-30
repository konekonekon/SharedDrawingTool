package SharedCanvas;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Dot extends Shape {

	Point p;

	public Dot(Point point){
		this.p = point;
	}

	public static Shape recognize(ArrayList<Point> line) {
		Dot dot = null;
		if (line.size() == 1){
			Point p = line.get(0);
			dot = new Dot(p);
		}
		return dot;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.fillOval(p.x-3, p.y-3, 6, 6);
	}
	
	@Override
	public String encode() {
		return "Dot " + Integer.toString(p.x) + " " + Integer.toString(p.y);
	}
	
	public static Shape decode(String data) {
		String[] elements = data.split(" ");
		Dot aDot = null;
		Point p = new Point(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
		aDot = new Dot(p);
		return aDot;
	}
}
