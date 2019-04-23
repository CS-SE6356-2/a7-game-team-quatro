import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class NetworkHost extends Thread{

	ClientList clients;//ArrayList of clients, ClientList extends ArrayList<Client>
	ServerSocket serverSocket;
	String state;
	
	
	@Override
	public void run(){
		
		while(state.equals("Joining")) {//Continuously runs catching connecting clients
			connectClient();
		}
		
		if(state.equals("Playing")) {//switches gear to the game loop
			broadcastToClients("Begin game");
			while(state.equals("Joining")) {//continually runs, managing communication needed for the game
				//cycle player turns
			}
		}

	}
	
	//attempts to start the host server
	//returns the IP address of the server if it starts
	//or null if creation fails
	String startHost() {
		String ipAddress = null;

		//create host
		boolean success = false;
		int attempts = 0;//keeps track of attempts to establish server
		while(!success && attempts++ < 10){//tries ten times to create the server
			try{
				serverSocket = new ServerSocket(0);//this line throws exceptions, argument zero chooses an available port
				success = true;//server created if made it here
				ipAddress = InetAddress.getLocalHost().getHostAddress() +":"+ serverSocket.getLocalPort();
				
				//put IP address in clipboard to make my life easier
				StringSelection data = new StringSelection(ipAddress);
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				cb.setContents(data, data);
				
			}
			catch(IOException e){}//creation failed, trying again
		}
		
		if(success) {
			state = "Joining";
			this.start();//begin catching client connections
		}
		
		return ipAddress;
	}
	
	//attempts to receive a client connection
	boolean connectClient() {
		try {
			Socket newSocket = serverSocket.accept();//this line throws the exceptions
			//client connected if we reach here
			Client newClient = new Client(newSocket);//create new client
			writeToClient(newClient, "How are you?");//ask for name
			String name = waitForClientReply(newClient);//get name
			if(name == null) return false;//client didn't give name, disconnected
			//make sure name is unique
			clients.add(newClient);//new client is all set
			
			updateClients();
			String names = "Player list:\n"+clients.getNameList();//get updated player names list
			broadcastToClients(names);//send out updated player names list
			
		}
		catch(SocketTimeoutException e){}//waiting for a client timed out, means nobody tried to connect
		catch(IOException e){}//something failed, hopefully the client tries again
		catch(NullPointerException e){}//something failed, hopefully the client tries again
		return false;
	}
	
	void disconnectClient(Client client) {
		try{
			client.socket.close();
		}catch (IOException e){}
		
		clients.remove(client);
	}
	
	//check if any clients have disconnected
	void updateClients() {
		for(Client client:clients) {//for each client
			if(client.socket.isClosed() || client.socket.isOutputShutdown() || client.socket.isInputShutdown()) {//if the socket is disconnected
				disconnectClient(client);//remove the client from the list
			}
		}
	}
		
	
	//sends the message to all clients
	void broadcastToClients(String message) {
		updateClients();//removed disconnect clients
		for(Client client:clients) {//for each client
			if(!writeToClient(client, message)) {//Send message
				disconnectClient(client);//if the message fails, remove the client from list
			}
		}
		
	}
	
	//sends the message to all clients
	boolean writeToClient(Client client, String message) {
		try {
			DataOutputStream out = new DataOutputStream(client.socket.getOutputStream());
			out.writeUTF(message);
		}
		catch(IOException e){
			return false;
		}
		return true;
	}
	
	//pauses until a message is received from the specified client and returns the message
	//or returns null if that client disconnects
	String waitForClientReply(Client client) {
		String message = null;
		try{
			DataInputStream in = new DataInputStream(client.socket.getInputStream());
			message = in.readUTF();
		}catch (IOException e){
			disconnectClient(client);
		}
		return message;
	}
	

}
