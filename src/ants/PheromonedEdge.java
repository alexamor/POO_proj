package ants;

import graphHandler.Edge;

/**
 * Classe PheromonedEdge: esta classe é uma subclasse da classe Edge (Graph Handler). Implementa edges com um atributo adicional, feromonas.
 * @author Alexandre Filipe, Sofia Salgueiro, José Rocha
 * @since 26-04-2019
 */
public class PheromonedEdge extends Edge{
	
	private float pheromoneLevel = 0;

	/**
	 * Default constructor - invoca o constructor da superclasse para inicializar o nó i, o nó j e o peso da aresta.
	 * As feromonas são inicializadas a 0.
	 * @param iNode - nó i 
	 * @param jNode - nó j
	 * @param weight - peso da aresta
	 */
	public PheromonedEdge(int iNode, int jNode, int weight) {
		super(iNode, jNode, weight);
	}
	
	/**
	 * Pheromone Level Getter - devolve o nível de feromonas da aresta
	 * @return nível de feromonas da aresta
	 */
	public float getPheromoneLevel() {
		return pheromoneLevel;
	}

	/**
	 * Adiciona o nível de feromonas dado como parâmetro à aresta.
	 * @param pheromoneLevel - nível de feromonas que se pretende adicionar
	 */
	public void increasePheromoneLevel(float pheromoneLevel) {
		this.pheromoneLevel += pheromoneLevel;
	}
	
	/**
	 * Diminui o nível de feromonas dado como parâmetro à aresta. O nível de feromonas é, no máximo, diminuído até 0.
	 * @param pheromoneLevel - nível de feromonas que se pretende diminuir
	 */
	public void decreasePheromoneLevel(float pheromoneLevel) {
		this.pheromoneLevel -= pheromoneLevel;
		
		if(this.pheromoneLevel < 0)
			this.pheromoneLevel = 0;
	}
}
