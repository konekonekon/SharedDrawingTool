package recognition;

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

}
