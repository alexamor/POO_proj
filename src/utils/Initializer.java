package utils;

import java.util.ArrayList;
import graphHandler.Graph;
import ants.PheromonedEdge;
import java.lang.reflect.Field;

public class Initializer {

	ArrayList<PheromonedEdge> edges;
	
	float pLevel;
	float finalInst;
	int antColSize;
	int nbNode;
	int nestNode;
	float alpha, beta, delta, eta, rho;
	
	
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
	
	public int getNbNode() {
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

	public float getpLevel() {
		return pLevel;
	}

	public float getAlpha() {
		return alpha;
	}

	public float getBeta() {
		return beta;
	}

	public float getDelta() {
		return delta;
	}

	public float getEta() {
		return eta;
	}

	public float getRho() {
		return rho;
	}
	
	
	
}
