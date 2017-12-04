package telegram;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import BFSN.BFSNServer;

/* CLASS GET UPDATES  JOB
 * Private attributes:
 * 
 * ClienT and webTarget
 * Updates List
 * 
 * Handles Telegram's Bot response,
 * 		If asked for chat_id send chat_id
 * 		If asked for slots, sends slots
 * 			if user not subscribed, ask to subscribe
 */

public class GetUpdatesJob implements Job {

	// Client & target Declaration
	private final Client RtelegramClient = ClientBuilder.newClient();
	private final WebTarget targetGetUpdates = RtelegramClient.target("https://api.telegram.org")
			.path("/bot493034514:AAElHVPyaWMFXWrtJNWg5h-yDwGppSDlMKg/getUpdates");

	// List of Updates
	private Updates updates;

	// Job EXECUTION method
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("Executing telegramJob at " + new Date());

		try {
			// Get updates from telegram API
			updates = targetGetUpdates.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<Updates>() {
			});

			// get number of Updates
			int nUpdatesFresh = updates.size();

			// Updates variables print
			System.out.println((BFSNServer.firstTime ? "first" : "Not first") + " Count: " + BFSNServer.nUpdates
					+ ", Fresh: " + nUpdatesFresh);

			// If there's new messages
			if (nUpdatesFresh > BFSNServer.nUpdates && !BFSNServer.firstTime) {
				// Get new message's info
				long chat_id = updates.getResult().get(nUpdatesFresh - 1).getMessage().getChat().getId();
				String name = updates.getResult().get(nUpdatesFresh - 1).getMessage().getChat().getFirst_name();
				// Received text
				String rText = updates.getResult().get(nUpdatesFresh - 1).getMessage().getText();

				// To send text
				String text;
				// On received msg
				switch (rText.toLowerCase()) {
				// Return chat_id
				case "id":
					text = "Your chat_id is: " + Long.toString(chat_id);
					break;
				// return slots
				case "slots":

					try {
						text = BFSNServer.getClient(chat_id).freeSlotsStations();
						// If client is not subscribed
					} catch (Exception e) {
						System.out.println("Client with chat_id: " + chat_id + " does not exist." + e.getMessage());
						text = name+ ", you are not currently subscribed to any stations. "
								+ "\nPlease, type 'id' to get you chat id and make a request to /clients/suscribe/<num>/<chat_id>/<stations>";
					}
					break;
				// If not understandable message
				default:
					text = name+", type the following options:"
							+ "\n- id 		: returns you chat_id for subscribe to stations."
							+ "\n- slots	: returns your free slots list of you subscribed stations.";
					break;

				}
				// Send message to chat_id
				BFSNServer.sendMessage(chat_id, text);
				// If no messages
			} else
				System.out.println("No messages!");

			// Update BFSNServer's updates variables
			BFSNServer.firstTime = false;
			BFSNServer.nUpdates = nUpdatesFresh;

		} catch (Exception e) {
			System.out.println("target:" + e.getMessage());
		}

	}

}
