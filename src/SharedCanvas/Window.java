package SharedCanvas;
import java.awt.*;
import javax.swing.*;

public class Window extends JFrame implements Performer {

	private static final long serialVersionUID = 1L;
	private Menu menu;
	private DrawSpace drawSpace;
	private JLabel status;

	public Window(){
		this.setTitle("Shared Drawing & Recognition App");
		this.setMinimumSize(new Dimension(250, 200));
		this.setPreferredSize(new Dimension(600, 700));
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawSpace = new DrawSpace();
		menu = new Menu(this);
		status = new JLabel();
		
		/* For color option */
		JPanel panel = new JPanel();
		FlowLayout panelFlowLayout = new FlowLayout();
		panel.setLayout(panelFlowLayout);
		this.add(panel, BorderLayout.NORTH);

		JButton buttonBlue = new JButton("Blue");
		panel.add(buttonBlue);
		buttonBlue.addActionListener(e -> drawSpace.setColor(Color.BLUE));

		JButton buttonRed = new JButton("Red");
		panel.add(buttonRed);
		buttonRed.addActionListener(e -> drawSpace.setColor(Color.RED));

		JButton buttonColor = new JButton("Choose Color");
		panel.add(buttonColor);
		buttonColor.addActionListener(e -> showColor());

		this.setJMenuBar(menu);
		this.add(status, BorderLayout.SOUTH);
		this.add(drawSpace, BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);
	}

	public DrawSpace getDrawSpace(){
		return this.drawSpace;
	}

	@Override
	public void newFile() {
		status.setText("New file opened");
		drawSpace.newFileEvent();
	}
	
	@Override
	public void quit() {
		status.setText("Exiting, bye!");
		System.exit(0);
	}
	@Override
	public void undo() {
		status.setText("Undo last shape");
		drawSpace.undoEvent();
	}
	@Override
	public void redo() {
		status.setText("Redo last shape");
		drawSpace.redoEvent();
	}
	@Override
	public void setColor(Color color){
		drawSpace.setColor(color);
		drawSpace.repaint();
	}

	@SuppressWarnings("static-access")
	public void showColor(){
		System.out.println("Show Color Dialog dialog");
		Color color;
		JColorChooser colorChooser = new JColorChooser();
		color = colorChooser.showDialog(this, "Choose Color", Color.WHITE);
		drawSpace.setColor(color);
		drawSpace.repaint();
	}
}