package graphHandler;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph implements IGraph{
	
	int nbNodes;
	ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();
	
	

	public Graph(int nbNodes) {
		this.nbNodes = nbNodes;
	}

	@Override
	public LinkedList<Integer> getAdjacentNodes(int node) {
		return adjList.get(node);
	}

	@Override
	public void addEdges(ArrayList<Edge> e) {
		
		int i;
		
		//Create arrayList of Lists
		for(i = 0; i < nbNodes; i++) {
			adjList.add(new LinkedList<Integer>());
		}
		
		//Add edges index to Lists
		for(Edge edj : e) {
			//The -1 is due to the array starting in 0 and the nodes in 1
			adjList.get(edj.i_NODE - 1).add(edj.j_NODE - 1);
			adjList.get(edj.j_NODE - 1).add(edj.i_NODE - 1);
		}
	}

	@Override
	public String toString() {
		return "Graph [nbNodes=" + nbNodes + ", adjList=" + adjList + "]";
	}

}
