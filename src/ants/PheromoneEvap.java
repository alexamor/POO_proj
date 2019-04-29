package ants;

import eventHandler.Event;
import eventHandler.PEC;
import simulator.AntSimulator;

public class PheromoneEvap extends Event{
	
	private PheromonedEdge edge;
	private static int nr = 0;
	

	public PheromoneEvap(double timestamp, PheromonedEdge edge) {
		super(timestamp);
		this.edge = edge;
	}
	
	@Override
	public void simulateEvent() {
		
		edge.decreasePheromoneLevel(AntSimulator.getRho());
		
		//Caso o nível de feromonas continue positivo agendar a próxima evaporação
		double newTimestamp = getNewTimestamp(timestamp);
		
		if(edge.getPheromoneLevel() > 0 && newTimestamp < AntSimulator.getFinalInst())
			AntSimulator.getPec().addEvent(new PheromoneEvap(newTimestamp, edge));	
				
	}

	public static int getNr() {
		return nr;
	}
	
	public static double getNewTimestamp(double timestamp) {
		float mean = AntSimulator.getEta();
		double random = new java.util.Random().nextDouble();
		double newTimestamp = timestamp + Math.log(1 - random) * (-mean);
		return newTimestamp;
	}
}
