package utils;

import java.util.ArrayList;


import graphHandler.Edge;
import graphHandler.Graph;
import ants.PheromonedEdge;

public class Initializer {

	ArrayList<PheromonedEdge> edges;
	
	Double pLevel;
	Double finalInst;
	Integer antColSize;
	int nbNode;
	int startNode;
	
	public Initializer() {}
	
	public Initializer(ArrayList<PheromonedEdge> edges, double pLevel, double finalInst, int antColSize) {
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
}
