package eventHandler;

public abstract class Event {
	/** instante em que o evento ocorre */
	protected double timestamp;
	
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
