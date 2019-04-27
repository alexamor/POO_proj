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
		
		
		int nbNonvisitedNodes = 0, adjacent = 0, aux_edge = 0, chosen_edge = 0;
		boolean hasNestNode = false;
		LinkedList<Integer> adjacentNodes = null; //vetor de inteiros que apontam para as edges dos adjacentes
		LinkedList<Integer> nonVisitedNodes = null; //vetor com edges de nós não visitados
		LinkedList<Double> edgesCost = null;
		Double[] edgesProbability = null;
		int currentNode = ant.getCurrentNode();
		int nextNode = 0;
		double cost, totalCost = 0;
	
		// obter nós adjacentes
		adjacentNodes = ant.graph.getAdjacentNodes(currentNode);
		
		// TODO verificar se tem nós não visitados
		for(ListIterator<Integer> i=adjacentNodes.listIterator(); i.hasNext();) {
			aux_edge = i.next(); // obter próxima edge 
			adjacent = ant.graph.getAdjacentFromEdge(currentNode, aux_edge); //obter o adjacente tendo em conta a aresta
		
			// se a formiga ainda não visitou este nó, adiciona-se à lista de nós não visitados e calcula-se o seu custo
			if(!ant.hasVisited( adjacent ) ) {
				if(nbNonvisitedNodes == 0) { //cria novas listas se for o primeiro nó não visitado
					nonVisitedNodes = new LinkedList<Integer>();
					edgesCost = new LinkedList<Double>(); 
				}
				nbNonvisitedNodes ++; // incrementa o nr de nós não visitados
				nonVisitedNodes.addLast(aux_edge); //adiciona a aresta à lista
				
				cost = ant.getCostijk(aux_edge); //calcula o custo Cijk
				edgesCost.addLast(cost); //adiciona o custo à sua lista
				totalCost += ant.getCostijk(aux_edge); // incrementa o custo total
				
			}
			else {
				if (adjacent == ant.getNestNode())
					hasNestNode = true;
			}
			
	
		}
		
		//caso apenas tenha nós já visitados
		if(nbNonvisitedNodes == 0) {
			//caso tenha a origem como adjacente e já tenha visitado todos os nós anteriores, o próximo nó é a origem
			if(hasNestNode) {
				if(ant.getNbVisitedNodes() == ant.getNbNodes()) {
					nextNode = ant.getNestNode();
					
					//TODO tem um ciclo!
				}
			}
			
			// numero de adjacentes
			int nbAdjacents = adjacentNodes.size();
			if(hasNestNode)
				nbAdjacents --;
			
			edgesProbability = new Double[adjacentNodes.size()];
			Iterator<Integer> iIterator = adjacentNodes.listIterator();		
			
			// caclcula a probabilidade uniforme de cada aresta, a origem tem probabilidade = 0
			for(int i = 0; i< edgesProbability.length; i++) {
				int origin = iIterator.next();
				if(origin == ant.getNestNode())
					edgesProbability[i] = 0.0;
				else 
					edgesProbability[i] = (double) (1/nbAdjacents);
			}
			
			// escolha do próximo nó, tendo em conta a probabilidade uniforme de cada um
			chosen_edge = this.getChosenNode(edgesProbability); // retorna o indice no vetor de probabilidades
			nextNode = ant.graph.getAdjacentFromEdge(ant.getCurrentNode(), nonVisitedNodes.get(chosen_edge));
			
			//TODO reverter o ciclo 
			
		}
		//caso haja nós por visitar
		else {
			
			//caso só exista um nó para visitar, vai obrigatoriamente para esse
			if( nbNonvisitedNodes == 1) {
				nextNode = ant.graph.getAdjacentFromEdge(currentNode, nonVisitedNodes.getFirst());
			}
			//calcula a probabilidade de cada nó
			else {
				
				edgesProbability = new Double[nbNonvisitedNodes];
				Iterator<Double> dIterator = edgesCost.listIterator();				
				for(int i = 0; i< nbNonvisitedNodes; i++) {
					edgesProbability[i] = dIterator.next()/totalCost;	//Cijk/Ci
				}
				
			}
			
			// escolha do próximo nó, tendo em conta a probabilidade de cada um
			chosen_edge = this.getChosenNode(edgesProbability); // retorna o indice no vetor de probabilidades
			nextNode = ant.graph.getAdjacentFromEdge(ant.getCurrentNode(), nonVisitedNodes.get(chosen_edge));
			
			
		}
		
		
		//TODO final do evento, calcular o tempo que demora até acontecer o próximo e aumentar o nr de nos visitados
		
		// atualizar o current node
		ant.addEdgesPath(ant.getNbVisitedNodes(), chosen_edge);
		ant.addVisitedNode(nextNode);
		
		ant.setCurrentNode(nextNode);
		
		ant.incrementNbVisitedNodes();
		
	}
	
	public double getRandom() {
		return new Random().nextDouble();
	}
	
	public int getChosenNode(Double[] edgesProbability) {
		int aux = -1, node = -1;
		double random = new Random().nextDouble();
		double prob_inf = 0.0, prob_sup=0.0;
		
		
		for(int j = 0; j < edgesProbability.length; j++) {
			prob_sup += edgesProbability[j];
			if((prob_inf < random) && (random <= prob_sup) ) {
				aux = j;
				break;
			}
			prob_inf += edgesProbability[j];
			
		}
		
		return aux;
	}
	
	
	
	
	

	
}
