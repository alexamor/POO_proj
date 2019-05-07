package eventHandler;

public abstract class Event {
	
	protected double timestamp;
	/**Construtor**/
	public Event(double timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Event [timestamp=" + timestamp + "]";
	}
	
	/**
	 * Realização do evento atual
	 */
	public void simulateEvent() {}
	
	
}
