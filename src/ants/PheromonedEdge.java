package ants;

import graphHandler.Edge;;

public class PheromonedEdge extends Edge{
	
	private float pheromoneLevel = 0;

	public PheromonedEdge(int iNode, int jNode, int weight) {
		super(iNode, jNode, weight);
	}
	
	public float getPherLevel()
    {
        return this.getPheromoneLevel();
    }

	public float getPheromoneLevel() {
		return pheromoneLevel;
	}

	public void increasePheromoneLevel(float pheromoneLevel) {
		this.pheromoneLevel += pheromoneLevel;
	}
}
