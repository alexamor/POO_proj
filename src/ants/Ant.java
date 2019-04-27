package ants;
import java.util.*;

public class Ant{

	private int currentNode;
	private int currentWeight;
	private int nbVisitedNodes;
	
	private int[] visitedNodes;
	
	public Ant(int nestNode, int nbNodes) {
		currentNode = nestNode; // As formigas começam pelo nest node
		currentWeight = 0; // Inicialmente, ainda não encontraram nenhum ciclo de Hamilton por isso o peso é 0
		nbVisitedNodes = 1; // Visitam o nest node.
		
		this.visitedNodes = new int[nbNodes]; // Array de nós visitados. Inicialmente, este array é inicializado a -1.
		Arrays.fill(visitedNodes, -1);
		
		visitedNodes[nestNode - 1 ] = nestNode - 1;// Como a formiga já visitou a origem, é necessário atualizar no array.
		
	}
	
	// verifica se já visitou o nó
	public boolean hasVisited(int node) {
		if(visitedNodes[node -1] != -1)
			return true;
		else
			return false;
	}

	int getCurrentNode() {
		return currentNode;
	}

	
	
	
	
}
