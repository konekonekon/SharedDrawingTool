package SharedCanvas;

/* Connection between DrawSpace and DrawClient */
public interface DrawListener {

	public void shapeDrawn(Shape s);

	public void newFileCreated();

	public void shapeUndo();

	public void shapeRedo();
}
