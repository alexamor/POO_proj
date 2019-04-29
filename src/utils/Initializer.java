package utils;

import java.util.ArrayList;


import graphHandler.Graph;
import ants.PheromonedEdge;

public class Initializer {

	ArrayList<PheromonedEdge> edges;
	
	float pLevel;
	float finalInst;
	int antColSize;
	int nbNode;
	int nestNode;
	
	public Initializer() {}
	
	public Initializer(ArrayList<PheromonedEdge> edges, float pLevel, float finalInst, int antColSize) {
		this.pLevel = pLevel;
		this.finalInst = finalInst;
		this.antColSize = antColSize;
		this.edges = edges;
	}
	
	public Graph CreateGraph() {	
		Graph g = new Graph(nbNode, edges);
		return g;
	}

	public String toString()
	{
		String output = "";
		output = "[pLevel]: " + this.pLevel + "\n[FinalInst]: " + this.finalInst + "\n[AntColSize]: " + this.antColSize + "\n\n";
		
		for(int i=0; i<edges.size(); i++) 
		{
			output += this.edges.get(i).toString() + "\n";
		}
		
		return output;
	}
	
	public int getNbNodes() {
		return nbNode;
	}
	
	public ArrayList<PheromonedEdge> getEdges(){
		return edges;
	}
	
	public float getPLevel() {
		return pLevel;
	}
	
	public float getFinalInst() {
		return finalInst;
	}

	public int getAntColSize() {
		return antColSize;
	}

	public int getNestNode() {
		return nestNode;
	}
	
	
}
