package ants;
import java.util.*;
import graphHandler.*;

public class Ant{
	
	// TODO tornar static
	private final int alfa;
	private final int beta;
	private final int delta;
	private final int nestNode;
	private final int nbNodes;

	private int currentNode;
	private int currentWeight;
	private int nbVisitedNodes;
	final Graph graph;
	
	private int[] visitedNodes;
	private int[] edgesPath;
	
	public Ant(int nestNode, int nbNodes, Graph graph, int alfa, int beta, int delta) {
		currentNode = nestNode; // As formigas começam pelo nest node
		currentWeight = 0; // Inicialmente, ainda não encontraram nenhum ciclo de Hamilton por isso o peso é 0
		nbVisitedNodes = 0; // Visitam o nest node.
		
		this.visitedNodes = new int[nbNodes]; // Array de nós visitados. Inicialmente, este array é inicializado a -1.
		Arrays.fill(visitedNodes, -1);
		
		visitedNodes[nestNode - 1 ] = nestNode - 1;// Como a formiga já visitou a origem, é necessário atualizar no array.
		
		this.edgesPath = new int[nbNodes - 1]; // Array que vai ter todas as edges do caminho para depois conseguir decrementar feromonas
		
		this.graph = graph;
		
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
	
	
	
	
}
