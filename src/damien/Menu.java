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
	
	public Menu(){
		fileMenu = new JMenu("File");
		//TODO: add menonic ? // keyboard shortcut
		this.add(fileMenu);
		
		canvaMenu = new JMenu("Canvas");
		
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
		
	}
	
}
