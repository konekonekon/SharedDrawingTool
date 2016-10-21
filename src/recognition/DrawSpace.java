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
	private Rectangle bounds;
	private Point center;
	private double rayon;
	private double smallRayon;
	private double bigRayon;
	//private boolean reshapeDemande;

	public DrawSpace() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		lines = new ArrayList<ArrayList<Point>>();
		//reshapeDemande = false;
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
			/*for (Point p : aShape) {
				this.drawAnchor(p, g2);
			}*/
			this.drawAnchor(aShape, g2);
			//remove line that user drew
			this.displayShape(aShape, g2);
			//this.drawCircle(bounds, g2);
			
			/*if (reshapeDemande == true)
				this.displayShape(aShape, g2);*/
		}
	}

	public void displayShape(ArrayList<Point> shapeToDisplay, Graphics2D g2){
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		Point previous = null;
		
		for (Point p : shapeToDisplay){
			
			if (previous == null){
				previous = p;
				continue;
			}
			g2.drawLine(p.x, p.y, previous.x, previous.y);
			/*if (p.distance(startPoint) < 10)
				g2.drawLine(previous.x, previous.y, startPoint.x, startPoint.y);
			else
				g2.drawLine(p.x, p.y, previous.x, previous.y);*/
			previous = p;
		}
	}
	
	public void drawAnchor(ArrayList<Point> shape, Graphics2D g2){
		for (Point p : shape){
			g2.setColor(Color.RED);
			g2.fillOval(p.x, p.y, 5, 5);
		}
	}
	
	public void calculateRayon(Rectangle rec){
		int x = rec.x + (rec.width / 2);
		int y = rec.y + (rec.height / 2);
		center = new Point(x,y);
		rayon = ((rec.width / 2) + (rec.height / 2)) / 2;
		smallRayon = rayon-(rayon*0.1);
		bigRayon = rayon+(rayon*0.1);
		//global? create new class Circle?
	}
	
	public void drawCircle(Rectangle rec, Graphics2D g2d){
		this.calculateRayon(rec);
		
		int leftForSmall = center.x - (int)smallRayon;
		int topForSmall = center.y - (int)smallRayon;
		int leftForBig = center.x - (int)bigRayon;
		int topForBig = center.y - (int)bigRayon;

		g2d.setColor(Color.YELLOW);
		g2d.setStroke(new BasicStroke(1));
		//draw center point
		g2d.fillOval(center.x, center.y, 5, 5);
		//draw rayons
		g2d.drawOval(leftForSmall, topForSmall, (int)smallRayon*2, (int)smallRayon*2);
		g2d.drawOval(leftForBig, topForBig, (int)bigRayon*2, (int)bigRayon*2);
	}
	
	public boolean recognizeCircle(Rectangle rec, ArrayList<Point> currentStroke) {
		/*int x = rec.x + (rec.width / 2);
		int y = rec.y + (rec.height / 2);
		Point center = new Point(x,y);
		double rayon = ((rec.width / 2) + (rec.height / 2)) / 2;*/
		this.calculateRayon(rec);
		
		for (Point p : currentStroke) {
			if (p.distance(center) < smallRayon){
				if (p.distance(center) > bigRayon)
					System.out.println("False");
					return false;
			}
		}
		System.out.println("True");
		return true;
	}
	
	public Rectangle getBounds(ArrayList<Point> shape){
		int left = this.getWidth();
		int top = this.getHeight();
		int right = 0;
		int bottom = 0;
		
		for (Point p : shape){			
			left = Math.min(p.x, left);
			top = Math.min(p.y, top);
			right = Math.max(p.x, right);
			bottom = Math.max(p.y, bottom);
		}
		//System.out.println(left + " " + top + " " + right + " " + bottom);
		Rectangle bounds = new Rectangle(left, top, (right - left), (bottom - top));
		return bounds;
	}
	
	public String getShapeName(int numAngle){
        switch(numAngle){
        	case 0: return "Point";
        	case 1: return "Line";
        	case 2: return "1 angle";
            case 3: return "Triangle";
            case 4: return "Rectangle";
            case 5: return "Pentagon";
            case 6: return "Hexagon";
            case 7: return "Heptagon";
            case 8: return "Octagon";
        }
        return null;
    }
	
	public ArrayList<Point> recognizeShape(ArrayList<Point> line){
		Point previous = null;
		Point anchor = null;
		ArrayList<Point> shape = new ArrayList<Point>();
		
		for (Point current : line){
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
			
			if (angle > 30){
				if (current.distance(anchor)> 25){
					if (current.distance(previous) > 10) {
						anchor = previous;
						shape.add(anchor);					
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
		if (shape.size() < 3){
			if (shape.get(0).distance(shape.get(shape.size()-1)) < 5){
				shape.get(shape.size()-1).setLocation(shape.get(0));
			}
		}
		else{
			if (shape.get(0).distance(shape.get(shape.size()-1)) < 20){
				shape.get(shape.size()-1).setLocation(shape.get(0));
			}
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
		double delta = Math.acos(cosDelta);
		delta = delta * 180 / Math.PI;
		
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
		aShape = this.recognizeShape(currentLine); //aShape is global, why it needs return? 
		bounds = this.getBounds(aShape);
		//System.out.println(this.recognizeCircle(bounds, currentLine));
		
		if (this.recognizeCircle(bounds, currentLine) == true)
			System.out.println("This figure is Cicle\n");
		else
			System.out.println("This figure is " + getShapeName(aShape.size()-1) + "\n");
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
