package recognition;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Dot implements Shape {

	Point p;

	public Dot(Point p){
		//super();
		this.p = p;
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
		g2.setColor(Color.RED);
		g2.fillOval(p.x-2, p.y-2, 4, 4);
	}

}
