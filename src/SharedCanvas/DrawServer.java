package SharedCanvas;

import java.io.*;
import java.net.*;

public class DrawServer {

	private static final int PORT = 1232;
	private static ServerSocket serverSocket;

	public static void main(String[] args) throws IOException {
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server on");
			while (true) {
				new Threads(serverSocket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			serverSocket.close();
		}
	}
}