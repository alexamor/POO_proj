package ants;
import java.util.*;
import java.math.*;
import graphHandler.*;

public class Ant{
	
	// TODO tornar static - mudar para o main e ir lá buscar
	private final int alfa;
	private final int beta;
	private final int delta;
	private final int nestNode;
	private final int nbNodes;
	private final float phLevelConst;

	private int currentNode;
	private int currentWeight;
	private int nbVisitedNodes;
	final Graph graph;
	
	private int[] visitedNodes;
	private int[] edgesPath;
	
	public Ant(int nestNode, int nbNodes, Graph graph, int alfa, int beta, int delta, float phLevelConst) {
		currentNode = nestNode; // As formigas começam pelo nest node
		currentWeight = 0; // Inicialmente, ainda não encontraram nenhum ciclo de Hamilton por isso o peso é 0
	
		//TODO verificar isto
		nbVisitedNodes = 0; // Visitam o nest node. 
		
		
		this.visitedNodes = new int[nbNodes]; // Array de nós visitados. Inicialmente, este array é inicializado a -1.
		Arrays.fill(visitedNodes, -1);
		
		visitedNodes[nestNode] = nestNode;// Como a formiga já visitou a origem, é necessário atualizar no array.
		
		this.edgesPath = new int[nbNodes - 1]; // Array que vai ter todas as edges do caminho para depois conseguir decrementar feromonas
		
		this.graph = graph;
		
		this.phLevelConst = phLevelConst;
		this.alfa = alfa;
		this.beta = beta;
		this.delta = delta;
		this.nestNode = nestNode;
		this.nbNodes = nbNodes;
	}
	
	void setCurrentNode(int currentNode) {
		this.currentNode = currentNode;
	}

	int getNbNodes() {
		return nbNodes;
	}

	// verifica se já visitou o nó
	public boolean hasVisited(int node) {
		if(visitedNodes[node] != -1)
			return true;
		else
			return false;
	}

	int getCurrentNode() {
		return currentNode;
	}

	double getCostijk(int edge) {
		float pheromones = graph.getPheromonesFromEdge(edge);
		int weight = graph.getWeightFromEdge(edge);
		
		return (alfa + pheromones)/(beta + weight );
	}
	
	int getNestNode() {
		return nestNode;
	}

	int getAlfa() {
		return alfa;
	}

	int getBeta() {
		return beta;
	}
	
	void incrementNbVisitedNodes() {
		this.nbVisitedNodes++;
	}

	int getNbVisitedNodes() {
		return nbVisitedNodes;
	}
	
	void addEdgesPath(int index, int edge) {
		edgesPath[index] = edge;
	}
	
	void addVisitedNode(int nextNode) {
		visitedNodes[this.currentNode] = nextNode;
	}
	
	void revertCycle(int chosen_node) {
		int aux = chosen_node;
		int aux_next;
		while(visitedNodes[aux] != -1) {
			aux_next = visitedNodes[aux];
			visitedNodes[aux] = -1;
			aux = aux_next;
			

			currentWeight -= graph.getWeightFromEdge(edgesPath[nbVisitedNodes]);
			nbVisitedNodes --;
		}
	}
	
	double getNextEventTime(int edge) {
		
		double mean = this.delta * this.graph.getWeightFromEdge(edge);
		double random = new java.util.Random().nextDouble();
		
		return Math.log(1 - random) * (-mean);
		
		
	}

	// TODO verificar isto
	void resetNbVisitedNodes() {
		this.nbVisitedNodes = 0;
	}

	void resetVisitedNodes() {
		Arrays.fill(this.visitedNodes, -1);
		this.visitedNodes[this.nestNode] = this.nestNode;
	}

	int[] getEdgesPath() {
		return edgesPath;
	}

	public int getCurrentWeight() {
		return currentWeight;
	}

	public void addCurrentWeight(int weight) {
		this.currentWeight += weight;
	}
	
	public void increasePheromones() {
		
		// adicionar feromonas
		for(int i = 0; i < edgesPath.length ; i++) {
			
			float aux = (this.phLevelConst * this.currentWeight) / this.graph.getWeightFromEdge(edgesPath[i]);
			graph.addPheromonesFromEdge(edgesPath[i], aux);
						
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
