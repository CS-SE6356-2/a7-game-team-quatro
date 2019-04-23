import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class NetworkClient extends Thread{

	Socket socket;
	
	
	@Override
	public void run(){// called from Thread.start()
		
		while(!socket.isClosed()) {//continually polls the server waiting for a message
			DataInputStream in;
			try{
				in = new DataInputStream(socket.getInputStream());
				String message = in.readUTF();
				messageFromServer_Handler(message);
				
			}catch (IOException e){}
			
		}
	}
	
	//attempts to establish a connection to a server
	boolean connectToServer(String addressStr, int portNumber) {
		try {
			socket = new Socket();
			InetSocketAddress address = new InetSocketAddress(addressStr, portNumber);
			socket.connect(address, 10000);
			this.start();//starts reading messages from the server
			return true;
		}
		catch(UnknownHostException e){}//could not find server
		catch(SocketTimeoutException e){}//connection attempt timed out
		catch(IOException e){}//failed to connect
		return false;
	}
	
	//sends the string to the server
	boolean writeToServer(String message) {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(message);
		}
		catch(IOException e){
			return false;
		}
		return true;
	}
	
	//will be called when a message is received from the server
	void messageFromServer_Handler(String message) {
		if(message.equals("Who are you?")) {
			//send back name
			writeToServer("My name");
		}
		else if(message.equals("Name taken")) {
			//try a different name
		}
		else if(message.startsWith("Player list:")) {
			//got list of players
		}
		else if(message.equals("Begin game")) {
			//start game
		}
		else if(message.equals("Your turn")) {
			//client can take a turn
		}
	}

}
