package TestSharedCanvas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class DrawClient implements DrawListener {

    BufferedReader in;
    PrintWriter out;
    Window window;
    //JFrame frame = new JFrame("Shared Drawing Tool");
    private DrawSpace drawSpace;

    public DrawClient() {
    	window = new Window();
    	drawSpace = new DrawSpace();
    	drawSpace.addDrawListener(this);
    	window.setLayout(new BorderLayout());
    	window.add(drawSpace, BorderLayout.CENTER);
    	
		/*frame.setMinimumSize(new Dimension(520,450));
    	frame.getContentPane().add(drawSpace, "Center");
        frame.pack();  */      
    }

    private void run() throws IOException {

        // Make connection and initialize streams
        //String serverAddress = getServerAddress();
        Socket socket = new Socket("localhost", 9000);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

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
        client.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.window.setVisible(true);
        /*client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);*/
        client.run();
    }
    
	@Override
	public void shapeDrawn(Shape s) {
		out.println(s.encode());
	}
}