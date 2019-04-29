package ants;

import eventHandler.*;
import simulator.AntSimulator;

import java.util.*;

public class AntMove extends Event {

	
	// Cada evento de movimento de formiga está associado a uma formiga
	private Ant ant;
	private PEC pec;

	AntMove(Ant ant, double timestamp, PEC pec) {
		super(timestamp);
		this.ant = ant;
		this.pec = pec;
	}

	Ant getAnt() {
		return ant;
	}

	
	@Override
	public void simulateEvent() {
		
		
		int nbNonvisitedNodes = 0, adjacent = 0, auxEdge = 0, chosenEdge = 0;
		int hasNestNode = -1;
		LinkedList<Integer> adjacentNodes = null; //vetor de inteiros que apontam para as edges dos adjacentes
		LinkedList<Integer> nonVisitedNodes = null; //vetor com edges de nós não visitados
		LinkedList<Double> edgesCost = null;
		Double[] edgesProbability = null;
		int currentNode = ant.getCurrentNode();
		int nextNode = 0;
		double cost, totalCost = 0;
		double nextEventTime;
	
		
		// verificar se encontrou um ciclo
		if(ant.getCurrentNode() == AntSimulator.getNestNode() && AntSimulator.getNbNode() == ant.getNbVisitedNodes()) {
			
			ant.increasePheromones();
			
			// TODO ver se o ciclo é o mais pequeno encontrado até agora e se for, devolver o caminho
			if(ant.getCurrentWeight() < AntSimulator.getBestWeight()) {
				AntSimulator.setBestPath(ant.getVisitedNodes());
				AntSimulator.setBestWeight(ant.getCurrentWeight());
			}
			
			//reset dos nós visitados e do nr de nós visitados.
			ant.resetNbVisitedNodes();
			ant.resetVisitedNodes();
		}
		
		
		
		
		// obter nós adjacentes
		adjacentNodes = ant.graph.getAdjacentNodes(currentNode);
		
		for(ListIterator<Integer> i=adjacentNodes.listIterator(); i.hasNext();) {
			auxEdge = i.next(); // obter próxima edge 
			adjacent = ant.graph.getAdjacentFromEdge(currentNode, auxEdge); //obter o adjacente tendo em conta a aresta
		
			// se a formiga ainda não visitou este nó, adiciona-se à lista de nós não visitados e calcula-se o seu custo
			if(!ant.hasVisited( adjacent ) ) {
				if(nbNonvisitedNodes == 0) { //cria novas listas se for o primeiro nó não visitado
					nonVisitedNodes = new LinkedList<Integer>();
					edgesCost = new LinkedList<Double>(); 
				}
				nbNonvisitedNodes ++; // incrementa o nr de nós não visitados
				nonVisitedNodes.addLast(auxEdge); //adiciona a aresta à lista
				
				cost = ant.getCostijk(auxEdge); //calcula o custo Cijk
				edgesCost.addLast(cost); //adiciona o custo à sua lista
				totalCost += ant.getCostijk(auxEdge); // incrementa o custo total
				
			}
			else {
				if (adjacent == AntSimulator.getNestNode())
					hasNestNode = auxEdge;
			}
			
	
		}
		
		//caso apenas tenha nós já visitados
		if(nbNonvisitedNodes == 0) {
			//caso tenha a origem como adjacente e já tenha visitado todos os nós anteriores, o próximo nó é a origem
			if((hasNestNode != -1) && (ant.getNbVisitedNodes() == (AntSimulator.getNbNode() - 1))) {
					nextNode = AntSimulator.getNestNode();
					chosenEdge = hasNestNode;
			}
			else {
				// numero de adjacentes
				int nbAdjacents = adjacentNodes.size();
				if(hasNestNode != -1)
					nbAdjacents --;
				
				edgesProbability = new Double[adjacentNodes.size()];
				Iterator<Integer> iIterator = adjacentNodes.listIterator();		
				
				// caclcula a probabilidade uniforme de cada aresta, a origem tem probabilidade = 0
				for(int i = 0; i< edgesProbability.length; i++) {
					int origin = iIterator.next();
					if(origin == AntSimulator.getNestNode() )
						edgesProbability[i] = 0.0;
					else 
						edgesProbability[i] = (double) (1/nbAdjacents);
				}
				
				// escolha do próximo nó, tendo em conta a probabilidade uniforme de cada um
				chosenEdge = this.getChosenNode(edgesProbability); // retorna o indice no vetor de probabilidades
				nextNode = ant.graph.getAdjacentFromEdge(ant.getCurrentNode(), nonVisitedNodes.get(chosenEdge));
				
				//reverter o ciclo 
				ant.revertCycle(nextNode, chosenEdge);
			}
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
			chosenEdge = this.getChosenNode(edgesProbability); // retorna o indice no vetor de probabilidades
			nextNode = ant.graph.getAdjacentFromEdge(ant.getCurrentNode(), nonVisitedNodes.get(chosenEdge));
			
			//adiciona a Edge no vetor, tendo em conta o nr de nós visitados
			ant.addEdgesPath(ant.getNbVisitedNodes(), chosenEdge);
			
		}
		
		
		
		// verifica se o timestamp do prox evento é depois do final da simulação. se for, nao adiciona ao pec
		nextEventTime = this.ant.getNextEventTime(chosenEdge);
		if(nextEventTime <= AntSimulator.getFinalInst()) {
			
			// adicionar o evento do proximo movimento da formiga
			pec.addEvent(new AntMove(this.ant, nextEventTime, this.pec));

			// atualizar o current node
			ant.addVisitedNode(nextNode);
			ant.setCurrentNode(nextNode);
			// incrementa o nr de nós visitados
			ant.incrementNbVisitedNodes();
			//atualizar o weight
			ant.addCurrentWeight(ant.graph.getWeightFromEdge(chosenEdge));
		}
		
		
	}
	
	public int getChosenNode(Double[] edgesProbability) {
		int aux = -1;
		double random = new Random().nextDouble();
		double prob_inf = 0.0, prob_sup=0.0;
		
		// se o numero aleatorio se encontrar entre a soma das probabilidades dos nós anteriores e a sua probabilidade mais a soma das anteriores
		// então encontrou-se o nó escolhido aleatoriamente
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
