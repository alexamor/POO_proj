package ants;

import eventHandler.Event;
import java.util.*;

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
		LinkedList<Integer> adjacentNodes = null;
		LinkedList<Integer> nonVisitedNodes = null;
		
		int currentNode = ant.getCurrentNode();
	
		// TODO get adjacent nodes
		
		
		// TODO verificar se tem nós não visitados
		for(ListIterator<Integer> i=adjacentNodes.listIterator(); i.hasNext();) {
			
			/*if(!ant.hasVisited(i.nex)) {
				nbNonvisitedNodes ++;
				nonVisitedNodes.addLast(e);
				
			}
			else {
				if (nbNonvisitedNodes != 0 ){
					
				}
			}*/
			
		}
		
		//get random number between 0.0 and 1.0
		double aux = new Random().nextDouble();
		
		
	}
	
	
	
	
	
	
	
	

	
}
