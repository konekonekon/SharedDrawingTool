package recognition;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Circle implements Shape {

	Point center;
	double radius;
	
	public Circle(Point p, double r){
		center = p;
		radius = r;
	}
	
/*	@Override
	public void draw(Rectangle rectangle, Graphics2D g2d){
		calculateRayon(rectangle);
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(3));
		
		int left = center.x - (int)rayon;
		int top = center.y - (int)rayon;
		
		g2d.drawOval(left, top, (int)rayon*2, (int)rayon*2);
	}*/
	
/*	public void drawTestCircles(Rectangle rec, Graphics2D g2d){ //To test
		calculateRayon(rec); //each time call this function?
		
		int leftForSmall = center.x - (int)smallRayon;
		int topForSmall = center.y - (int)smallRayon;
		int leftForBig = center.x - (int)bigRayon;
		int topForBig = center.y - (int)bigRayon;

		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(1));
		//draw center point
		g2d.fillOval(center.x-2, center.y-2, 4, 4);
		//draw rayons	    
		g2d.drawOval(leftForSmall, topForSmall, (int)smallRayon*2, (int)smallRayon*2);
		g2d.drawOval(leftForBig, topForBig, (int)bigRayon*2, (int)bigRayon*2);
	}*/
	

	/*** To get each extremity of points ***/
	public static Rectangle getBounds(ArrayList<Point> aLine){
		boolean first = true;
		int left = 0, top = 0 , right = 0, bottom = 0;
		
		for (Point p : aLine){
			/*** Initialization ***/
			if (first){
				left = right = p.x;
				top = bottom = p.y;
				first = false;
			}
			/*** Compare to find extremities ***/
			left = Math.min(p.x, left);
			top = Math.min(p.y, top);
			right = Math.max(p.x, right);
			bottom = Math.max(p.y, bottom);
		}
		return new Rectangle(left, top, (right - left), (bottom - top));
	}
	
/*	public boolean recognizeCircle(Rectangle rec, ArrayList<Point> currentStroke) {
		this.calculateRayon(rec);
		
		if (currentStroke.size() > 3){
			for (Point p : currentStroke) {
				if (p.distance(center) < smallRayon){
					if (p.distance(center) > bigRayon)
						System.out.println("false");
						return false;
				}
			}
		}
		System.out.println("true");
		return true;
	}*/
	
	public boolean near(Point p) {
		double d = p.distance(center);
		return d > radius * 0.9 && d < radius * 1.1;
	}
	
	public boolean allNear(ArrayList<Point> cloudPoints) {
		for (Point p : cloudPoints){
			if (!near(p))
				return false;
		}
		return true;
	}
	
	public static Shape recognize(ArrayList<Point> line) {
		/*** Points number condition ***/
		if (line.size() < 5)
			return null;
		
		Rectangle rec = getBounds(line);
		int x = rec.x + (rec.width / 2);
		int y = rec.y + (rec.height / 2);
		
		Point center = new Point(x,y);
		double radius = ((rec.width / 2) + (rec.height / 2)) / 2;
		Circle estimateCircle = new Circle(center, radius);
		
		if (estimateCircle.allNear(line))
			return estimateCircle;
		return null;
	}

	@Override
	public void draw(Graphics2D g2) {
		calculateRayon(rectangle);
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(3));
		
		int left = center.x - (int)rayon;
		int top = center.y - (int)rayon;
		
		g2d.drawOval(left, top, (int)rayon*2, (int)rayon*2);
	}
}
