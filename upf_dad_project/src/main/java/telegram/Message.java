package telegram;

/* MESSAGE (aka MESSAGE TO SEND)
 * 
 * Object class for message object.
 * 
 * Only for sending
 * */
public class Message {
	
	// chat id
	private long chat_id;
	// Content
	private String text;

	//DEFAULT CONSTRUCTORS, GETTERS & SETTERS
	public Message(long id, String t) {
		super();
		this.chat_id = id;
		this.text = t;
	}
	
	public Message() {
		super();
		}
	
	public long getChat_id() {
		return chat_id;
	}

	public String getText() {
		return text;
	}
	
	public void setChat_id(long id) {
		chat_id = id;
	}

	public void setText(String t){
		this.text = t;
	}

	@Override
	public String toString() {
		return "Message--> chat_id: " + chat_id + ", text: " + text;
	}

	

}
