package damien;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPC extends JFrame implements KeyListener, MouseListener, MouseMotionListener{

	/*
	 * Variables and states
	 */

	private Dimension minimumSize = new Dimension(250, 300);
	private Dimension prefSize = new Dimension(500, 600);
	private ArrayList<Canva> canvas;
	protected int canvasNB;

	private JPanel panel;

	public DrawPC(){
		super();
		setTitle("Share Drawing - PC");
		setMinimumSize(minimumSize);
		setPreferredSize(prefSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel for the canvas
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		this.add(panel);

		// Menu

		// Toolbar

		// Canvas as component
		canvas = new ArrayList<Canva>();
		canvasNB = 0;

		// test adding
		for (int i = 0; i < 10; i++){
			addcanva();
		}

		// Pack and visibility
		pack();
		this.setVisible(true);
	}

	protected void addcanva(){
		Canva tempcanva = new Canva();
		canvas.add(tempcanva);
		canvasNB++;
		System.out.println("There is now " + canvasNB + " canvas");
		panel.add(tempcanva);
		revalidate();
		repaint();
	}


	protected void removeLastcanva(){
		if (canvasNB > 0){
			panel.remove(canvas.get(canvasNB - 1));
			canvas.remove(canvasNB - 1);
			canvasNB--;
			System.out.println("There is now " + canvasNB + " canvas");
		}
		else{
			System.out.println("Unable to remove canva " + canvasNB + " remaining");
		}
	}

	protected void removeIndexcanva(int index) {
		if (index > canvasNB){
			System.out.println("Unable! index: " + index + " is greater than number of components: " + canvasNB);
			return;
		}
		else{
			panel.remove(canvas.get(index));
			canvas.remove(index);
			canvasNB--;
			System.out.println("There is now " + canvasNB + " canvas");
		}
		revalidate();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
