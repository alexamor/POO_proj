package graphHandler;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IGraph {
	
	public LinkedList<Integer> getAdjacentNodes(int node);
	public void createAdjacencyList();
	public int getAdjacentFromEdge(int curNode, int edge);
	public Edge getEdge(int edge);
}
