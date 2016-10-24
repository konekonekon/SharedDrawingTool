package SharedCanvas;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class DrawSpace extends JComponent implements MouseListener, MouseMotionListener {

	private BufferedImage bufImage;
	// private Image img;
	private Graphics2D g2d;

	private ArrayList<Point> currentLine;
	private ArrayList<ArrayList<Point>> lines;
	private ArrayList<Shape> shapes;
	private ArrayList<DrawListener> drawListeners;
	private ArrayList<Shape> prevShapes;

	public DrawSpace() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		lines = new ArrayList<ArrayList<Point>>();
		shapes = new ArrayList<Shape>();
		drawListeners = new ArrayList<DrawListener>();
		prevShapes = new ArrayList<Shape>();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		/*if (bufImage == null){
			bufImage = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
			g2d = (Graphics2D) bufImage.getGraphics();
			clear();
		}*/

	    /* ANTI ALIASING */
		RenderingHints rh = g2.getRenderingHints ();
	    rh.put (RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints (rh);
	    
	    /* Paint background */
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		/* Draw lines user drew in black */
		g2.setColor(Color.black);
		for (ArrayList<Point> line : lines)
			drawStroke(line, g2);
		
		/* Draw recognized shapes in red */
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		for (Shape s : shapes)
			s.draw(g2);
		/*for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(g2);
			if (shapes.get(i) != shapes.get(i+1))
				i += 1;	
		}*/
	}
	
	public static void drawStroke(ArrayList<Point> line, Graphics2D g2) {
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
		if (aShape != null) {
			shapes.add(aShape);
			repaint();
			for (DrawListener l : drawListeners)
				l.shapeDrawn(aShape);
		}
	}

	public void mouseDragged(MouseEvent e) {
		currentLine.add(e.getPoint());
		repaint();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}

	public void addDrawListener(DrawListener listener) {
		drawListeners.add(listener);
	}
	
	public void addShape(Shape s) {
		shapes.add(s);
		repaint();
	}
	
	public void clear(){
		lines.clear();
		shapes.clear();
		prevShapes.clear();
		repaint();
	}

	public void setBufferedImage(String imagePath){
		try{
			bufImage = ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveBufferedImage(String imagePath){
		try{
			File outfile = new File(imagePath);
			ImageIO.write(bufImage, "PNG", outfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void undoLastShape(){
		if (shapes != null) {
			System.out.println("prevShapes.size(): " + prevShapes.size());
			System.out.println("shapes.size(): " + shapes.size());
			prevShapes.add(shapes.get(shapes.size() - 1));
			shapes.remove(shapes.get(shapes.size() - 1));
			System.out.println("prevShapes.size(): " + prevShapes.size());
			System.out.println("shapes.size(): " + shapes.size());
		}
		repaint();
	}

	public void redoLastShape(){
		if (prevShapes != null) {
			shapes.add(prevShapes.get(prevShapes.size() - 1));
			prevShapes.remove(prevShapes.get(prevShapes.size() - 1));
		}
		repaint();
	}

}
