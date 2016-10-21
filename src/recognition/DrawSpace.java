package recognition;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
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
	private boolean isCircle;
	private ArrayList<ArrayList<Point>> recognizeShapes;

	public DrawSpace() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		lines = new ArrayList<ArrayList<Point>>();
		isCircle = false;
		recognizeShapes = new ArrayList<ArrayList<Point>>(); //stock
	}

	// TODO : color, thickness for line/text
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		RenderingHints rh = g2.getRenderingHints();
	    rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
	    
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.black);

		for (ArrayList<Point> line : lines){
			this.drawStroke(line, g2);
		}
		
		if (this.aShape != null) {
			/*for (Point p : aShape) {
				this.drawAnchor(p, g2);
			}*/ //which is better?
			this.drawAnchor(this.aShape, g2);
			
			if (this.aShape.size() > 2)
				this.drawTestCircles(this.bounds, g2); //not work
			
			if (this.isCircle == true) {
				this.displayCircle(this.bounds, g2);
				this.isCircle = false;
			}
			else {
				this.displayShape(this.aShape, g2);
			}
			//this.isCircle = false;
			this.aShape = null;
		}
	}
	
	/*** DRAW ELEMENTS SECTION ***/
	public void displayCircle(Rectangle rectangle, Graphics2D g2d){
		this.calculateRayon(rectangle);
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(3));
		
		int left = center.x - (int)rayon;
		int top = center.y - (int)rayon;
		
		g2d.drawOval(left, top, (int)rayon*2, (int)rayon*2);
	}
	
	public void drawTestCircles(Rectangle rec, Graphics2D g2d){ //To test
		this.calculateRayon(rec); //each time call this function?
		System.out.println("draw Test Circle : ");
		System.out.println("smallRayon : " + smallRayon);
		System.out.println("bigRayon : " + bigRayon);
		
		int leftForSmall = center.x - (int)smallRayon;
		int topForSmall = center.y - (int)smallRayon;
		int leftForBig = center.x - (int)bigRayon;
		int topForBig = center.y - (int)bigRayon;

		g2d.setColor(Color.YELLOW);
		g2d.setStroke(new BasicStroke(1));
		//draw center point
		g2d.fillOval(center.x-2, center.y-2, 4, 4);
		//draw rayons
		/*Ellipse2D.Double shape = new Ellipse2D.Double(0.5, 0.5, 50, 50);
	    g2d.draw(shape);*/
	    
		g2d.drawOval(leftForSmall, topForSmall, (int)smallRayon*2, (int)smallRayon*2);
		g2d.drawOval(leftForBig, topForBig, (int)bigRayon*2, (int)bigRayon*2);
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
			previous = p;
		}
	}
	
	public void drawAnchor(ArrayList<Point> shape, Graphics2D g2){
		for (Point p : shape){
			g2.setColor(Color.RED);
			g2.fillOval(p.x-3, p.y-3, 6, 6);
		}
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
	
	/*** CALCULATE & RECOGNIZE SECTION ***/	
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
            default : return "Unknown";
        }
    }
	
	public void calculateRayon(Rectangle rec){
		int x = rec.x + (rec.width / 2);
		int y = rec.y + (rec.height / 2);
		center = new Point(x,y);
		rayon = ((rec.width / 2) + (rec.height / 2)) / 2;
		System.out.println("RAyon : " + rayon);
		System.out.println("calculate RAyon : ");
		smallRayon = rayon-(rayon*0.1);
		System.out.println("smallRayon : " + smallRayon);
		bigRayon = rayon+(rayon*0.1);
		System.out.println("bigRayon : " + bigRayon);
		//global? create new class Circle?
		
	}
	
	public boolean recognizeCircle(Rectangle rec, ArrayList<Point> currentStroke) {
		this.calculateRayon(rec);
		System.out.println("recognize Circle : ");
		System.out.println("smallRayon : " + smallRayon);
		System.out.println("bigRayon : " + bigRayon);
		
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
	
	public ArrayList<Point> recognizeShape(ArrayList<Point> line){
		Point previous = null;
		Point anchor = null;
		ArrayList<Point> shape = new ArrayList<Point>();
		
		for (Point current : line){
			//Initialization
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
			//Add last point to shape 
			if (current == line.get(line.size()-1)){
				anchor = current;
				shape.add(anchor);
			}
		}
		/* Compare distance between start point and end point.
		 * if the distance is small, ignore end point, consider end point equal to start point.
		 */ 
		if (shape.size() < 3){ //For small line
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

	/*** MOUSE EVENT SECTION ***/
	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {

		currentLine = new ArrayList<Point>();
		lines.add(currentLine);
		currentLine.add(e.getPoint());
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		this.aShape = this.recognizeShape(currentLine); //aShape is global, why it needs return? 
		//System.out.println("Shape has " + aShape.size() + " points.");
		
		if (this.aShape.size() > 2){
			this.bounds = this.getBounds(this.aShape);
			//System.out.println("Bounds : " + this.bounds);
		
			if (this.recognizeCircle(this.bounds, currentLine) == true){
				this.isCircle = true;
				System.out.println("This figure is Cicle\n");
			}
			else {
				System.out.println("This figure is " + getShapeName(this.aShape.size()-1) + "\n");
			}
		}
		else {
			System.out.println("This figure is " + getShapeName(this.aShape.size()-1) + "\n");
		}
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
