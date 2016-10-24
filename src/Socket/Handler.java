package Socket;
import java.io.*;
import java.net.*;


public class Handler extends Thread {
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	

/*	public Threads(Socket s) {
		this.socket = s;
	}*/
	public Handler(ServerSocket ss) throws IOException{
		ss.setSoTimeout(10000);
		this.socket = ss.accept();
	}
	
	public void run(){
			try {
				in = new DataInputStream(this.socket.getInputStream());
				out = new DataOutputStream(this.socket.getOutputStream());
				
				System.out.println("Just connected to : " + this.socket.getRemoteSocketAddress());
				System.out.println(in.readUTF());
				
				out.writeUTF("Connected to : " + this.socket.getLocalSocketAddress());
				/*TO DO*/
				
			} catch(SocketTimeoutException s){
				System.out.println("time out!");
				
			} catch (IOException e) {
				e.printStackTrace();
			
			} finally {
				try {
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		
	}
}
