package ants;
import java.util.*;
import graphHandler.*;
import simulator.AntSimulator;

public class Ant{
	
	private int currentNode;
	public int[] getVisitedNodes() {
		return visitedNodes;
	}

	private int currentWeight;
	private int nbVisitedNodes;
	Graph graph;
	
	private int[] visitedNodes;
	private int[] edgesPath;
	
	public Ant( Graph graph) {
		currentNode = AntSimulator.getNestNode(); // As formigas começam pelo nest node
		currentWeight = 0; // Inicialmente, ainda não encontraram nenhum ciclo de Hamilton por isso o peso é 0
	
		//TODO verificar isto
		nbVisitedNodes = 0; // Visitam o nest node. 
		
		
		this.visitedNodes = new int[AntSimulator.getNbNode()]; // Array de nós visitados. Inicialmente, este array é inicializado a -1.
		Arrays.fill(visitedNodes, -1);
		
		visitedNodes[currentNode] = currentNode;// Como a formiga já visitou a origem, é necessário atualizar no array.
		
		this.edgesPath = new int[AntSimulator.getNbNode() - 1]; // Array que vai ter todas as edges do caminho para depois conseguir decrementar feromonas
		
		this.graph = graph;
		
		
	}
	
	void setCurrentNode(int currentNode) {
		this.currentNode = currentNode;
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
		
		return (AntSimulator.getAlpha() + pheromones)/(AntSimulator.getBeta() + weight );
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
	
	void revertCycle(int chosenNode, int chosenEdge) {
		int aux = chosenNode;
		int aux_next;
		while(visitedNodes[aux] != -1) {
			aux_next = visitedNodes[aux];
			visitedNodes[aux] = -1;
			aux = aux_next;
			

			currentWeight -= graph.getWeightFromEdge(edgesPath[nbVisitedNodes]);
			nbVisitedNodes --;
		}
		
		currentWeight -= graph.getWeightFromEdge(chosenEdge);
		nbVisitedNodes --;
	}
	
	double getNextEventTime(int edge) {
		
		double mean = AntSimulator.getDelta() * this.graph.getWeightFromEdge(edge);
		double random = new java.util.Random().nextDouble();
		
		return Math.log(1 - random) * (-mean);
		
		
	}

	// TODO verificar isto
	void resetNbVisitedNodes() {
		this.nbVisitedNodes = 0;
	}

	void resetVisitedNodes() {
		Arrays.fill(this.visitedNodes, -1);
		this.visitedNodes[AntSimulator.getNestNode()] = AntSimulator.getNestNode();
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
			
			float aux = (AntSimulator.getpLevel()* this.currentWeight) / this.graph.getWeightFromEdge(edgesPath[i]);
			graph.addPheromonesFromEdge(edgesPath[i], aux);
						
		}
		
	}
	
	public PheromonedEdge getEdgeFromIndex(int index) {
		return graph.getEdge(index);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
