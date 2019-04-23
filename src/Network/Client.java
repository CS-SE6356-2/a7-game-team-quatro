import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client{
	String name;
	Socket socket;
	
	public Client(String name, Socket socket) {
		this.name = name;
		this.socket = socket;
	}
	
	String connectToServer(String addressStr, int portNumber) {
		try {
			socket = new Socket();
			InetSocketAddress address = new InetSocketAddress(addressStr, portNumber);
			socket.connect(address, 10000);
			return "Connected";
		}
		catch(UnknownHostException e){
			return "ERROR: Could not find server address";
		}
		catch(SocketTimeoutException e){
			return "ERROR: Connection attempt timed out";
		}
		catch(IOException e){
			return "ERROR: Failed to connect to server";
		}
	}
	
	//sends the string to the server
	void writeToServer(String message) {
		
	}
	
	//will be called when a message is received from the server
		void messageFromServer_Handler(String message) {
			
		}
}
