package eventHandler;

import ants.*;

public class QueueTest {

	public static void main(String[] args) {


		IPEC queue = new PEC();
		
		queue.addEvent(new Observation(2f));
		queue.addEvent(new Observation(5f));
		queue.addEvent(new PheromoneEvap(4f));
		
		Event aux;
		
		
		aux = (Event) queue.removeEvent();
		System.out.println("fst " + aux.timestamp);

		aux = (Event) queue.removeEvent();
		System.out.println("snd " + aux.timestamp);
		
		aux = (Event) queue.removeEvent();
		System.out.println("trd " + aux.timestamp);

	}

}
