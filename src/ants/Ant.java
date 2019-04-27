package ants;
import java.util.*;
import graphHandler.*;

public class Ant{
	
	// TODO tornar static
	public final int alfa;
	public final int beta;
	public final int delta;

	private int currentNode;
	private int currentWeight;
	private int nbVisitedNodes;
	final Graph graph;
	
	private int[] visitedNodes;
	private int[] edgesPath;
	
	public Ant(int nestNode, int nbNodes, Graph graph, int alfa, int beta, int delta) {
		currentNode = nestNode; // As formigas começam pelo nest node
		currentWeight = 0; // Inicialmente, ainda não encontraram nenhum ciclo de Hamilton por isso o peso é 0
		nbVisitedNodes = 1; // Visitam o nest node.
		
		this.visitedNodes = new int[nbNodes]; // Array de nós visitados. Inicialmente, este array é inicializado a -1.
		Arrays.fill(visitedNodes, -1);
		
		visitedNodes[nestNode - 1 ] = nestNode - 1;// Como a formiga já visitou a origem, é necessário atualizar no array.
		
		this.edgesPath = new int[nbNodes - 1]; // Array que vai ter todas as edges do caminho para depois conseguir decrementar feromonas
		
		this.graph = graph;
		
		this.alfa = alfa;
		this.beta = beta;
		this.delta = delta;
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

	double getCostijk() {
		
		
		return currentNode;
	}
	
	
	
	
}
