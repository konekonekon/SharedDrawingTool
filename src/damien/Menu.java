import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar implements ActionListener{

	private JMenu fileMenu, editMenu;
	private JMenuItem newFile, importFile, saveFile, quit, redo, undo, reset;

	private Performer performer;

	public  Menu(Performer performer) {
		this.performer = performer;

		// File Menu
		fileMenu = new JMenu("File");
		this.add(fileMenu);

		// File menuItems
		newFile = new JMenuItem("New");
		newFile.addActionListener(this);
		fileMenu.add(newFile);
		importFile = new JMenuItem("Import");
		importFile.addActionListener(this);
		fileMenu.add(importFile);
		saveFile = new JMenuItem("Save");
		saveFile.addActionListener(this);
		fileMenu.add(saveFile);
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		fileMenu.add(quit);
		
		// Edit Menu
		editMenu = new JMenu("Edit");
		this.add(editMenu);
		
		// Edit menuItems
		redo = new JMenuItem("Redo");
		redo.addActionListener(this);
		editMenu.add(redo);
		undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		editMenu.add(undo);
		reset = new JMenuItem("Reset");
		reset.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == this.newFile){
			this.performer.newfile();
		}
		if (e.getSource() == this.importFile){
			this.performer.importFile();
		}
		if (e.getSource() == this.saveFile){
			this.performer.savefile();
		}
		if (e.getSource() == this.quit){
			this.performer.quit();
		}
		if (e.getSource() == this.redo){
			this.performer.redo();
		}
		if (e.getSource() == this.undo){
			this.performer.undo();
		}
		if (e.getSource() == this.reset){
			this.performer.reset();
		}
		
		
		
		
		
		
		
		
		
	}

}
