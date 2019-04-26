import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

//GUI have a reference to an instance of NetworkClient, and should call connectToServer() to get things rolling
//GUI should call shutdown() to stop
public class NetworkClient extends Thread{

	Socket socket;
	String state;
	
	@Override
	public void run(){// called from Thread.start()
		
		while(!socket.isClosed()) {//continually polls the server waiting for a message
			DataInputStream in;
			try{
				in = new DataInputStream(socket.getInputStream());
			}catch (IOException e){continue;}//are we closed?
			
			try {
				String message = in.readUTF();
				messageFromServer_Handler(message);
			}catch (IOException e){//server is probably shutdown
				disconnected();
			}
		}
		//socket is closed
		if(state.equals("Running")) {
			disconnected();
		}
	}
	
	//attempts to establish a connection to a server
	String connectToServer(String addressStr, int portNumber, String name) {
		try {
			socket = new Socket();
			InetSocketAddress address = new InetSocketAddress(addressStr, portNumber);
			socket.connect(address, 10000);
			//if made it here, established a connection
			DataInputStream in = new DataInputStream(socket.getInputStream());
			String message = in.readUTF();//"Who are you"
			writeToServer(name);
			if(message.equals("Name Taken")) {//name is taken, must try again
				return "Name Taken";
			}
			//else message.equals("Welcome"), and we're in
			state = "Running";
			this.start();//starts reading messages from the server
			
			return "Success";
		}
		catch(UnknownHostException e){}//could not find server
		catch(SocketTimeoutException e){}//connection attempt timed out
		catch(IOException e){}//failed to connect
		return "ERROR";
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
		if(message.startsWith("Player list:")) {
			System.out.println(message);
			//tell GUI the player list
		}
		else if(message.equals("Begin game")) {
			//tell GUI to move from lobby to game field
		}
		else if(message.startsWith("Info:")) {
			//tell GUI to update game info
		}
		else if(message.equals("Your turn")) {
			//Tell GUI that is the client's turn
			//tell GUI to send back turn info
		}
	}
	
	//GUI should call this after told to take a turn
	void respondWithTurnInfo(String turn) {
		writeToServer(turn);
	}
	
	//lost connection to the server
	void disconnected() {
		//tell GUI that server disconnected
		state = "Disconnected";
		try{
			socket.close();//close each client
		}catch (IOException e){}
	}
	
	//closes the connection to the server, to be called by GUI
	void shutdown() {
		state = "Shutdown";
		try{
			if(socket != null)
				socket.close();//close each client
		}catch (IOException e){}
	}

}
