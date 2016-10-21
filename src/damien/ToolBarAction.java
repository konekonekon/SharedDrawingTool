package damien;

import java.awt.Color;
import java.awt.Event;

public class ToolBarAction {

	public static void strokes(Event e){
		System.out.println("Clicked on strokes button");
	}
	
	public static void color(Event e){
		System.out.println("Clicked on color button");
		Color color= Color.WHITE;
		System.out.println();;
	}
	
}
