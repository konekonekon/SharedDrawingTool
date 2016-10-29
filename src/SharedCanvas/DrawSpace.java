package SharedCanvas;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class DrawSpace extends JComponent implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -7109290034266072605L;
	private BufferedImage bufImage;
	// private Image img;
	private Graphics2D g2d;

	private ArrayList<Point> currentLine;
	private ArrayList<Shape> shapes;
	private ArrayList<DrawListener> drawListeners;
	private ArrayList<Shape> prevShapes;
	//private int numShapeElement;
	private boolean lastActionUndo;
	private Color color;

	public DrawSpace() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		shapes = new ArrayList<Shape>();
		drawListeners = new ArrayList<DrawListener>();
		prevShapes = new ArrayList<Shape>();
		//numShapeElement = 0;
		lastActionUndo = false;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		// color = Color.RED;
		
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
		
		/* Draw current line user draw in black */
		g2.setColor(Color.black);
		if (currentLine != null)
			drawStroke(currentLine, g2);
		
		/* Draw recognized shapes in red */
		g2.setColor(color);
		g2.setStroke(new BasicStroke(3));
		for (Shape s : shapes)
			s.draw(g2);
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
		currentLine.add(e.getPoint());
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		Shape aShape = ShapeRecognizer.recognize(currentLine);
		if (aShape != null) {
			for (DrawListener l : drawListeners)
				l.shapeDrawn(aShape);
		}
		currentLine.clear();
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
	
	public void clear() {
		currentLine.clear();
		shapes.clear();
		prevShapes.clear();
		repaint();
	}

	// NOT WORKING!
	public void setBufferedImage(String imagePath){
		try{
			bufImage = ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// NOT WORKING!
	public void saveBufferedImage(String imagePath){
		try{
			File outfile = new File(imagePath);
			ImageIO.write(bufImage, "PNG", outfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void undoLastShape(){
		// System.out.println(shapes.size());
		if (shapes.size() >= 1) {
			prevShapes.add(shapes.get(shapes.size() - 1));
			shapes.remove(shapes.size() - 1);
			//numShapeElement = shapes.size();
			lastActionUndo = true;
		} else {
			System.out.println("Nothing to undo");
		}
		repaint();
	}
	
	/*public void redoLastShape() {
		// System.out.println(prevShapes.size());
		if (prevShapes.size() >= 1) {
			shapes.add(prevShapes.get(prevShapes.size() - 1));
			prevShapes.remove(prevShapes.size() - 1);
		} else {
			System.out.println("Nothing to redo");
		}
		repaint();
	}*/
	
	public void redoLastShape(){
		// System.out.println(prevShapes.size());
		if (prevShapes.size() >= 1 && lastActionUndo){
			//if(numShapeElement == shapes.size()) {
				shapes.add(prevShapes.get(prevShapes.size() - 1));
				prevShapes.remove(prevShapes.size() - 1);
            //}
        }
		else{
			
			System.out.println("Cannot redo, last action was not undo");
		}
	}
	
/*	public void redoLastShape(){
		// System.out.println(prevShapes.size());
		if (prevShapes.size() >= 1) {
			shapes.add(prevShapes.get(prevShapes.size() - 1));
			prevShapes.remove(prevShapes.size() - 1);
		}
		else
		{
		System.out.println("Nothing to redo");
		}
		repaint();
	}*/
	
	
	/*
	// Only allow redo if last action was undo
		public void redoLastShape(){
		// System.out.println(prevShapes.size());
		if (prevShapes.size() == 1){
			shapes.add(prevShapes.get(prevShapes.size() - 1));
			prevShapes.remove(prevShapes.size() - 1);
            }
		else
		{
		System.out.println("Cannot redo, last action was not undo");
		}
		repaint();
	}
	*/
	
	
	public void newFileEvent() {
		for (DrawListener l : drawListeners)
			l.newFileCreated();
	}
	
	public void undoEvent() {
		for (DrawListener l : drawListeners)
			l.shapeUndo();
	}
	
	public void redoEvent() {
		for (DrawListener l : drawListeners)
			l.shapeRedo();
	}
	
	public void setColor(Color newColor){
        color = newColor;
        repaint();
   }
   
   public void setTrickness(int trickness){
        g2d.setStroke(new BasicStroke(trickness));
        repaint();
   }
}
