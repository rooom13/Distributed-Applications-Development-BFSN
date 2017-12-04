package BFSN;



import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.NotFoundException;

public class Clients {
	
	/* CLIENTS (aka USERS)
	 * 
	 * Object class for clientS list
	 * 
	 * */
	
	
	//	List of clients
	private List<Client> clients;

	//DEFAULT CONSTRUCTORS, GETTERS AND SETTER
	public Clients() {clients = new ArrayList<Client>(); }
	public List<Client> getClients(){return clients; }
	public void setClients(List<Client> c){  clients = c; }
	
	/* Add Client to list
	 * 
	 * */
	public void addClient(Client c)
	{
		clients.add(c);
	}
	
	/* Returns Client reference
	 * Throws NotFoundException if not found
	 * 
	 * */
	public Client getClient(int num)
	{
		for(Client client: clients)
			if( client.getNum() == num )
				return client;
		// If not existing
		System.out.println("Client with num: "+num+" does not exist");
		throw new NotFoundException(); 
	}
	
	public Client getClient(long c_i)
	{
		for(Client client: clients)
			if( client.getChat_id() == c_i )
				return client;
		// If not existing
		System.out.println("Client with chat_id: "+ c_i +" does not exist");
		throw new NotFoundException(); 
	}
	
	/* Finds client and subscribe to stations in id list
	 * 
	 * */
	public void subscribeClientToStation(int num, List<Integer> ss)
	{
		Client client = this.getClient(num);
		client.subscribe(ss);
	}
	
	public String toString() {
		String aux = "";
		for(Client client: clients)
			aux += client.toString();
			return aux;
	}
	
	/* Checks if client exists 
	 * 
	 * */
	public Boolean contains(int num) {
		for(Client c : clients)
			if(c.getNum()== num)
				return true;
		return false;
	}
	
	
	
}