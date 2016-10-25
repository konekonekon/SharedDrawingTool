package SharedCanvas;

import java.io.*;
import java.net.*;

public class DrawClient implements DrawListener {
	private static final int PORT = 1231;
	private String serverAddress = "localhost";
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

        	/* Undo */
        	
        	if (line.startsWith("Undo"))
        		window.getDrawSpace().undoLastShape();
        	/*if (line.startsWith("DotUndo"))
        		s = Dot.decodeUndo(line);
        	else if (line.startsWith("CircleUndo"))
        		s = Circle.decodeUndo(line);
        	else if (line.startsWith("PolygonUndo"))
        		s = Polygon.decodeUndo(line);
        	 Add this shape to shapes list to draw 
        	if (s != null)
        		window.getDrawSpace().undoLastShape();*/
        	
        }
    }
    
    /* When DrawSpace has action = new shape, 
     * it encode to string, and send to Socket. */
	@Override
	public void shapeDrawn(Shape s) {
		out.println(s.encode());
	}
	//how to distingish each case?
	@Override
	public void shapeUndo() {
		out.println("Undo");
		//out.println(s.encodeUndo());
	}

	@Override
	public void shapeRedo() {
		out.println("Redo");
		//out.println(s.encodeRedo());
	}

    public static void main(String[] args) throws Exception {
        DrawClient client = new DrawClient();
        client.run();
    }
    
    
}