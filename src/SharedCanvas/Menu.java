package SharedCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 8192450392020603398L;
	private JMenu fileMenu, editMenu;
	private JMenuItem newFile, quit, redo, undo;
	private Performer performer;

	public Menu(Performer performer) {
		this.performer = performer;

		// File Menu
		fileMenu = new JMenu("File");
		this.add(fileMenu);

		// File menuItems
		newFile = new JMenuItem("New");
		newFile.addActionListener(this);
		fileMenu.add(newFile);
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
	}

	/* Call performer section in Window class */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.newFile) {
			this.performer.newFile();
		}
		if (e.getSource() == this.quit) {
			this.performer.quit();
		}
		if (e.getSource() == this.redo) {
			this.performer.redo();
		}
		if (e.getSource() == this.undo) {
			this.performer.undo();
		}
	}
}
