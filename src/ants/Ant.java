package ants;
import java.util.*;
import simulator.AntSimulator;

public class Ant{
	
	private int currentNode;
	private int currentWeight;
	private int nbVisitedNodes;
	
	private int[] visitedNodes;
	private int[] edgesPath;
	
	public Ant() {
		currentNode = AntSimulator.getNestNode(); // As formigas começam pelo nest node
		currentWeight = 0; // Inicialmente, ainda não encontraram nenhum ciclo de Hamilton por isso o peso é 0
	
		//TODO verificar isto
		nbVisitedNodes = 0; // Visitam o nest node. 
		
		
		this.visitedNodes = new int[AntSimulator.getNbNode()]; // Array de nós visitados. Inicialmente, este array é inicializado a -1.
		Arrays.fill(visitedNodes, -1);
		
		visitedNodes[currentNode] = currentNode;// Como a formiga já visitou a origem, é necessário atualizar no array.
		
		this.edgesPath = new int[AntSimulator.getNbNode()]; // Array que vai ter todas as edges do caminho para depois conseguir decrementar feromonas
	
	}
	
	public void setCurrentNode(int currentNode) {
		this.currentNode = currentNode;
	}

	// verifica se já visitou o nó
	public boolean hasVisited(int node) {
		if(visitedNodes[node] != -1)
			return true;
		else
			return false;
	}

	public int getCurrentNode() {
		return currentNode;
	}

	public double getCostijk(int edge) {
		float pheromones = AntSimulator.getG().getPheromonesFromEdge(edge);
		int weight = AntSimulator.getG().getWeightFromEdge(edge);
		
		return (AntSimulator.getAlpha() + pheromones)/(AntSimulator.getBeta() + weight );
	}

	
	public void incrementNbVisitedNodes() {
		this.nbVisitedNodes++;
	}

	public int getNbVisitedNodes() {
		//return nbVisitedNodes;
		int nbVis = 0;
		
		for(int i = 0; i < AntSimulator.getNbNode(); i++) {
			if(visitedNodes[i] != -1)
				nbVis++;
		}
		
		return nbVis;
	}
	
	public void addEdgesPath(int index, int edge) {
		edgesPath[index] = edge;
	}
	
	public void addVisitedNode(int nextNode) {
		visitedNodes[this.currentNode] = nextNode;
	}
	
	public void revertCycle(int chosenNode, int chosenEdge) {
		int aux = chosenNode;
		int aux_next;
		while(aux != currentNode) {
			aux_next = visitedNodes[aux];
			visitedNodes[aux] = -1;
			aux = aux_next;
			
			//System.out.println("nbVis " + nbVisitedNodes + " edge path " + /*edgesPath[nbVisitedNodes] + " size of " + */AntSimulator.getG().nrOfEdges());
			currentWeight -= AntSimulator.getG().getWeightFromEdge(edgesPath[getNbVisitedNodes()]);
			nbVisitedNodes --;
		}
		
		visitedNodes[aux] = -1;
		currentWeight -= AntSimulator.getG().getWeightFromEdge(chosenEdge);
		nbVisitedNodes --;
	}
	
	public static double getNextEventTime(int edge, double ts) {
		
		double mean = AntSimulator.getDelta() * AntSimulator.getG().getWeightFromEdge(edge);
		double random = new java.util.Random().nextDouble();
		
		return (ts + Math.log(1 - random) * (-mean));
	}

	public void resetNbVisitedNodes() {
		this.nbVisitedNodes = 0;
	}

	public void resetVisitedNodes() {
		Arrays.fill(this.visitedNodes, -1);
		this.visitedNodes[AntSimulator.getNestNode()] = AntSimulator.getNestNode();
	}

	public int[] getEdgesPath() {
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
			
			float aux = (AntSimulator.getpLevel()* this.currentWeight) / AntSimulator.getG().getWeightFromEdge(edgesPath[i]);
			AntSimulator.getG().addPheromonesFromEdge(edgesPath[i], aux);
						
		}
		
	}
	
	public PheromonedEdge getEdgeFromIndex(int index) {
		return AntSimulator.getG().getEdge(index);
	}
	
	

	public int[] getVisitedNodes() {
		return visitedNodes;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
