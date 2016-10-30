package SharedCanvas;

import java.awt.Point;
import java.util.ArrayList;

public class ShapeRecognizer {

	public static Shape recognize(ArrayList<Point> line) {
		Shape aShape = null;

		if ((aShape = Dot.recognize(line)) != null)
			return aShape;
		if ((aShape = Circle.recognize(line)) != null)
			return aShape;
		if ((aShape = Polygon.recognize(line)) != null)
			return aShape;

		return aShape;
	}
}
