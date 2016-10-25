package SharedCanvas;

import java.io.*;
import java.net.*;

public class DrawClient implements DrawListener {
	private static final int PORT = 1233;
	private String serverAddress = "192.168.55.1";
    BufferedReader in;
    PrintWriter out;
    Window window;

    public DrawClient() {
    	window = new Window();
    	/* Listening DrawSpace action */
    	window.getDrawSpace().addDrawListener(this);
    }

    private void run() throws IOException {
        Socket socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        //out.println("This message is from " + socket.getLocalSocketAddress());

        /* Decode String line to Shape */
        while (true) {
        	String line = in.readLine();
        	Shape s = null;
        	
        	/* Drawing */
        	if (line.startsWith("Dot"))
        		s = Dot.decode(line);
        	else if (line.startsWith("Circle"))
        		s = Circle.decode(line);
        	else if (line.startsWith("Polygon"))
        		s = Polygon.decode(line);
        	/* Add this shape to shapes list to draw */
        	if (s != null)
        		window.getDrawSpace().addShape(s);
        	
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
    
    /* When DrawSpace has action = new shape, 
     * it encode to string, and send to Socket. */
	@Override
	public void shapeDrawn(Shape s) {
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