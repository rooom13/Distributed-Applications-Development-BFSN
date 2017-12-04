package telegram;

import java.util.ArrayList;
import java.util.List;

/* Updates (List of Update)
 * 
 * Object class for updates list obtained from getUpdates method 
 * from Telegram's API
 * 
 * Attributes, methods and constructors set by default
 * 
 * */
public class Updates {

	Boolean ok;
	List<Update> result;
	
	public Updates() {
		super();
		this.ok = false;
		this.result = new ArrayList<Update>();
	}
	
	public Updates(Boolean ok, List<Update> result) {
		super();
		this.ok = ok;
		this.result = result;
	}
	public Boolean getOk() {
		return ok;
	}
	public void setOk(Boolean ok) {
		this.ok = ok;
	}
	public List<Update> getResult() {
		return result;
	}
	public void setResult(List<Update> result) {
		this.result = result;
	}
	public int size() {
		return result.size();
	}
	
	
}
