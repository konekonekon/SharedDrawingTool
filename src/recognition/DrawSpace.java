package recognition;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DrawSpace extends JComponent implements MouseListener, MouseMotionListener {

	private ArrayList<Point> currentLine;
	private ArrayList<ArrayList<Point>> lines;
	private ArrayList<Shape> shapes;

	public DrawSpace() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		lines = new ArrayList<ArrayList<Point>>();
		shapes = new ArrayList<Shape>();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

	    /*** ANTI ALIASING ***/
		RenderingHints rh = g2.getRenderingHints ();
	    rh.put (RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints (rh);
	    
	    /*** Paint background ***/
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		/*** Draw lines user drew in black ***/
		g2.setColor(Color.black);
		/*if (currentLine !=  null)
			drawStroke(currentLine, g2);*/
		for (ArrayList<Point> line : lines)
			drawStroke(line, g2);
		
		/*** Draw recognized shapes in red ***/
		for (Shape s : shapes)
			s.draw(g2);
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

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		currentLine = new ArrayList<Point>();
		lines.add(currentLine);
		currentLine.add(e.getPoint());
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		Shape aShape = ShapeRecognizer.recognize(currentLine);
		if (aShape != null)
			shapes.add(aShape);
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
