import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class SimpleDrawWindow extends JFrame implements Performer{

	private Menu menu;
	private DrawAreaComponent drawComp;

	private Dimension minSize = new Dimension(250, 200);
	private JLabel status;
	private File file;

	public SimpleDrawWindow(){
		this.setTitle("Simple drawing App");
		this.setMinimumSize(minSize);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawComp = new DrawAreaComponent();
		//	drawComp.setBufferedImage("C:\\Users\\NasVr\\Downloads\\4030433.jpg");

		content.add(drawComp, BorderLayout.CENTER);

		//TODO: separate Menu
		menu = new Menu(this);
		status = new JLabel();


		/*JButton buttonSave = new JButton("Save");

		 content.add(buttonSave, BorderLayout.NORTH);
		 buttonSave.addActionListener(e -> drawComp.saveBufferedImage("C:\\Users\\NasVr\\Downloads\\a.png"));*/

		this.setSize(600, 600);
		this.setJMenuBar(menu);

		// status bar
		this.add(status, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	@Override
	public void newfile() {
		drawComp.clear();
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
			drawComp.setBufferedImage(path);
			repaint();
			status.setText("Succesfully imported file: " + path);
		}
	}

	@Override
	public void savefile() {
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
			drawComp.saveBufferedImage(path);
			repaint();
			status.setText("Succesfully saved file: " + path);
		}
		//	drawComp.saveBufferedImage());
	}

	@Override
	public void quit() {
		status.setText("Exiting, bye!");
		this.dispose();
	}

	@Override
	public void redo() {
		status.setText("Redo last line drawing");
		drawComp.redrawlastline();
	}

	@Override
	public void undo() {
		status.setText("Undoing last line drawing");
		drawComp.removelastline();
	}

	@Override
	public void reset() {
		status.setText("Resetting draw area");
		drawComp.reset();
	}

}
