package SharedCanvas;

import java.io.*;
import java.net.*;
import java.util.*;

public class Threads extends Thread {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	/* The set of all the print writers for all the clients */
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

	public Threads(ServerSocket ss) throws IOException {
		this.socket = ss.accept();
	}

	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Just connected to : "
					+ socket.getRemoteSocketAddress());
			writers.add(out);

			while (true) {
				String input = in.readLine();
				if (input == null) {
					return;
				}
				for (PrintWriter writer : writers) {
					writer.println(input);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				writers.remove(out);
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}