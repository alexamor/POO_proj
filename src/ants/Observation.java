package ants;

import eventHandler.Event;
import eventHandler.PEC;

public class Observation extends Event{
	
	PEC pec;
	int nr;

	

	public Observation(double timestamp, PEC pec, int nr) {
		super(timestamp);
		this.pec = pec;
		this.nr = nr;
	}



	@Override
	public void simulateEvent() {
		
		System.out.println("Observation " + nr + ":");
		System.out.println("                Present instant:               " + timestamp);
		System.out.println("                Number of move events:         " + AntMove.getNr());
		System.out.println("                Number of evaporation events:  " + PheromoneEvap.getNr());
		//TODO adicionar o ciclo depois
	}
	
	
}
