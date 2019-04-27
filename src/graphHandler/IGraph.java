package graphHandler;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IGraph {
	
	LinkedList<Integer> getAdjacentNodes(int node);
	void createAdjacencyList();
	int getAdjacentFromEdge(int curNode, int edge);
}
