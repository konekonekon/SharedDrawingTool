package damien;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar{

	private JMenu fileMenu;
	private JMenu canvaMenu;
	private JMenu editMenu;
	
	private JMenuItem importFile;
	private JMenuItem exportFile;
	private JMenuItem exitFile;
	
	private JMenuItem addCanvas;
	private JMenuItem removeCanvas;
	
	public Menu(){
		fileMenu = new JMenu("File");
		//TODO: add menonic ? // keyboard shortcut
		this.add(fileMenu);
		
		canvaMenu = new JMenu("Canvas");
		this.add(canvaMenu);
		
		// fileMenu items
		importFile = new JMenuItem("Import");
		exportFile = new JMenuItem("Export");
		exitFile = new JMenuItem("Exit");
		
		fileMenu.add(importFile);
		importFile.addActionListener(event -> MenuAction.importFile());
		fileMenu.add(exportFile);
		exportFile.addActionListener(event -> MenuAction.exportFile());
		fileMenu.add(exitFile);
		exitFile.addActionListener(event -> MenuAction.exitFile());
		
		// CanvasMenu items
		addCanvas = new JMenuItem("Add canva");
		addCanvas.addActionListener(event -> MenuAction.addCanvas());
		removeCanvas = new JMenuItem("Remove canva");
		removeCanvas.addActionListener(event -> MenuAction.removeCanvas());
		canvaMenu.add(addCanvas);
		canvaMenu.add(removeCanvas);
		
		
	}
	
}
