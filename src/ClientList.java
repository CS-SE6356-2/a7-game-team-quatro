import java.util.ArrayList;

public class ClientList extends ArrayList<Client>{
	
	//returns true if the passed string not already a name in the list of clients
	boolean isUniqueName(String newName) {
		for(Client client:this) {
			if(newName.equals(client.name)) {
				return false;//name already exists in list
			}
		}
		return true;//name must not already exist
	}
	
	//returns a list of names of clients, one per line
	String getNameList() {
		String names = "";
		for(Client client:this) {
			names = names + client.name + "\n";
		}
		return names;
	}
	
}
