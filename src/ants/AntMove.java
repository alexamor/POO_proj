package ants;

import eventHandler.*;
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
		int adjacent = 0;
		int aux_edge = 0;
		LinkedList<Integer> adjacentNodes = null; //vetor de inteiros que apontam para as edges dos adjacentes
		LinkedList<Integer> nonVisitedNodes = null; //vetor com edges de nós não visitados
		
		int currentNode = ant.getCurrentNode();
	
		// obter nós adjacentes
		adjacentNodes = ant.graph.getAdjacentNodes(currentNode);
		
		// TODO verificar se tem nós não visitados
		for(ListIterator<Integer> i=adjacentNodes.listIterator(); i.hasNext();) {
			aux_edge = i.next();
			adjacent = ant.graph.getAdjacentFromEdge(currentNode, aux_edge);
		
			if(!ant.hasVisited( adjacent ) ) {
				if(nbNonvisitedNodes == 0) {
					nonVisitedNodes = new LinkedList<Integer>();
				}
				nbNonvisitedNodes ++;
				nonVisitedNodes.addLast(aux_edge);
				
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
