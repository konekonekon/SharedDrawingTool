package SharedCanvas;
/*
import java.awt.*;
import javax.swing.JFrame;

public class Window extends JFrame {
	
	private DrawSpace drawSpace;
	
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
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawSpace = new DrawSpace();
		//drawSpace.addDrawListener(this);
		this.add(drawSpace, BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);
	}
}*/

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

//@SuppressWarnings("serial")
public class Window extends JFrame implements Performer {

	private Menu menu;
	private DrawSpace drawSpace;
	private Dimension minSize = new Dimension(250, 200);
	private JLabel status;
	private File file;

	public Window(){
		this.setTitle("Shared Drawing & Recognition App");
		this.setMinimumSize(minSize);
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
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawSpace = new DrawSpace();
		//	drawComp.setBufferedImage("C:\\Users\\NasVr\\Downloads\\4030433.jpg");
		menu = new Menu(this);
		status = new JLabel();

		/*JButton buttonSave = new JButton("Save");
		 content.add(buttonSave, BorderLayout.NORTH);
		 buttonSave.addActionListener(e -> drawComp.saveBufferedImage("C:\\Users\\NasVr\\Downloads\\a.png"));*/

		this.setJMenuBar(menu);
		this.add(status, BorderLayout.SOUTH);
		//this.add(drawSpace, BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);
	}

	@Override
	public void newFile() {
		drawSpace.clear();
	}

	@Override
	public void importFile() {
		status.setText("Import function selected");

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Images", "jpg", "jpeg", "png", "gif", "JPG", "JPEG", "PNG", "GIF");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		Component parent = null;
		int returnVal = fileChooser.showDialog(parent, "Choose a file");
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
			status.setText("Imported " + file);
			String path = file.getAbsolutePath();
			//??
			drawSpace.reset();
			drawSpace.setBufferedImage(path);
			repaint();
			status.setText("Succesfully imported file: " + path);
		}
	}

	@Override
	public void saveFile() {
		status.setText("Save function selected");

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Images", "jpg", "jpeg", "png", "gif", "JPG", "JPEG", "PNG", "GIF");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		Component parent = null;
		int returnVal = fileChooser.showDialog(parent, "Choose a file");
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
			status.setText("Imported " + file);
			String path = file.getAbsolutePath();
			drawSpace.saveBufferedImage(path);
			repaint();
			status.setText("Succesfully saved file: " + path);
		}
		//	drawComp.saveBufferedImage());
	}
	@Override
	public void quit() {
		status.setText("Exiting, bye!");
		this.dispose(); //?
	}
	@Override
	public void redo() {
		status.setText("Redo last line drawing");
		drawSpace.redrawlastline();
	}
	@Override
	public void undo() {
		status.setText("Undoing last line drawing");
		drawSpace.removelastline();
	}
	@Override
	public void reset() {
		status.setText("Resetting draw area");
		drawSpace.reset(); //clear?
	}
}

