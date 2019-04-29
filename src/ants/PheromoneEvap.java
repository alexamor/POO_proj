package ants;

import eventHandler.Event;
import eventHandler.PEC;
import simulator.AntSimulator;

public class PheromoneEvap extends Event{
	
	PheromonedEdge edge;
	PEC pec;
	static int nr = 0;
	

	public PheromoneEvap(float timestamp, PheromonedEdge edge, PEC pec) {
		super(timestamp);
		this.edge = edge;
		this.pec = pec;
	}
	
	@Override
	public void simulateEvent() {
		
		edge.decreasePheromoneLevel(AntSimulator.getRho());
		
		//Caso o nível de feromonas continue positivo agendar a próxima evaporação
		if(edge.getPheromoneLevel() > 0)
		{
			float mean = AntSimulator.getEta();
			double random = new java.util.Random().nextDouble();
			float newTimestamp = (float) (timestamp + Math.log(1 - random) * (-mean));
			
			pec.addEvent(new PheromoneEvap(newTimestamp, edge, pec));	
		}
				
	}

	public static int getNr() {
		return nr;
	}
}
