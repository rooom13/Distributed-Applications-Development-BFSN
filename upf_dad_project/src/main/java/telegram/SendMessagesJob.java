package telegram;

import java.util.Date;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/* CLASS SEND MESSAGES JOB
 * Private attributes:
 * 
 * chat_id: destination of the msg
 * text: msg content
 * 
 * Sends message (Telegram bot) using chat_id 
 */
public class SendMessagesJob implements Job {
	// Data
	private long chat_id;
	private String freeSlots;

	// Declaracion Client & target
	private Client telegramClient;
	WebTarget targetSendMessage;

	// Constructor
	public SendMessagesJob() {
		telegramClient = ClientBuilder.newClient();
		targetSendMessage = telegramClient.target("https://api.telegram.org")
				.path("/bot493034514:AAElHVPyaWMFXWrtJNWg5h-yDwGppSDlMKg/sendMessage");
	}

	// Job EXECUTION method
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("Executing RtelegramJob at " + new Date());

		try {
			//Send message
			sendMessage(chat_id, "BFSN:\n" + freeSlots);

		} catch (BadRequestException e) {
			System.out.println("Chat_id: " + chat_id + " is unvalid");
			throw e;

		} catch (Exception e) {
			System.out.println("Problem connecting to https://api.telegram.org\n" + e.getMessage());

		}
	}

	public void setChat_id(long chid) {
		chat_id = chid;
	}

	public void setFreeSlots(String freeSlots) {
		this.freeSlots = freeSlots;
	}

	//Send message to webTarget
	private void sendMessage(long c_id, String text) throws Exception {
		// Msg to send
		Message msg = new Message(c_id, text);
		// Send message
		String response = targetSendMessage.request().post(Entity.entity(msg, MediaType.APPLICATION_JSON_TYPE),
				String.class);
		System.out.println("Message sended: '" + response);

	}

}
