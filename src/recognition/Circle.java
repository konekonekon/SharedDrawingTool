package recognition;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Circle extends Shape {

	Point center;
	double radius;
	
	public Circle(Point p, double r){
		center = p;
		radius = r;
	}
	
/*	public void drawTestCircles(Graphics2D g2d){	
		int leftForSmall = center.x - (int)(radius * 0.9);
		int topForSmall = center.y - (int)(radius * 0.9);
		int leftForBig = center.x - (int)(radius * 1.1);
		int topForBig = center.y - (int)(radius * 1.1);

		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(1));
		g2d.fillOval(center.x-2, center.y-2, 4, 4);
		g2d.drawOval(leftForSmall, topForSmall, (int)(radius * 0.9 *2), (int)(radius * 0.9 *2));
		g2d.drawOval(leftForBig, topForBig, (int)(radius * 1.1 *2), (int)(radius * 1.1 *2));
	}*/
	

	/* To get each extremity of points */
	public static Rectangle getBounds(ArrayList<Point> aLine){
		boolean first = true;
		int left = 0, top = 0 , right = 0, bottom = 0;
		
		for (Point p : aLine){
			/* Initialization */
			if (first){
				left = right = p.x;
				top = bottom = p.y;
				first = false;
			}
			/* Compare to find extremities */
			left = Math.min(p.x, left);
			top = Math.min(p.y, top);
			right = Math.max(p.x, right);
			bottom = Math.max(p.y, bottom);
		}
		return new Rectangle(left, top, (right - left), (bottom - top));
	}
	
	public boolean near(Point p) {
		double d = p.distance(center);
		double diff = radius * 0.15;
		return d > radius - diff && d < radius + diff;
	}
	
	public boolean allNear(ArrayList<Point> points) {
		for (Point p : points){
			if (!near(p))
				return false;
		}
		return true;
	}
	
	public static Shape recognize(ArrayList<Point> line) {
		/* Points number condition */
		if (line.size() < 5)
			return null;
		
		Rectangle rec = getBounds(line);
		int x = rec.x + (rec.width / 2);
		int y = rec.y + (rec.height / 2);
		
		Point center = new Point(x,y);
		double radius = ((rec.width / 2) + (rec.height / 2)) / 2;
		Circle estimatedCircle = new Circle(center, radius);
		
		if (estimatedCircle.allNear(line))
			return estimatedCircle;
		return null;
	}

	@Override
	public void draw(Graphics2D g2) {		
		int left = center.x - (int)radius;
		int top = center.y - (int)radius;
		
		g2.drawOval(left, top, (int)radius*2, (int)radius*2);
	}
}
