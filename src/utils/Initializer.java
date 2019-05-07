package utils;

import java.util.ArrayList;
import graphHandler.Graph;
import ants.PheromonedEdge;
import java.lang.reflect.Field;

/**
 * Classe Initializer : implementa o objecto criado a partir do parse do documento XML,
 * sendo posteriormente utilizado para construir o Grafo e instanciar as propriedades do
 * sistema na classe AntSimulator. 
 * 
 * @author Alexandre Filipe, Sofia Salgueiro, José Rocha
 * @since 07-05-2019
 */

public class Initializer {

	ArrayList<PheromonedEdge> edges;
	
	float pLevel;
	float finalInst;
	int antColSize;
	int nbNode;
	int nestNode;
	float alpha, beta, delta, eta, rho;
	
	/**
	 * Initializer construtor default.
	 */
	public Initializer() {}
	
	/**
	 * Initializer contrutor - inicializa os atributos da classe
	 * @param pLevel - atributo propagation level
	 * @param finalInst - atributo instante final
	 * @param antColSize - atributo nrº de elemento da colónia
	 * @param nbNode - atributo nbnode
	 * @param nestNode - atributo nestNode
	 * @param alpha - atributo alpha
	 * @param beta - atributo beta
	 * @param delta - atributo delta
	 * @param eta - atributo eta
	 * @param rho - atributo rho 
	 * @return Initializer - Objecto Initializer utilizado para construir o sistema de simulação
	 */
	public Initializer(ArrayList<PheromonedEdge> edges, float pLevel, float finalInst, int antColSize, int nbNode, int nestNode,
			float alpha, float beta, float delta, float eta, float rho) {
		this.pLevel = pLevel;
		this.finalInst = finalInst;
		this.antColSize = antColSize;
		this.edges = edges;
		this.alpha = alpha;
		this.beta = beta;
		this.delta = delta;
		this.eta = eta;
		this.rho = rho;
		this.nestNode = nestNode;
		this.nbNode = nbNode;
	}
	
	/**
	 * Graph CreateGraph - devolve um Grafo a partir da ArrayList de arestas e do nbNode
	 * @param nbNode - atributo nbNode
	 * @param edges - atributo edges 
	 * @return Graph 
	 */
	public Graph CreateGraph() {	
		Graph g = new Graph(nbNode, edges);
		return g;
	}
	
	/**
	 * Initializer toString - devolve uma String com os atributos da classe
	 */
	public String toString()
	{
		StringBuilder output = new StringBuilder();
		String newline = System.getProperty("line.separator");
		
		Field[] fields = this.getClass().getDeclaredFields();
		
		//PRINT FIELDS
		for(Field field : fields)
		{
			try 
			{
				if (field.getName() != "edges")
				{
					output.append("[" + field.getName().toUpperCase() + "]: " + field.get(this) + newline);
				}
			} 
			catch (IllegalArgumentException e) { e.printStackTrace(); } 
			catch (IllegalAccessException e) { e.printStackTrace(); }
		}
		
		output.append(newline);
		
		//PRINT EDGES
		for(int i=0; i<edges.size(); i++) 
		{
			output.append(this.edges.get(i).toString() + newline);
		}
		
		return output.toString();
	}
	
	/**
	 * Initializer getNbNode() - devolve o atributo nbNode
	 * @return nbNode 
	 */
	public int getNbNode() {
		return nbNode;
	}
	
	/**
	 * Initializer getEdges() - devolve a ArrayList de Edges
	 * @return edges 
	 */
	public ArrayList<PheromonedEdge> getEdges(){
		return edges;
	}
	
	/**
	 * Initializer getPLevel() - devolve o atributo pLevel
	 * @return pLevel 
	 */	
	public float getPLevel() {
		return pLevel;
	}
	
	/**
	 * Initializer getFinalInst() - devolve o atributo finalInst
	 * @return finalInst 
	 */		
	public float getFinalInst() {
		return finalInst;
	}

	/**
	 * Initializer getAntColSize() - devolve o atributo antColSize
	 * @return antColSize 
	 */	
	public int getAntColSize() {
		return antColSize;
	}

	/**
	 * Initializer getNestNode() - devolve o atributo nestNode
	 * @return nestNode 
	 */	
	public int getNestNode() {
		return nestNode;
	}

	/**
	 * Initializer getpLevel() - devolve o atributo pLevel
	 * @return pLevel 
	 */	
	public float getpLevel() {
		return pLevel;
	}

	/**
	 * Initializer getAlpha() - devolve o atributo alpha
	 * @return alpha 
	 */	
	public float getAlpha() {
		return alpha;
	}

	/**
	 * Initializer getBeta() - devolve o atributo beta
	 * @return beta 
	 */	
	public float getBeta() {
		return beta;
	}

	/**
	 * Initializer getDelta() - devolve o atributo delta
	 * @return delta 
	 */	
	public float getDelta() {
		return delta;
	}

	/**
	 * Initializer getEta() - devolve o atributo eta
	 * @return eta 
	 */	
	public float getEta() {
		return eta;
	}

	/**
	 * Initializer getRho() - devolve o atributo rho
	 * @return rho 
	 */	
	public float getRho() {
		return rho;
	}
		
}
