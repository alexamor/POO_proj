package utils;

import java.util.ArrayList;

import graphHandler.Edge;
import graphHandler.Graph;

public class Initializer {

	ArrayList<Edge> Edges;
	
	Double pLevel;
	Double finalInst;
	Integer antColSize;
	int nbNode;
	int startNode;
	
	public Initializer() {}
	
	public Initializer(ArrayList<Edge> Edges, double pLevel, double finalInst, int antColSize) {
		this.pLevel = pLevel;
		this.finalInst = finalInst;
		this.antColSize = antColSize;
		this.Edges = Edges;
	}
	
	public Graph CreateGraph() {	
		Graph g = new Graph(nbNode, Edges);
		return g;
	}

	public String toString()
	{
		String output = "";
		output = "[pLevel]: " + this.pLevel + "\n[FinalInst]: " + this.finalInst + "\n[AntColSize]: " + this.antColSize + "\n\n";
		
		for(int i=0; i<Edges.size(); i++) 
		{
			output += this.Edges.get(i).toString() + "\n";
		}
		
		return output;
	}
	
	public int getNbNodes() {
		return nbNode;
	}
	
	public ArrayList<Edge> getEdges(){
		return Edges;
	}
}
