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
		importFile = new JMenuItem();
		exportFile = new JMenuItem();
		exitFile = new JMenuItem();
		
		fileMenu.add(importFile);
		importFile.addActionListener(event -> MenuAction.importFile());
		fileMenu.add(exportFile);
		fileMenu.add(exitFile);
		
	}
	
}
