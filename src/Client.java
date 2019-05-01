import java.net.Socket;

public class Client{
	String name;
	Socket socket;
	
	public Client(Socket socket) {
		this.socket = socket;
	}
	
	public Client(String name, Socket socket) {
		this.name = name;
		this.socket = socket;
	}
	
	
}
