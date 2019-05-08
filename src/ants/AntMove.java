package ants;

import eventHandler.*;
import simulator.AntSimulator;
import java.util.*;

/**
 * Classe AntMove: implementa o evento de movimento da formiga. Esta classe implementa a classe abstracta Event. 
 * A formiga chega a um nó, verifica se completou um ciclo Hamiltoniano.
 * Caso o tenha feito, através do peso do caminho, vê se o ciclo encontrado tem um peso total inferior ao menor ciclo encontrado até agora.
 * Caso isto se verifique, deve atualizar o peso do menor caminho no AntSimulator, bem como o próprio caminho. Aumenta também o nível de 
 * feromonas de todas as arestas do caminho e cria novos eventos de evaporação para as arestas que não os tenham. Naturalmente, faz também
 * reset às suas variáveis, para poder iniciar um novo caminho.
 * Depois, quer tenha completado um ciclo quer não, decide qual o próximo nó para onde vai, calcula o tempo que demora a chegar ao nó e 
 * adiciona um novo evento, cujo instante é dado pela soma do atual com o do tempo calculado, ao PEC.
 * 
 * @author Alexandre Filipe, Sofia Salgueiro, José Rocha
 * @since 26-04-2019
 */

public class AntMove extends Event {

	
	// Cada evento de movimento de formiga está associado a uma formiga
	private Ant ant;
	private static int nr = 0;

	/**
	 * AntMove Constructor - invoca o constructor da superclasse para inicializar o instante e, no seu próprio constructor, inicializa a formiga associada ao evento.
	 * @param ant - formiga à qual o evento está associado
	 * @param timestamp - instante em que o evento ocorre
	 */
	public AntMove(Ant ant, double timestamp) {
		super(timestamp);
		this.ant = ant;
	}

	/**
	 * Ant Getter - retorna a formiga à qual o evento está associado
	 * @return formiga a que o evento está associado
	 */
	public Ant getAnt() {
		return ant;
	}

	/**
	 * Override da função dada pela classe abstracta Event. Esta função trata de todo o movimento da formiga.
	 * A formiga chega a um nó, verifica se completou um ciclo Hamiltoniano.
	 * Caso o tenha feito, através do peso do caminho, vê se o ciclo encontrado tem um peso total inferior ao menor ciclo encontrado até agora.
	 * Caso isto se verifique, deve atualizar o peso do menor caminho no AntSimulator, bem como o próprio caminho. Aumenta também o nível de 
	 * feromonas de todas as arestas do caminho e cria novos eventos de evaporação para as arestas que não os tenham. Naturalmente, faz também
	 * reset às suas variáveis, para poder iniciar um novo caminho.
	 * Depois, quer tenha completado um ciclo quer não, decide qual o próximo nó para onde vai, calcula o tempo que demora a chegar ao nó e 
	 * adiciona um novo evento, cujo instante é dado pela soma do atual com o do tempo calculado, ao PEC.
	 */
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
			
			// calcula o peso do caminho
			ant.setCurrentWeight();
			
			// verifica se o peso do seu caminho é inferior ao peso do atual menor caminho da simulação. Se isto se verificar, atualiza
			// o caminho da simulação e, consequentemente, também o peso.
			if(ant.getCurrentWeight() < AntSimulator.getBestWeight()) {
				AntSimulator.setBestPath(ant.getVisitedNodes());
				AntSimulator.setBestWeight(ant.getCurrentWeight());
			}
			
			// percorre todas as arestas do caminho e cria um evento de evaporação para cada aresta que não tenha nenhum no PEC
			for(int i=0; i < ant.getEdgesPath().length; i++) {	
				// obtém a aresta
				int aux = ant.getEdgesPathi(i);
				// caso o nível de feromonas seja igual a 0, esta aresta não tem nenhum evento de evaporação no PEC. Então, é necessário criá-lo
				if(AntSimulator.getG().getPheromonesFromEdge(aux) == 0) {
					// Calcula o instante em que ocorrerá a evaporação, cria o evento e adiciona-lo ao PEC.
					double evapTimestamp = PheromoneEvap.getNewTimestamp(this.timestamp);
					if(evapTimestamp < AntSimulator.getFinalInst()) {
						PheromonedEdge evapEdge = ant.getEdgeFromIndex(aux);
						AntSimulator.getPec().addEvent(new PheromoneEvap(evapTimestamp, evapEdge) );
					}
				}
				
			}
			// aumenta o nível de feromonas de todas as arestas do caminho
			ant.increasePheromones();
			
			//reset dos nós visitados e do nr de nós visitados para poder iniciar um novo caminho
			ant.resetNbVisitedNodes();
			ant.resetVisitedNodes();
		}
		
		
		// obter nós adjacentes
		adjacentEdges = AntSimulator.getG().getAdjacentEdges(currentNode);
		
		//System.out.println("Atual: " + currentNode);
		//System.out.println("Adj: " + Arrays.toString(adjacentEdges.toArray()));
		
		for(ListIterator<Integer> i=adjacentEdges.listIterator(); i.hasNext();) {
			auxEdge = i.next(); // obter próxima edge
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
			}
			else {
			// numero de adjacentes
			int nbAdjacents = adjacentEdges.size();
			/*if(hasNestNode != -1)
				nbAdjacents --;*/
			
			edgesProbability = new Double[adjacentEdges.size()];
			//Iterator<Integer> iIterator = adjacentEdges.listIterator();		
			
			// caclcula a probabilidade uniforme de cada aresta, a origem tem probabilidade = 0
			for(int i = 0; i< adjacentEdges.size(); i++) {
				//int origin = iIterator.next();
				/*if(AntSimulator.getG().getAdjacentFromEdge(currentNode, origin) == AntSimulator.getNestNode() )
					edgesProbability[i] = 0.0;
				else */
				edgesProbability[i] = (double) 1/nbAdjacents;
				}
				
			
				// escolha do próximo nó, tendo em conta a probabilidade uniforme de cada um
				chosenEdge = this.getChosenNode(edgesProbability); // retorna o indice no vetor de probabilidades
				nextNode = AntSimulator.getG().getAdjacentFromEdge(ant.getCurrentNode(), adjacentEdges.get(chosenEdge));
				
				//System.out.println("Probab: " + Arrays.toString(edgesProbability) + "-> Chosen " + nextNode);
				
				//reverter o ciclo 
				ant.revertCycle(nextNode);
				hasRevertedCycle = true;
				
				
			}
		}
		//caso haja nós por visitar
		else {
			
			//caso só exista um nó para visitar, vai obrigatoriamente para esse
			if( nbNonVisitedNodes == 1) {
				chosenEdge = nonVisitedNodes.getFirst();
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
				
			}
			
			// atualiza o nó atual para ser o próximo
			ant.setCurrentNode(nextNode);
			

		}
		
		// incrementa o nr destes eventos através da variável estática nr
		nr++;		
	}
	
	/**
	 * Determina o nó para o qual a formiga se irá deslocar. Cada nó é escolhido aleatoriamente e tendo em conta o vetor de probabilidades,
	 * que representa a probabilidade de cada nó, dado como argumento.
	 * @param edgesProbability - vetor de probabilidades dos nós
	 * @return nó escolhido
	 */
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
	
	
	/**
	 * Nr Static Getter - retorna o número de eventos AntMove realizados, dado pleo atributo estático da classe, nr.
	 * @return nº de eventos AntMove realizados
	 */
	public static int getNr() {
		return nr;
	}
	
	
	
	

	
}
