import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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
