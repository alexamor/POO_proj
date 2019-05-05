package ants;

import graphHandler.Edge;

public class PheromonedEdge extends Edge{
	
	private float pheromoneLevel = 0;

	public PheromonedEdge(int iNode, int jNode, int weight) {
		super(iNode, jNode, weight);
	}
	
	public float getPheromoneLevel() {
		return pheromoneLevel;
	}

	public void increasePheromoneLevel(float pheromoneLevel) {
		this.pheromoneLevel += pheromoneLevel;
	}
	
	public void decreasePheromoneLevel(float pheromoneLevel) {
		this.pheromoneLevel -= pheromoneLevel;
		
		if(this.pheromoneLevel < 0)
			this.pheromoneLevel = 0;
	}
}
