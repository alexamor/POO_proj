package graphHandler;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph implements IGraph{
	
	int nbNodes;
	ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();
	ArrayList<Edge> edges;
	

	public Graph(int nbNodes, ArrayList<Edge> edges) {
		this.nbNodes = nbNodes;
		this.edges = edges;
	}

	@Override
	public LinkedList<Integer> getAdjacentNodes(int node) {
		return adjList.get(node);
	}

	@Override
	public void createAdjacencyList() {
		
		int i;
		
		//Create arrayList of Lists
		for(i = 0; i < nbNodes; i++) {
			adjList.add(new LinkedList<Integer>());
		}
		
		//Add edges index to Lists
		for(Edge edj : edges) {
			//The -1 is due to the array starting in 0 and the nodes in 1
			adjList.get(edj.iNode).add(edj.jNode);
			adjList.get(edj.jNode).add(edj.iNode);
		}
	}

	@Override
	public String toString() {
		return "Graph [nbNodes=" + nbNodes + ", adjList=" + adjList + "]";
	}

	@Override
	public int getAdjacentFromEdge(int curNode, int edge) {
		if(curNode == edges.get(edge).getjNode())
			return edges.get(edge).getiNODE();
		else if( curNode == edges.get(edge).getiNODE())
			return edges.get(edge).getjNode();
			
		return 0;
	}

	@Override
	public Edge getEdge(int edge) {
		return edges.get(edge);
	}
	
	public float getPheromonesFromEdge(int edge) {
		return getEdge(edge).pheromoneLevel;
		
	}
	
	public int getWeightFromEdge(int edge) {
		return getEdge(edge).weight;
	}


}
