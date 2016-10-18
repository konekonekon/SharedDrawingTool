package recognition;

import java.awt.*;

import javax.swing.JFrame;

public class Window extends JFrame{
	
	private DrawSpace ds;
	
	public Window(){
		this.setTitle("Recognition Tool");
		this.setMinimumSize(new Dimension(220,150));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		int preferredWidth = (int)(screenWidth *0.6);
		int preferredHeight = (int)(screenHeight *0.6);
		this.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		int preferredX = (screenWidth - preferredWidth) /2;
		int preferredY = (screenHeight - preferredHeight) /2;
		this.setLocation(preferredX, preferredY);
		this.setMaximizedBounds(new Rectangle(screenWidth, screenHeight));
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ds = new DrawSpace();

		this.add(ds, BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);
	}
	
	/*public double calculateAngle(Point p0, Point p1, Point p2){
		*//*** cos Delta = vectorU*vectorV / length(vectorU)*length(vectorV) 
		 *   dotProcudt = vectorU*vectorV.
		 * 	 Delta = acos Delta. acos = inverse of cos = cos small(-1)
		 * 
		 *   vectorU = p0 to p1 = u, vectorV = p0 to p2 = v
		 *   length(vectorU) = lengthU, length(vectorV) = lengthV ***//*

		Point u = new Point((p1.x - p0.x), (p1.y - p0.y));
		Point v = new Point((p2.x - p1.x), (p2.y - p1.y));
		double dotProduct = (u.x * v.x) + (u.y * v.y);
		System.out.println("DotProduct : " + dotProduct);
		double lengthU = p0.distance(p1);
		double lengthV = p1.distance(p2);
		double length = lengthU * lengthV;
		System.out.println("Length : " + length);
		
		double cosDelta = dotProduct / length;
		System.out.println("cosDelta : " + cosDelta);
		double delta = Math.acos(cosDelta);
		delta = delta * 180 / Math.PI;
		System.out.println("Delta : " + delta);
		
		return delta;
	}*/
	
	public static void main(String[] args) {
		Window w = new Window();
		
		/*Point p0 = new Point(0,0);
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,1);
		System.out.println(w.calculateAngle(p0, p1, p2));*/

	}
}
