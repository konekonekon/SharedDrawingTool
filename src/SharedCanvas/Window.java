package SharedCanvas;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

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
		int preferredWidth = (int)(screenWidth *0.4);
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
		status = new JLabel("Waiting");

		JPanel panel = new JPanel();
		FlowLayout panelFlowLayout = new FlowLayout();
		panel.setLayout(panelFlowLayout);
		
		
		JButton buttonBlue = new JButton("Blue");
		panel.add(buttonBlue);
        buttonBlue.addActionListener(e -> drawSpace.setColor(Color.BLUE));
		
        this.add(panel, BorderLayout.NORTH);
        JButton buttonRed = new JButton("Red");
        panel.add(buttonRed);
		 buttonRed.addActionListener(e -> drawSpace.setColor(Color.RED));

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
	public void importFile() {
		status.setText("Import function selected");

		/*
		 * NOT WORKING !
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
			
			drawSpace.clear();
			drawSpace.setBufferedImage(path);
			status.setText("Succesfully imported file: " + path);
		}
		*/
	}

	@Override
	public void saveFile() {
		status.setText("Save function selected");

		/*
		 * NOT WORKING !
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
		}*/
		//	drawComp.saveBufferedImage());
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
	
}

