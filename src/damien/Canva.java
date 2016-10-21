package damien;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

public class Canva extends JComponent implements KeyListener, MouseListener, MouseMotionListener{


	private Graphics2D g2;
	//private Dimension minimumSize = new Dimension(100, 100);
	private Dimension prefSize = new Dimension(200, 200);

	//TODO: add hash (as in Julie code)
	private HashMap<Integer, ArrayList<Point>> lines;
	private ArrayList<Point> line;
	private Point point;
	private int last;
	private Color color;

	public Canva() {
		setPreferredSize(prefSize);
		setVisible(true);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);

		lines = new HashMap<Integer, ArrayList<Point>>();
		line = new ArrayList<Point>();
	}

	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.setColor(Color.ORANGE);
		g2.fillRect(0, 0, 200, 200);

		// from https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);

		g2.setColor(color);


		// DRAWING MODE 
		// draw current line
		for (int i = 0; i < line.size()-2 ; i++) {
			Point p1 = line.get(i);
			Point p2 = line.get(i+1);
			g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
		}

		// draw previous lines
		for (Map.Entry<Integer, ArrayList<Point>> entry : lines.entrySet()) {
			// int j = entry.getKey();
			ArrayList<Point> list = entry.getValue();            		

			for (int i = 0; i < list.size()-2 ; i++) {
				Point p1 = list.get(i);
				Point p2 = list.get(i+1);
				g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
			}

		}
		// revalidate();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		line.add(e.getPoint());
		revalidate();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		point = e.getPoint();
		requestFocusInWindow();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		last = lines.size();
		lines.put(last, line);
		line = new ArrayList<Point>();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void setColor(Color color){
		g2.setColor(color);
	}

}
