import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javafx.application.Platform;

//GUI have a reference to an instance of NetworkClient, and should call connectToServer() to get things rolling
//GUI should call shutdown() to stop
public class NetworkClient extends Thread{

	Socket socket;
	String state;
	GUI GUI;
	String name;
	
	@Override
	public void run(){// called from Thread.start()
		
		while(!socket.isClosed()) {//continually polls the server waiting for a message
			DataInputStream in;
			try{
				in = new DataInputStream(socket.getInputStream());
			}catch (IOException e){//are we closed? Something probably happened...
				disconnected();
				return;
			}
			
			try {
				String message = in.readUTF();
				messageFromServer_Handler(message);
			}catch (IOException e){//server is probably shutdown
				disconnected();
				return;
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
			this.name = name;
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
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.updatePlayerList(message.substring(message.indexOf(":")+1));
					//tell GUI the player list
				}
				});
		}
		
		else if(message.equals("Begin game")) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.goToGame();
				}
				});
		}
		
		else if(message.startsWith("Info:")) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.updateGameInfo(message.substring(message.indexOf(":")+1));
				}
				});
		}
		
		else if(message.equals("Your turn")) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.takeTurn();
				}
				});
		}
		
		else if(message.startsWith("Game over:")) {
			shutdown();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GUI.endGame(message.substring(message.indexOf(":")+1));   
				}
				});
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
		close();
		GUI.updatePlayerList("Disconnected");
		//GUI.disconnected();
	}
	
	//closes the connection to the server, to be called by GUI
	void shutdown() {
		state = "Shutdown";
		close();
	}
	
	//closes socket
	private void close() {
		try{
			if(socket != null)
				socket.close();//close each client
		}catch (IOException e){}
		
	}

}
