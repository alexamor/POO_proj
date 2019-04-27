package ants;

import eventHandler.*;
import java.util.*;

public class AntMove extends Event {

	
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
		
		
		int nbNonvisitedNodes = 0, adjacent = 0, aux_edge = 0;
		int hasNestNode = -1;
		LinkedList<Integer> adjacentNodes = null; //vetor de inteiros que apontam para as edges dos adjacentes
		LinkedList<Integer> nonVisitedNodes = null; //vetor com edges de nós não visitados
		
		int currentNode = ant.getCurrentNode();
	
		// obter nós adjacentes
		adjacentNodes = ant.graph.getAdjacentNodes(currentNode);
		
		// TODO verificar se tem nós não visitados
		for(ListIterator<Integer> i=adjacentNodes.listIterator(); i.hasNext();) {
			aux_edge = i.next(); // obter próxima edge 
			adjacent = ant.graph.getAdjacentFromEdge(currentNode, aux_edge); //obter o adjacente tendo em conta a aresta
		
			// se a formiga ainda não visitou este nó, adiciona-se à lista de nós não visitados
			if(!ant.hasVisited( adjacent ) ) {
				if(nbNonvisitedNodes == 0) { //cria nova lista se for o primeiro nó não visitado
					nonVisitedNodes = new LinkedList<Integer>();
				}
				nbNonvisitedNodes ++; // incrementa o nr de nós não visitados
				nonVisitedNodes.addLast(aux_edge); 
				
			}

			
	
		}
		
		//caso apenas tenha nós já visitados
		if(nbNonvisitedNodes == 0) {
			
		}
		
		
		//get random number between 0.0 and 1.0
		double aux = new Random().nextDouble();
		
		
	}
	
	
	
	
	
	
	
	

	
}
