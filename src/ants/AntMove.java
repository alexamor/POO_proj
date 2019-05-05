package ants;

import eventHandler.*;
import simulator.AntSimulator;

import java.util.*;

public class AntMove extends Event {

	
	// Cada evento de movimento de formiga está associado a uma formiga
	private Ant ant;
	private static int nr = 0;

	public AntMove(Ant ant, double timestamp) {
		super(timestamp);
		this.ant = ant;
	}

	public Ant getAnt() {
		return ant;
	}

	
	@Override
	public void simulateEvent() {
		
		
		int nbNonVisitedNodes = 0, adjacent = 0, auxEdge = 0, chosenEdge = 0;
		int hasNestNode = -1;
		LinkedList<Integer> adjacentEdges = null; //vetor de inteiros que apontam para as edges dos adjacentes
		LinkedList<Integer> nonVisitedNodes = null; //vetor com edges de nós não visitados
		LinkedList<Double> edgesCost = null;
		Double[] edgesProbability = null;
		int currentNode = ant.getCurrentNode();
		int nextNode = 0;
		double cost, totalCost = 0;
		double nextEventTime;
		boolean hasRevertedCycle = false;
		
		//System.out.println("\nVisitados: " + Arrays.toString(ant.getVisitedNodes()));
	
		
		// verificar se encontrou um ciclo
		if((ant.getCurrentNode() == AntSimulator.getNestNode()) && (AntSimulator.getNbNode() == ant.getNbVisitedNodes())) {
			
			ant.increasePheromones();
			
			System.out.println("Chegou: " + Arrays.toString(ant.getVisitedNodes()));
			System.out.println("Peso : "+ ant.getCurrentWeight());
			ant.setCurrentWeight();
			System.out.println("Peso 2: " + ant.getCurrentWeight());
			
			if(ant.getCurrentWeight() < AntSimulator.getBestWeight()) {
				AntSimulator.setBestPath(ant.getVisitedNodes());
				AntSimulator.setBestWeight(ant.getCurrentWeight());
			}
			
			for(int i=0; i < ant.getEdgesPath().length; i++) {
				double evapTimestamp = PheromoneEvap.getNewTimestamp(this.timestamp);
				if(evapTimestamp < AntSimulator.getFinalInst()) {
					PheromonedEdge evapEdge = ant.getEdgeFromIndex(i);
					AntSimulator.getPec().addEvent(new PheromoneEvap(evapTimestamp, evapEdge) );
				}
				
			}
			//reset dos nós visitados e do nr de nós visitados.
			ant.resetNbVisitedNodes();
			ant.resetVisitedNodes();
		}
		
		
		
		
		// obter nós adjacentes
		adjacentEdges = AntSimulator.getG().getAdjacentEdges(currentNode);
		
		//System.out.println("Atual: " + currentNode);
		//System.out.println("Adj: " + Arrays.toString(adjacentEdges.toArray()));
		
		for(ListIterator<Integer> i=adjacentEdges.listIterator(); i.hasNext();) {
			auxEdge = i.next(); // obter próxima edge
			//TODO é obter a aresta a partir do adjacente
			adjacent = AntSimulator.getG().getAdjacentFromEdge(currentNode, auxEdge); //obter o adjacente tendo em conta a aresta
		
			// se a formiga ainda não visitou este nó, adiciona-se à lista de nós não visitados e calcula-se o seu custo
			if(!ant.hasVisited( adjacent ) ) {
				if(nbNonVisitedNodes == 0) { //cria novas listas se for o primeiro nó não visitado
					nonVisitedNodes = new LinkedList<Integer>();
					edgesCost = new LinkedList<Double>(); 
				}
				nbNonVisitedNodes ++; // incrementa o nr de nós não visitados
				nonVisitedNodes.addLast(auxEdge); //adiciona a aresta à lista
				
				cost = ant.getCostijk(auxEdge); //calcula o custo Cijk
				edgesCost.addLast(cost); //adiciona o custo à sua lista
				totalCost += ant.getCostijk(auxEdge); // incrementa o custo total
				
				//System.out.print("not visited  ");
				
			}
			else {
				if (adjacent == AntSimulator.getNestNode())
					hasNestNode = auxEdge;
				
				//System.out.print("visited  ");
			}
			
			//System.out.println("adjCode: " + adjacent);
			
	
		}
		
		//System.out.println("Visitados: " + Arrays.toString(ant.getVisitedNodes()));
		
		//if(nbNonVisitedNodes != 0)
			//System.out.println("Nao visitados: " + Arrays.toString(nonVisitedNodes.toArray()) + "  #" + nbNonVisitedNodes);
		
		//caso apenas tenha nós já visitados
		if(nbNonVisitedNodes == 0) {
			//caso tenha a origem como adjacente e já tenha visitado todos os nós anteriores, o próximo nó é a origem
			if((hasNestNode != -1) && (ant.getNbVisitedNodes() == (AntSimulator.getNbNode() - 1))) {
					nextNode = AntSimulator.getNestNode();
					chosenEdge = hasNestNode;
					
					System.out.println("A seguir acaba: " + Arrays.toString(ant.getVisitedNodes()));
			}
			else {
				// numero de adjacentes
				int nbAdjacents = adjacentEdges.size();
				if(hasNestNode != -1)
					nbAdjacents --;
				
				edgesProbability = new Double[adjacentEdges.size()];
				Iterator<Integer> iIterator = adjacentEdges.listIterator();		
				
				// caclcula a probabilidade uniforme de cada aresta, a origem tem probabilidade = 0
				for(int i = 0; i< adjacentEdges.size(); i++) {
					int origin = iIterator.next();
					if(AntSimulator.getG().getAdjacentFromEdge(currentNode, origin) == AntSimulator.getNestNode() )
						edgesProbability[i] = 0.0;
					else 
						edgesProbability[i] = (double) 1/nbAdjacents;
				}
				
				
				// escolha do próximo nó, tendo em conta a probabilidade uniforme de cada um
				chosenEdge = this.getChosenNode(edgesProbability); // retorna o indice no vetor de probabilidades
				nextNode = AntSimulator.getG().getAdjacentFromEdge(ant.getCurrentNode(), adjacentEdges.get(chosenEdge));
				
				//System.out.println("Probab: " + Arrays.toString(edgesProbability) + "-> Chosen " + nextNode);
				
				//reverter o ciclo 
				ant.revertCycle(nextNode, chosenEdge);
				hasRevertedCycle = true;
				
				
			}
		}
		//caso haja nós por visitar
		else {
			
			//caso só exista um nó para visitar, vai obrigatoriamente para esse
			if( nbNonVisitedNodes == 1) {
				chosenEdge = nonVisitedNodes.getFirst();
				//TODO nextNode e chosenEdge estao trocados e é get edge from adjacents
				nextNode = AntSimulator.getG().getAdjacentFromEdge(currentNode, nonVisitedNodes.getFirst());
			}
			//calcula a probabilidade de cada nó
			else {
				
				edgesProbability = new Double[nbNonVisitedNodes];
				Iterator<Double> dIterator = edgesCost.listIterator();				
				for(int i = 0; i< nbNonVisitedNodes; i++) {
					edgesProbability[i] = (double) dIterator.next()/totalCost;	//Cijk/Ci
				}
				
				// escolha do próximo nó, tendo em conta a probabilidade de cada um
				chosenEdge = this.getChosenNode(edgesProbability); // retorna o indice no vetor de probabilidades
				nextNode = AntSimulator.getG().getAdjacentFromEdge(ant.getCurrentNode(), nonVisitedNodes.get(chosenEdge));
				
				//System.out.println("Probab: " + Arrays.toString(edgesProbability) + "-> Chosen " + nextNode);
			}
			
			
			//adiciona a Edge no vetor, tendo em conta o nr de nós visitados
			ant.addEdgesPath(ant.getNbVisitedNodes(), chosenEdge);
			ant.setCurrentWeight();
			
		}
		
		
		
		// verifica se o timestamp do prox evento é depois do final da simulação. se for, nao adiciona ao pec
		nextEventTime = Ant.getNextEventTime(chosenEdge, timestamp);
		if(nextEventTime < AntSimulator.getFinalInst()) {
			
			// adicionar o evento do proximo movimento da formiga
			AntSimulator.getPec().addEvent(new AntMove(this.ant, nextEventTime));
			
			
			if(!hasRevertedCycle)
			{
				// atualizar o current node
				ant.addVisitedNode(nextNode);
				
				
				
				// incrementa o nr de nós visitados
				ant.incrementNbVisitedNodes();
				//atualizar o weight
				ant.addCurrentWeight(AntSimulator.getG().getWeightFromEdge(chosenEdge));
			}
			
			ant.setCurrentNode(nextNode);
			ant.setCurrentWeight();
			

		}
		
		
		nr++;		
	}
	
	public int getChosenNode(Double[] edgesProbability) {
		int aux = -1;
		double random = new Random().nextDouble();
		double prob_inf = 0.0, prob_sup=0.0;
		
		// se o numero aleatorio se encontrar entre a soma das probabilidades dos nós anteriores e a sua probabilidade mais a soma das anteriores
		// então encontrou-se o nó escolhido aleatoriamente
		for(int j = 0; j < edgesProbability.length; j++) {
			prob_sup += edgesProbability[j];
			if((prob_inf <= random) && (random < prob_sup) ) {
				aux = j;
				break;
			}
			prob_inf += edgesProbability[j];
			
		}
		
		return aux;
	}

	public static int getNr() {
		return nr;
	}
	
	
	
	

	
}
