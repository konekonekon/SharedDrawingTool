import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Server  {

	private static int port = 1234;
	private static ServerSocket serverSocket;

	
	public static void main(String args[]) throws IOException{
		
		try {
			serverSocket = new ServerSocket(port);
			
			System.out.println("Server on");
			//Thread server = new Server(port);
			while(true){
				//new Threads(serverSocket.accept()).start();
				new Threads(serverSocket).start();
				//server.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			serverSocket.close();
		}
	}

}
