import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;

public class NetworkHost implements Runnable{

	ArrayList<Client> clients;
	ServerSocket serverSocket;
	
	@Override
	public void run(){
		// TODO Auto-generated method stub

	}
	
	//attempts to receive a client connection
	boolean connectClient() {
		
		return false;
	}
	
	//sends the message to all clients
	void broadcastToClients(String message) {
		
	}
	
	//check if any clients have disconnected
	void updateClients() {
		
	}
	
	//will be called when a message is received from a client
	void messageFromClient_Handler(String message, Socket client) {
		
	}

}
