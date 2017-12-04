package telegram;

/* Update
 * 
 * Object class for updates obtained from getUpdates method 
 * from Telegram's API
 * 
 * Attributes, methods and constructors set by default
 * 
 * */
public class Update {

	private int update_id;
	private RMessage message;
	
	public Update() {
		super();
		this.update_id = 0;
		this.message = new RMessage();
	}
	
	public Update(int update_id, RMessage message) {
		super();
		this.update_id = update_id;
		this.message = message;
	}
	public int getUpdate_id() {
		return update_id;
	}
	public void setUpdate_id(int update_id) {
		this.update_id = update_id;
	}
	public RMessage getMessage() {
		return message;
	}
	public void setMessage(RMessage message) {
		this.message = message;
	}
	
	
}
