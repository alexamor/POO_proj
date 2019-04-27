package graphHandler;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IGraph {
	
	LinkedList<Integer> getAdjacentNodes(int node);
	void addEdges(ArrayList<Edge> e);
	

}
