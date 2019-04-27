package utils;

import java.util.ArrayList;

import graphHandler.Edge;
import graphHandler.Graph;

public class Initializer {

	ArrayList<Edge> Edges;
	
	Double pLevel;
	Double FinalInst;
	Integer AntColSize;
	int nbNode;
	int startNode;
	
	public Initializer() {}
	
	public Initializer(ArrayList<Edge> Edges, double pLevel, double FinalInst, int AntColSize) {
		this.pLevel = pLevel;
		this.FinalInst = FinalInst;
		this.AntColSize = AntColSize;
		this.Edges = Edges;
	}
	
	public Graph CreateGraph() {	
		Graph g = new Graph(nbNode);
		return g;
	}

	public String toString()
	{
		String output = "";
		output = "[pLevel]: " + this.pLevel + "\n[FinalInst]: " + this.FinalInst + "\n[AntColSize]: " + this.AntColSize + "\n\n";
		
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
