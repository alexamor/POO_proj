package ants;
import java.util.*;
import simulator.AntSimulator;

/**
 * Classe Ant: implementa uma Formiga, que irá percorrer o grafo à procura de um ciclo Hamiltoniano.
 * Cada formiga tem informação sobre o nó atual, o peso do ciclo, o nº de nós visitados e o caminho percorrido, ou seja,
 * nós e arestas.
 * 
 * @author Alexandre Filipe, Sofia Salgueiro, José Rocha
 * @since 26-04-2019
 */

public class Ant{
	
	private int currentNode;
	private int currentWeight = 0; // Inicialmente, ainda não encontraram nenhum ciclo de Hamilton por isso o peso é 0
	private int[] visitedNodes;
	private int[] edgesPath;
	
	/**
	 * Ant constructor - inicializa todos os atributos da classe.
	 */
	public Ant() {
		currentNode = AntSimulator.getNestNode(); // As formigas começam pelo nest node
		this.visitedNodes = new int[AntSimulator.getNbNode()]; // Array de nós visitados. Inicialmente, este array é inicializado a -1.
		Arrays.fill(visitedNodes, -1);
		
		visitedNodes[currentNode] = currentNode;// Como a formiga começaa origem, é necessário atualizar no array.
		
		this.edgesPath = new int[AntSimulator.getNbNode()]; // Array que vai ter todas as edges do caminho para depois conseguir decrementar feromonas
	}
	
