package eventHandler;

public abstract class Event {
	
	protected float timestamp;
	/**Construtor**/
	public Event(float timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Event [timestamp=" + timestamp + "]";
	}
	
	
	
}
