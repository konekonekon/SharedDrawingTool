package SharedCanvas;

public interface DrawListener {
	
	public void shapeDrawn(Shape s);
	public void shapeUndo();
	public void shapeRedo();
}
