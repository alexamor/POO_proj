package graphHandler;

import java.util.LinkedList;

public interface IGraph {
	
	LinkedList<Edge> getAdjacentNodes(int node);
	void addEdge(Edge e);
	

}
