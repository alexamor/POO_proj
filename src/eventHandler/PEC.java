package eventHandler;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PEC implements IPEC{
	
	protected PriorityQueue<Event> pec = new PriorityQueue<Event>(50, 
		(Comparator<? super Event>) new Comparator<Event>() {
			public int compare(Event a, Event b) {
				if(a.timestamp > b.timestamp) 
					return 1;
				else if(a.timestamp == b.timestamp)
					return 0;
				else
					return -1;
			}
		}
	);
	//Nota: verificar o <? superEvent>


	@Override
	public void addEvent(Object ev) {
		pec.add((Event) ev);
	}

	@Override
	public Object removeEvent() {
		Event x;
		
		x = pec.remove();
		return x;
	}

	@Override
	public String toString() {
		return "PEC [pec=" + pec + "]";
	}
	
	public boolean isEmpty() {
		return pec.isEmpty();
	}

}
