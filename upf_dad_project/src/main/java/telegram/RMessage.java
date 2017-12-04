package telegram;

/* RMessage (Received Message)
 * 
 * Object class for messages obtained from getUpdates method 
 * from Telegram's API
 * 
 * Attributes, methods and constructors set by default
 * 
 * */
public class RMessage {

	private int message_id;
	private Chat chat;
	private int Date;
	private String text;

	public RMessage() {
		super();
		this.message_id = 0;
		this.chat = new Chat();
		Date = 0;
		this.text = "";
	}

	public RMessage(int message_id, Chat chat, int date, String text) {
		super();
		this.message_id = message_id;
		this.chat = chat;
		Date = date;
		this.text = text;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public int getDate() {
		return Date;
	}

	public void setDate(int date) {
		Date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
