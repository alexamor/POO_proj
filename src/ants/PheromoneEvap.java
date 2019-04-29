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
		
		//Só realiza o decremento das feromonas caso estas tenham um nível positivo
		if(edge.getPheromoneLevel() > 0)
			edge.decreasePheromoneLevel(AntSimulator.getRho());
		
		//colocar na queue próxima evaporação
		float mean = AntSimulator.getEta();
		double random = new java.util.Random().nextDouble();
		float newTimestamp = (float) (timestamp + Math.log(1 - random) * (-mean));
		
		pec.addEvent(new PheromoneEvap(newTimestamp, edge, pec));		
	}

	public static int getNr() {
		return nr;
	}
	
	

}
