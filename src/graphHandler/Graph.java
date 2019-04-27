package graphHandler;

import java.util.LinkedList;

public class Graph implements IGraph{
	
	int nbNodes;
	LinkedList<Edge>[] AdjList;
	
	

	public Graph(int nbNodes) {
		this.nbNodes = nbNodes;
	}

	@Override
	public LinkedList<Edge> getAdjacentNodes(int node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEdge(Edge e) {
		// TODO Auto-generated method stub
		
	}

}
