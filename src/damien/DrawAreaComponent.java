import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

// From http://www.ssaurel.com/blog/learn-how-to-make-a-swing-painting-and-drawing-application/
// https://www.youtube.com/watch?v=OOb1eil4PCo/


@SuppressWarnings("serial")
public class DrawAreaComponent extends JComponent{

	private BufferedImage bufImage;
	// private Image img;
	private Graphics2D g2d;
	private Point currentPt, oldPt;



	// list of drawing
	private ArrayList<Point> currentLine;
	private ArrayList<ArrayList<Point>> lines;
	private ArrayList<ArrayList<Point>> prev_lines;

	private int left, top, right, bottom;

	public DrawAreaComponent(){

		lines = new ArrayList<ArrayList<Point>>();
		currentLine = new ArrayList<Point>();
		prev_lines = new ArrayList<ArrayList<Point>>();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				oldPt = e.getPoint();

				if (bufImage != null){

					currentLine = new ArrayList<Point>();
					lines.add(currentLine);
				}
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e){
				currentPt = e.getPoint();

				if (g2d != null){
					//	g2d.drawLine((int) oldPt.getX(), (int) oldPt.getY(), (int) currentPt.getX(), (int) currentPt.getY());
					currentLine.add(currentPt);
					// revalidate when adding / remove component -> layout change
					// http://stackoverflow.com/questions/1097366/java-swing-revalidate-vs-repaint
					repaint();
					oldPt = currentPt; // Do not forget !!
				}
			}
		});
	}

	public void paintComponent(Graphics g){
		if (bufImage == null){
			bufImage = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
			g2d = (Graphics2D) bufImage.getGraphics();
			clear();
		}
		
		else{
			g2d = (Graphics2D) bufImage.getGraphics();
			for (ArrayList<Point> line : lines)
				this.drawStroke(line, g2d);
		}

		/*** ANTI ALIASING ***/
		RenderingHints rh = g2d.getRenderingHints ();
		rh.put (RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints (rh);
		// clear();

		g.drawImage(bufImage, 0, 0, null);
	}

	public void clear(){
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		g2d.setColor(Color.RED);
		repaint();
	}


	public void setBufferedImage(String imagePath){
		try{
			bufImage = ImageIO.read(new File(imagePath));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void saveBufferedImage(String imagePath){
		try{
			File outfile = new File(imagePath);
			ImageIO.write(bufImage, "PNG", outfile);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void reset() {
		clear();
	}

	public void drawStroke(ArrayList<Point> line, Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		int i = 0;
		while (i < line.size() - 1) {
			Point p1 = line.get(i);
			Point p2 = line.get(i + 1);
			g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
			i++;
		}
	}

	public void removelastline(){
		if (lines.size() < 1){
			System.out.println("No line to remove");
			return;
		}
		prev_lines.add(lines.get(lines.size() - 1));
		System.out.println("prev_line.size(): " + prev_lines.size());
		lines.remove(lines.size() - 1);
		System.out.println("lines.size(): " + lines.size());
		// revalidate();
		repaint();
	}

	public void redrawlastline(){
		if (prev_lines.size() < 1){
			System.out.println("No drawing to undo");
			return;
		}
		lines.add(prev_lines.get(prev_lines.size() - 1));
		prev_lines.remove(prev_lines.size() - 1);
		// revalidate();
		repaint();
	}

}

