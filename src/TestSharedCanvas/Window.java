package TestSharedCanvas;

import java.awt.*;

import javax.swing.JFrame;

public class Window extends JFrame /*implements DrawListener*/ {
	
	//private DrawSpace drawSpace;
	
	public Window(){
		this.setTitle("Shared Drawing Tool");
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
		//this.setLayout(new BorderLayout());
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*drawSpace = new DrawSpace();
		//drawSpace.addDrawListener(this);
		this.add(drawSpace, BorderLayout.CENTER);*/

		this.pack();
		//this.setVisible(true);
	}

	/*@Override
	public void shapeDrawn(Shape s) {
		out.println(s.encode());
	}*/
}
