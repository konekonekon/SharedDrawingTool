package damien;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

public class MenuAction {
	
	public static void importFile(){
		System.out.println("Selected Import from File menu");
	}

	public static void exportFile(){
		System.out.println("Selected Export from File menu");
	}
	
	public static void exitFile(){
		System.out.println("Selected Exit from File menu");
		// TODO: close parent windows via dispose	
	}
	
	public static void addCanvas(){
		System.out.println("Selected Add from Canvas menu");
		// TODO: call DrawPC.addcanva();
	}
	
	public static void removeCanvas(){
		System.out.println("Selected Remove from Canvas menu");
		// TODO: call DrawPC.removecanva();
	}
	
}
