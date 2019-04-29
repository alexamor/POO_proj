package ants;

import eventHandler.Event;

public class PheromoneEvap extends Event{
	
	PheromonedEdge edge;
	

	public PheromoneEvap(float timestamp) {
		super(timestamp);
	}
	
	@Override
	public void simulateEvent() {
		
		//Só realiza o decremento das feromonas caso estas tenham um nível positivo
		if(edge.getPheromoneLevel() > 0) {
			
		}
		
	}

}
