package ants;

import eventHandler.Event;
import java.util.Random;

public class AntMove extends Event {

	public static final int alfa;
	public static final int beta;
	public static final int delta;
	static{
		alfa = 1; //a mudar depois
		beta = 1;
		delta = 1;
	
	}
	
	// Cada evento de movimento de formiga está associado a uma formiga
	private Ant ant;

	AntMove(Ant ant, float timestamp) {
		super(timestamp);
		this.ant = ant;
	}

	Ant getAnt() {
		return ant;
	}

	
	@Override
	public void simulateEvent() {
		
		
		int nbNonvisitedNodes = 0;
		int[] adjacentNodes = null;
		
		int currentNode = ant.getCurrentNode();
	
		// TODO get adjacent nodes
		
		
		// TODO verificar se tem nós não visitados
		for(int i = 0; i < adjacentNodes.length; i++) {
			
			if(!ant.hasVisited(adjacentNodes[i])) {
				nbNonvisitedNodes ++;
				
			}
			else {
				if (nbNonvisitedNodes != 0 ){
					
				}
			}
			
		}
		
		//get random number between 0.0 and 1.0
		double aux = new Random().nextDouble();
		
		
	}
	
	
	
	
	
	
	

	
}
