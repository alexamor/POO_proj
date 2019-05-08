package ants;

import graphHandler.Edge;

/**
 * Classe PheromonedEdge: esta classe é uma subclasse da classe Edge (Graph Handler). Implementa edges com um atributo adicional, feromonas.
 * @author Alexandre Filipe, Sofia Salgueiro, José Rocha
 * @since 26-04-2019
 */
public class PheromonedEdge extends Edge{
	
	/**
	 * pheromoneLevel - Nível de feromonas da aresta.
	 */
	private float pheromoneLevel = 0;

	/**
	 * Default constructor - Invoca o constructor da superclasse para inicializar o nó i, o nó j e o peso da aresta.
	 * As feromonas são inicializadas a 0.
	 * @param iNode - Nó i 
	 * @param jNode - Nó j
	 * @param weight - Peso da aresta
	 */
	public PheromonedEdge(int iNode, int jNode, int weight) {
		super(iNode, jNode, weight);
	}
	
	/**
	 * Pheromone Level Getter - Devolve o nível de feromonas da aresta.
	 * @return Nível de feromonas da aresta
	 */
	public float getPheromoneLevel() {
		return pheromoneLevel;
	}

	/**
	 * Adiciona o nível de feromonas dado como parâmetro à aresta.
	 * @param pheromoneLevel - Nível de feromonas que se pretende adicionar
	 */
	public void increasePheromoneLevel(float pheromoneLevel) {
		this.pheromoneLevel += pheromoneLevel;
	}
	
	/**
	 * Diminui o nível de feromonas dado como parâmetro à aresta. O nível de feromonas é, no máximo, diminuído até 0.
	 * @param pheromoneLevel - Nível de feromonas que se pretende diminuir
	 */
	public void decreasePheromoneLevel(float pheromoneLevel) {
		this.pheromoneLevel -= pheromoneLevel;
		
		if(this.pheromoneLevel < 0)
			this.pheromoneLevel = 0;
	}
}
