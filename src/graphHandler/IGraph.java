package graphHandler;

//import java.util.ArrayList;
import java.util.LinkedList;

public interface IGraph {
	
	/**
	 * 
	 * @param node - nó para o qual se quer adjacências
	 * @return lista de índices das arestas adjacentes
	 */
	public LinkedList<Integer> getAdjacentEdges(int node);
	
	/**
	 * Criação da lista das adjacências
	 */
	public void createAdjacencyList();
	
	/**
	 * @param curNode - nó da aresta
	 * @param edge - aresta selecionada
	 * @return nó na outra ponta da aresta
	 */
	public int getAdjacentFromEdge(int curNode, int edge);
	
	/**
	 * @param edge - índice da aresta selecionada
	 * @return aresta selecionada
	 */
	public Edge getEdge(int edge);
}