	/**
	 * Current Node Setter - atualiza o nó atual.
	 * @param currentNode - nó atual
	 */
	public void setCurrentNode(int currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * Verifica se o nó que se pretende testar já foi visitado ou não.
	 * @param node - nó que se pretende testar
	 * @return boolean - "true" se o nó já foi visitado e "false" se ainda não foi visitado.
	 */
	// verifica se já visitou o nó
	public boolean hasVisited(int node) {
		if(visitedNodes[node] != -1)
			return true;
		else
			return false;
	}
	
	
	/**
	 * Current Node Getter - retorna o nó atual.
	 * @return currentNode - nó atual
	 */
	public int getCurrentNode() {
		return currentNode;
	}

	/**
	 * Calcula o custo, Cijk, da aresta dada como parâmetro. Este custo é diretamente proporcional ao nível de feromonas
	 * da resta e inversamente proporcional ao peso da mesma. 
	 * @param edge - aresta
	 * @return Custo calculado, Cijk
	 */
	public double getCostijk(int edge) {
		// Obtém as feromonas da aresta
		float pheromones = AntSimulator.getG().getPheromonesFromEdge(edge);
		// Obtém o peso da aresta
		int weight = AntSimulator.getG().getWeightFromEdge(edge);
		
		// Calcula o custo e retorna-lo
		return (AntSimulator.getAlpha() + pheromones)/(AntSimulator.getBeta() + weight );
	}

	/**
	 * Incrementa o nº de nós visistados pela formiga, ou seja, incrementa o atributo nbVisitedNodes
	 */
	public void incrementNbVisitedNodes() {
	}

	/**
	 * NbVisitedNodes Getter - Percorre o vetor de nós visitados para determinar o nº de nós visitados.
	 * @return número de nós visitados
	 */
	public int getNbVisitedNodes() {
		int nbVis = 0;
		
		for(int i = 0; i < AntSimulator.getNbNode(); i++) {
			if(visitedNodes[i] != -1)
				nbVis++;
		}
		
		return nbVis;
	}
	/**
	 * Adiciona a aresta do caminho ao vetor da formiga.
	 * @param index - índice do vetor onde se vai adicionar a aresta
	 * @param edge - aresta que se pretende adicionar
	 */
	public void addEdgesPath(int index, int edge) {
		edgesPath[index] = edge;
	}
	
	/**
	 * Adiciona o próximo nó no índice do vetor atual, construindo assim o caminho no vetor de índices visitados.
	 * @param nextNode - próximo nó
	 */
	public void addVisitedNode(int nextNode) {
		visitedNodes[this.currentNode] = nextNode;
	}
	
	/**
	 * Quando a formiga se desloca para um nó já visitado, tem de "esquecer" o caminho que fez até chegar a este nó novamente.
	 * Esta função reverte o ciclo no vetor de nós visitados.
	 * @param chosenNode - nó destino.
	 */
	public void revertCycle(int chosenNode) {
		int aux = chosenNode;
		int aux_next;
		
		//Reverte o ciclo colocando os elementos do vetor a -1
		while(aux != currentNode) {
			aux_next = visitedNodes[aux];
			visitedNodes[aux] = -1;
			aux = aux_next;
			
		}
		visitedNodes[aux] = -1;
	}
	
	/**
	 * Calcula o instante em que a formiga chegará ao nó destino para, posteriormente, poder criar o evento com este instante.
	 * O instante é calculado através de uma distribuição exponencial cuja média é diretamente proporcional ao peso da aresta.
	 * @param edge - aresta que a formiga vai percorrer
	 * @param ts - instante/timestamp do evento atual
	 * @return instante em que a formiga chegará ao destino, ou seja, instante do próximo evento
	 */
	public static double getNextEventTime(int edge, double ts) {
		
		double mean = AntSimulator.getDelta() * AntSimulator.getG().getWeightFromEdge(edge);
		double random = new java.util.Random().nextDouble();
		
		return (ts + Math.log(1 - random) * (-mean));
	}

	/**
	 * Coloca o nº de nós visitados a 0.
	 */
	public void resetNbVisitedNodes() {
	}
	
	/**
	 * Faz reset do vetor de nós visitados preenchendo-o completamente a -1.
	 */
	public void resetVisitedNodes() {
		Arrays.fill(this.visitedNodes, -1);
		this.visitedNodes[AntSimulator.getNestNode()] = AntSimulator.getNestNode();
	}

	/**
	 * Edges Path Getter - retorna o vetor de arestas do caminho que a formiga fez.
	 * @return vetor de arestas do caminho que a formiga fez
	 */
	public int[] getEdgesPath() {
		return edgesPath;
	}

	/**
	 * Current Weight Getter - retorna o peso do caminho que a formiga fez.
	 * @return peso do caminho que a formiga fez
	 */
	public int getCurrentWeight() {
		return currentWeight;
	}

	/**
	 * Adiciona o peso dado como parâmetro ao peso do caminho da formiga.
	 * @param weight - peso que se pretende adicionar
	 */
	public void addCurrentWeight(int weight) {
		this.currentWeight += weight;
	}
	
	/**
	 * Percorre o vetor de arestas da formiga, que tem todas as arestas que ela percorreu para fazer o ciclo, e aumenta
	 * o nível de feromonas destas arestas. 
	 */
	public void increasePheromones() {
		
		// percorre o vetor de arestas, calcula o nível de feromonas que tem de aumentar e invoca a função que o faz
		for(int i = 0; i < edgesPath.length ; i++) {
			
			float aux = (AntSimulator.getpLevel()* this.currentWeight) / AntSimulator.getG().getWeightFromEdge(edgesPath[i]);
			AntSimulator.getG().addPheromonesFromEdge(edgesPath[i], aux);
						
		}
		
	}
	
	/**
	 * O índice da aresta no vetor de arestas do grafo é dado como parâmetro e função retorna a aresta correspondente.
	 * @param index - índice da aresta no vetor de arestas do grafo
	 * @return PhermonoedEdge - aresta pretendida
	 */
	public PheromonedEdge getEdgeFromIndex(int index) {
		return AntSimulator.getG().getEdge(index);
	}
	
	/**
	 * Visited Nodes Getter - retorna o vetor de nós visitados, que contém os nós visitados e o caminho que a formiga fez.
	 * @return - vetor de nós visitados
	 */
	public int[] getVisitedNodes() {
		return visitedNodes;
	}

	/**
	 * Calcula o peso do caminho que a formiga fez e atualiza este atributo, currentWeight.
	 */
	public void setCurrentWeight() {
		currentWeight = 0;
		int aux = AntSimulator.getNestNode(), aux_next;
		int aux_edge;
		
		//Percorre todos os nós visitados, a partir da origem, reconstrói o caminho e, assim, calcula o peso do caminho
		// e atualiza o vetor de arestas do caminho da formiga.
		for(int i = 0; i < AntSimulator.getNbNode(); i++) {
			aux_next = visitedNodes[aux];
			aux_edge = AntSimulator.getG().getEdgeFromNodes(aux, aux_next);
			currentWeight += AntSimulator.getG().getWeightFromEdge(aux_edge);
			edgesPath[i] = aux_edge;
			aux = aux_next;
		}
	}
	
	/**
	 * Através do índice do vetor de arestas da formiga, dado como parâmetro, é devolvido o inteiro que identifica a aresta
	 * no vetor de arestas do grafo.
	 * @param i - índice do vetor de arestas da formiga, ao qual se pretende aceder
	 * @return - índice da aresta no vetor de arestas do grafo
	 */
	public int getEdgesPathi(int i) {
		return edgesPath[i];
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
