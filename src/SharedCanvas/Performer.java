package SharedCanvas;

import java.awt.*;

public interface Performer {

	public void newFile();

	public void importFile();

	public void saveFile();

	public void quit();
	
	public void redo();

	public void undo();
	
	public void setColor(Color color);

}
