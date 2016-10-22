import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SimpleDrawWindow extends JFrame{
	
	DrawAreaComponent drawComp;

	public SimpleDrawWindow(){
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		
		 drawComp = new DrawAreaComponent();
		 drawComp.setBufferedImage("C:\\Users\\NasVr\\Downloads\\4030433.jpg");
		 
		 content.add(drawComp, BorderLayout.CENTER);
		 JButton buttonSave = new JButton("Save");
		 
		 content.add(buttonSave, BorderLayout.NORTH);
		 buttonSave.addActionListener(e -> drawComp.saveBufferedImage("C:\\Users\\NasVr\\Downloads\\a.png"));
		 
		 this.setSize(600, 600);
		 
		// pack();
		 setVisible(true);
	}
	
}
