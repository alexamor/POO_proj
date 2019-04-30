package graphHandler;

import java.util.ArrayList;
import java.util.LinkedList;

import ants.PheromonedEdge;


public class Graph implements IGraph{
	
	private int nbNodes;
	private ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();
	private ArrayList<PheromonedEdge> edges;
	

	public Graph(int nbNodes, ArrayList<PheromonedEdge> edges) {
		this.nbNodes = nbNodes;
		this.edges = edges;
	}

	@Override
	public LinkedList<Integer> getAdjacentEdges(int node) {
		return adjList.get(node);
	}

	@Override
	public void createAdjacencyList() {
		
		int i;
		
		//Create arrayList of Lists
		for(i = 0; i < nbNodes; i++) {
			adjList.add(new LinkedList<Integer>());
		}
		
		i = 0;
		
		//Add edges index to Lists
		for(Edge edj : edges) {
			adjList.get(edj.iNode).add(i);
			adjList.get(edj.jNode).add(i);
			i++;
		}
	}

	@Override
	public String toString() {
		return "Graph [nbNodes=" + nbNodes + ", adjList=" + adjList + "]";
	}

	@Override
	public int getAdjacentFromEdge(int curNode, int edge) {
		if(curNode == edges.get(edge).getjNode())
			return edges.get(edge).getiNode();
		else if( curNode == edges.get(edge).getiNode())
			return edges.get(edge).getjNode();
			
		return 0;
	}

	@Override
	public PheromonedEdge getEdge(int edge) {
		return (PheromonedEdge) edges.get(edge);
	}
	
	
	public float getPheromonesFromEdge(int edge) {
		return getEdge(edge).getPheromoneLevel();
	}
	
	
	public int getWeightFromEdge(int edge) {
		return getEdge(edge).getWeight();
	}
	
	public void addPheromonesFromEdge(int edge,float pheromones) {
		getEdge(edge).increasePheromoneLevel(pheromones);
	}

	public int nrOfEdges() {
		return edges.size();
	}

}
