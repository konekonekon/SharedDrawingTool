package SharedCanvas;

import java.awt.*;

/* Connection between window and menu */
public interface Performer {

	public void newFile();

	public void quit();

	public void redo();

	public void undo();

	public void setColor(Color color);

}
