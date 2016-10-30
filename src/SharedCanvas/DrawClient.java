package SharedCanvas;

import java.io.*;
import java.net.*;

public class DrawClient implements DrawListener {
	private static final int PORT = 1231;
	private String serverAddress = "localhost";
	BufferedReader in;
	PrintWriter out;
	Window window;
	private Socket socket;

	public DrawClient() {
		window = new Window();
		/* Listening DrawSpace action */
		window.getDrawSpace().addDrawListener(this);
	}

	private void run() throws IOException {
		socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		while (true) {
			String line = in.readLine();
			Shape s = null;

			/* Decode String line to Shape */
			if (line.startsWith("Dot"))
				s = Dot.decode(line);
			else if (line.startsWith("Circle"))
				s = Circle.decode(line);
			else if (line.startsWith("Polygon"))
				s = Polygon.decode(line);
			/* Add this shape to shapes list to draw */
			if (s != null)
				window.getDrawSpace().addShape(s);

			/* Decode String line to an action */
			/* NewFile */
			if (line.startsWith("NewFile"))
				window.getDrawSpace().clear();
			/* Undo */
			if (line.startsWith("Undo"))
				window.getDrawSpace().undoLastShape();
			/* Redo */
			if (line.startsWith("Redo"))
				window.getDrawSpace().redoLastShape();
		}
	}

	/*
	 * When DrawSpace has action, it encode to string, and send to Socket.
	 */
	@Override
	public void shapeDrawn(Shape s) {
		if (out != null)
			out.println(s.encode());
	}

	@Override
	public void shapeUndo() {
		out.println("Undo");
	}

	@Override
	public void shapeRedo() {
		out.println("Redo");
	}

	@Override
	public void newFileCreated() {
		out.println("NewFile");
	}

	public static void main(String[] args) throws Exception {
		DrawClient client = new DrawClient();
		client.run();
	}
}