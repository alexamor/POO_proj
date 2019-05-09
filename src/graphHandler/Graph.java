package graphHandler;

import java.util.ArrayList;
import java.util.LinkedList;

import ants.PheromonedEdge;


public class Graph implements IGraph{
		
	/**Quantidade de nós no grafo*/
	private int nbNodes;
	/**Lista de adjacências, guarda os índices das arestas no vetor de arestas*/
	private ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>(); //Vetor com as edges adjacentes, regista o indice da edge no vetor de Edges
	/**Vetor de arestas*/
	private ArrayList<PheromonedEdge> edges; //Vetor de Arestas
	

	public Graph(int nbNodes, ArrayList<PheromonedEdge> edges) {
		this.nbNodes = nbNodes;
		this.edges = edges;
	}
	/**
	 * @param node - nó para qual se quer arestas adjacentes
	 * @return lista de índices no vetor de arestas das arestas paralelas
	 */
	@Override
	public LinkedList<Integer> getAdjacentEdges(int node) {
		return adjList.get(node);
	}
	/**
	 * Com o vetor de arestas completo criar uma lista de adjacências para facilitar a procura depois
	 */
	@Override
	public void createAdjacencyList() {
		
		int i;
		
		//Criação do vetor de listas
		for(i = 0; i < nbNodes; i++) {
			adjList.add(new LinkedList<Integer>());
		}
		
		i = 0;
		
		//Adicionar o index da aresta ao vetor de Listas de adjacências
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
	
	/**
	 * @param curNode - nó da aresta
	 * @param edge - aresta selecionada
	 * @return nó na outra ponta da aresta
	 */
	@Override
	public int getAdjacentFromEdge(int curNode, int edge) {
		//As arestas são bidirecionais por isso acrescentar a aresta a ambos os nós
		if(curNode == edges.get(edge).getjNode())
			return edges.get(edge).getiNode();
		else if( curNode == edges.get(edge).getiNode())
			return edges.get(edge).getjNode();
			
		return 0;
	}
	
	/**
	 * @param edge - índice da aresta selecionada
	 * @return aresta selecionada
	 */
	@Override
	public PheromonedEdge getEdge(int edge) {
		return (PheromonedEdge) edges.get(edge);
	}
	
	/**
	 * 
	 * @param edge - índice da aresta
	 * @return nível atual de feromonas na aresta
	 */
	public float getPheromonesFromEdge(int edge) {
		return getEdge(edge).getPheromoneLevel();
	}
	
	/**
	 * 
	 * @param edge - índice da aresta
	 * @return peso da aresta
	 */
	public int getWeightFromEdge(int edge) {
		return getEdge(edge).getWeight();
	}
	
	/**
	 * 
	 * @param edge - índice da aresta
	 * @param pheromones - quantidade de feromonas a adicionar à aresta
	 */
	public void addPheromonesFromEdge(int edge,float pheromones) {
		getEdge(edge).increasePheromoneLevel(pheromones);
	}
	
	/**
	 * 
	 * @return número de arestas
	 */
	public int nrOfEdges() {
		return edges.size();
	}
	
	/**
	 * 
	 * @param nodeI - nó numa extremidade da aresta
	 * @param nodeJ - nó na outra extremidade da aresta
	 * @return índice da aresta que liga os 2 nós
	 */
	public int getEdgeFromNodes(int nodeI, int nodeJ) {
		LinkedList<Integer> auxEdges = getAdjacentEdges(nodeI);
		int aux = 0;
		for(int i = 0; i< auxEdges.size(); i++) {
			aux = auxEdges.get(i);
			if((this.edges.get(aux).jNode == nodeJ) || (this.edges.get(aux).iNode == nodeJ))
				return aux;
				
		}
		return -1;
		
	}

}
