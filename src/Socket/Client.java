package Socket;
import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.*;


public class Client{
	private int port = 1234;
	private String serverAddress = "localhost";
	private DataInputStream in;
	private DataOutputStream out;
	private OutputStream outToServer;
	private InputStream inFromServer;
	private JFrame f;
	private DrawComponent dc;
	
	public Client(){
		f = new JFrame("Shared drawing tool");
		dc = new DrawComponent();
		
		f.getContentPane().add(dc, "Center");
		//f.getContentPane().setBackground(Color.WHITE);
		f.pack();
		
		
	}
	
	public void run() throws UnknownHostException, IOException{
		Socket socket = new Socket(serverAddress, port);
		
		inFromServer = socket.getInputStream();
		outToServer = socket.getOutputStream();
		in = new DataInputStream(inFromServer);
		out = new DataOutputStream(outToServer);

		out.writeUTF("This message is from " + socket.getLocalSocketAddress());
		System.out.println("Server : " + in.readUTF());
	}
	
	public static void main(String args[]) throws UnknownHostException, IOException{
		Client c = new Client();
		c.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.f.setSize(new Dimension(200,100));
		c.f.setVisible(true);
		c.run();
	}
}
