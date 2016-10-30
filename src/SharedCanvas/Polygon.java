package SharedCanvas;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Polygon extends Shape {
	ArrayList<Point> anchors;

	public Polygon(ArrayList<Point> points) {
		anchors = points;
	}

	public static double calculateAngle(Point p0, Point p1, Point p2) {
		/* cos Delta = vectorU*vectorV / length(vectorU)*length(vectorV) 
		 * Delta = acos Delta. acos = inverse of cos = cos small(-1)
		 * 
		 * vectorU = p0 to p1 = u, vectorV = p0 to p2 = v 
		 * length(vectorU) = lengthU, length(vectorV) = lengthV
		 */

		Point u = new Point((p1.x - p0.x), (p1.y - p0.y));
		Point v = new Point((p2.x - p1.x), (p2.y - p1.y));
		double dotProduct = (u.x * v.x) + (u.y * v.y);
		double lengthU = p0.distance(p1);
		double lengthV = p1.distance(p2);
		double length = lengthU * lengthV;

		double cosDelta = dotProduct / length;
		double delta = Math.acos(cosDelta);
		delta = delta * 180 / Math.PI;

		return delta;
	}

	public static Shape recognize(ArrayList<Point> line) {
		Point previous = null;
		Point anchor = null;
		ArrayList<Point> shape = new ArrayList<Point>();

		for (Point current : line) {
			/* Initialization */
			if (anchor == null) {
				anchor = current;
				shape.add(anchor);
				continue;
			}
			if (previous == null) {
				previous = current;
				continue;
			}
			if (previous.equals(current))
				continue;

			double angle = calculateAngle(anchor, previous, current);

			/* Condition to consider a point as anchor */
			if (angle > 25) {
				if (current.distance(previous) > 10) {
					anchor = previous;
					shape.add(anchor);
					previous = current;
				}
			} else {
				previous = current;
			}
			/* Add last point to shape */
			if (current == line.get(line.size() - 1)) {
				anchor = current;
				shape.add(anchor);
			}
		}
		/*
		 * Compare distance between start point and end point. if the distance
		 * is small, ignore end point, consider end point equal to start point.
		 */
		if (shape.size() < 3) { // For small line
			if (shape.get(0).distance(shape.get(shape.size() - 1)) < 5) {
				shape.get(shape.size() - 1).setLocation(shape.get(0));
			}
		} else {
			if (shape.get(0).distance(shape.get(shape.size() - 1)) < 30) {
				shape.get(shape.size() - 1).setLocation(shape.get(0));
			}
		}
		Polygon aPolygon = new Polygon(shape);
		return aPolygon;
	}

	@Override
	public void draw(Graphics2D g2) {
		Point previous = null;
		for (Point p : anchors) {
			/* Draw anchors */
			g2.fillOval(p.x - 3, p.y - 3, 6, 6);
			/* Draw lines between each anchor */
			if (previous == null) {
				previous = p;
				continue;
			}
			g2.drawLine(p.x, p.y, previous.x, previous.y);
			previous = p;
		}
	}

	@Override
	public String encode() {
		String aString = "Polygon";
		for (Point p : anchors)
			aString += " " + Integer.toString(p.x) + " "
					+ Integer.toString(p.y);
		return aString;
	}

	public static Shape decode(String data) {
		String[] elements = data.split(" ");
		Polygon aPolygon = null;
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = 1; i < elements.length; i += 2)
			points.add(new Point(Integer.parseInt(elements[i]), Integer
					.parseInt(elements[i + 1])));
		aPolygon = new Polygon(points);
		return aPolygon;
	}
}