package recognition;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class DrawSpace extends JComponent implements MouseListener, MouseMotionListener {

	private ArrayList<Point> currentLine;
	private ArrayList<ArrayList<Point>> lines;
	private ArrayList<Point> aShape;
	private boolean reshapeDemande;

	public DrawSpace() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		lines = new ArrayList<ArrayList<Point>>();
		reshapeDemande = false;
	}

	// TODO : color, thickness for line/text
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		RenderingHints rh = g2.getRenderingHints ();
	    rh.put (RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints (rh);
	    
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.black);

		for (ArrayList<Point> line : lines){
			this.drawStroke(line, g2);
		}
		
		if (aShape != null) {
			for (Point p : aShape) {
				this.drawAnchor(p, g2);
			}
			//remove line that user drew
			this.displayShape(aShape, g2);
			/*if (reshapeDemande == true)
				this.displayShape(aShape, g2);*/
		}
	}

	public void displayShape(ArrayList<Point> shapeToDisplay, Graphics2D g2){
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		Point startPoint = shapeToDisplay.get(0);
		Point previous = null;
		for (Point p : shapeToDisplay){
			
			if (previous == null){
				previous = p;
				continue;
			}
			
			if (p.distance(startPoint) < 10)
				g2.drawLine(previous.x, previous.y, startPoint.x, startPoint.y);
			else
				g2.drawLine(p.x, p.y, previous.x, previous.y);
			previous = p;
		}
	}
	
	public void drawAnchor(Point p, Graphics2D g2){
		g2.setColor(Color.RED);
		g2.fillOval(p.x, p.y, 5, 5);
	}
	
/*	public boolean isCircle(){
		for (Integer anAngle : aShape.angles){
			if (anAngle < 90)
				return true;
		}
		return false;
	}*/
	
	public String getShapeName(int numAngle){
		
        switch(numAngle){
        	case 1: return "Line";
        	case 2: return "1 angle";
            case 3: return "Triangle";
            case 4: return "Rectangle";
            case 5: return "Pentagon";
            case 6: return "Hexagon";
            case 7: return "Heptagon";
            case 8: return "Octagon";
            /*default: if (this.isCircle() == true)
            			return "Circle";
            		else
            			return "Unknown";*/
        }
        return null;
    }
	
	public ArrayList<Point> recognizeShape(ArrayList<Point> line){
		Point previous = null;
		Point anchor = null;
		//ArrayList<Point> shapePoints = new ArrayList<Point>();
		//Shape shapeElements = new Shape(null,null);
		ArrayList<Point> shape = new ArrayList<Point>();
		
		for (Point current : line){
			if (anchor == null) {
				anchor = current;
				//shapeElements.points.add(anchor);
				//shapeElements = new Shape(anchor, new Integer(null));
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
			
			if (angle > 30){
				if (current.distance(anchor)> 25){
					if (current.distance(previous) > 10) {
						anchor = previous;
						shape.add(anchor);
						/*shapeElements.points.add(anchor);
						Integer i = new Integer((int)angle);
						shapeElements.angles.add(i);*/					
						previous = current;
					}
				}
			} else {
				previous = current;
			}
			
			if (current == line.get(line.size()-1)){
				anchor = current;
				shape.add(anchor);
			}
		}
		/* Compare distance between start point and end point.
		 * if the distance is small, ignore end point, consider end point equal to start point.
		 */ 
		Point start = shape.get(0);
		Point end = shape.get(shape.size()-1);
		if (start.distance(end) < 5){
			end = start;
		}
		return shape;
	}
	
	public double calculateAngle(Point p0, Point p1, Point p2){
		/*** cos Delta = vectorU*vectorV / length(vectorU)*length(vectorV) 
		 * 	 Delta = acos Delta. acos = inverse of cos = cos small(-1)
		 * 
		 *   vectorU = p0 to p1 = u, vectorV = p0 to p2 = v
		 *   length(vectorU) = lengthU, length(vectorV) = lengthV ***/

		Point u = new Point((p1.x - p0.x), (p1.y - p0.y));
		Point v = new Point((p2.x - p1.x), (p2.y - p1.y));
		double dotProduct = (u.x * v.x) + (u.y * v.y);
		double lengthU = p0.distance(p1);
		double lengthV = p1.distance(p2);
		double length = lengthU * lengthV;
		
		double cosDelta = dotProduct / length;
		System.out.println("cosDelta : " + cosDelta);
		double delta = Math.acos(cosDelta);
		delta = delta * 180 / Math.PI;
		System.out.println("Delta : " + delta);
		
		return delta;
	}
	
	public void drawStroke(ArrayList<Point> line, Graphics2D g2) {
		int i = 0;
		while (i < line.size() - 1) {
			Point p0 = line.get(i);
			Point p1 = line.get(i + 1);
			g2.drawLine(p0.x, p0.y, p1.x, p1.y);			
			i++;
		}
	}

	public void mouseClicked(MouseEvent e) {
		/*if (e.getClickCount() == 2){
			currentLine = null;
			//if (e.getLocationOnScreen())
			reshapeDemande = true;
			repaint();
		}*/

	}

	public void mousePressed(MouseEvent e) {
		currentLine = new ArrayList<Point>();
		lines.add(currentLine);
		currentLine.add(e.getPoint());
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		aShape = recognizeShape(currentLine);
		System.out.println("This figure is " + getShapeName(aShape.size()-1));
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
		currentLine.add(e.getPoint());
		repaint();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}

}
