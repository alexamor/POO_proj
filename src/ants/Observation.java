package ants;

import eventHandler.Event;
import simulator.AntSimulator;

public class Observation extends Event{
	
	private static int nr = 1;

	

	public Observation(double timestamp) {
		super(timestamp);
	}



	@Override
	public void simulateEvent() {
		
		//Imprimir informação
		System.out.println("Observation " + nr + ":");
		System.out.println("                Present instant:               " + timestamp);
		System.out.println("                Number of move events:         " + AntMove.getNr());
		System.out.println("                Number of evaporation events:  " + PheromoneEvap.getNr());
		
		//Só imprime o ciclo caso algo já tenha sido determinado
		if(AntSimulator.getBestWeight() != Integer.MAX_VALUE)
			System.out.println("                Hamiltonian cycle:             " + AntSimulator.getBestPath());
		
		if(nr < 20) {
			//Colocar na pilha a próxima observação
			AntSimulator.getPec().addEvent(new Observation(timestamp + AntSimulator.getFinalInst()/20));
		}
		System.out.println("Best weight:" + AntSimulator.getBestWeight());
		
		nr++;
	}
		
	
	
}
