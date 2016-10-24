package SharedCanvas;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;

public class DrawClient implements DrawListener {
	private static final int PORT = 1234;
	private String serverAddress = "localhost";
    BufferedReader in;
    PrintWriter out;
    Window window;
    private DrawSpace drawSpace;

    public DrawClient() {
    	drawSpace = new DrawSpace();
    	drawSpace.addDrawListener(this);
    	window = new Window();
    	window.setLayout(new BorderLayout());
    	window.add(drawSpace, BorderLayout.CENTER);
    	window.pack();
    	window.setVisible(true);
    }

    private void run() throws IOException {

        // Make connection and initialize streams
        //String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        //out.println("This message is from " + socket.getLocalSocketAddress());

        // Process all messages from server, according to the protocol.
        while (true) {
        	String line = in.readLine();
        	Shape s = null;
        	if (line.startsWith("Dot"))
        		s = Dot.decode(line);
        	else if (line.startsWith("Circle"))
        		s = Circle.decode(line);
        	else if (line.startsWith("Polygon"))
        		s = Polygon.decode(line);
        	
        	if (s != null)
        		drawSpace.addShape(s);
        }
    }

    public static void main(String[] args) throws Exception {
        DrawClient client = new DrawClient();
        client.run();
    }
    
	@Override
	public void shapeDrawn(Shape s) {
		out.println(s.encode());
	}
}