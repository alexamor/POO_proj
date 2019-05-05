package simulator;

import graphHandler.Graph;
import utils.Initializer;
import utils.XML;

import java.util.Arrays;

import ants.*;
import eventHandler.Event;
import eventHandler.PEC;


public class AntSimulator implements ISimulator{

	static XML xml = new XML();
	//Atenção, ao mudar locais verificar se isto se mantém
	static String xml_path = System.getProperty("user.dir") + "\\\\"/* + "\\\\src\\\\utils\\\\XML.xml"*/;
	static float pLevel;
	static float finalInst;
	static int antColSize;
	static int nbNode;
	static int nestNode;
	static float alpha, beta, delta, eta, rho;
	static int bestWeight;
	static int[] bestPath;
	static Graph g;
	static PEC pec = new PEC();
	
	public static void main (String [] args)
	{
		AntSimulator antSim = new AntSimulator();
		
		xml_path += args[0];
		
		antSim.beginSimulation(xml_path);	
		
	}

	@Override
	public void beginSimulation(String xmlFile) {
		// TODO Auto-generated method stub
		try {
			
			Initializer init = XML.LoadXML(xml_path);
			
			getParameters(init);
			
			g = init.CreateGraph();
			g.createAdjacencyList();
			
			//System.out.println(this.toString());
			
			Ant[] ants	= new Ant[AntSimulator.antColSize];
			for(int i = 0; i < ants.length; i++) { 
				ants[i] = new Ant();
				pec.addEvent(new AntMove(ants[i], 0));
			}
			pec.addEvent(new Observation(finalInst/20));
			
			bestWeight = Integer.MAX_VALUE;
			bestPath = new int[AntSimulator.nbNode];
			
			Event curEvent;
			
			while(!pec.isEmpty()) {
				curEvent = (Event) pec.removeEvent();
				curEvent.simulateEvent();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static PEC getPec() {
		return pec;
	}

	public static Graph getG() {
		return g;
	}

	public void getParameters(Initializer init) {
		pLevel = init.getPLevel();
		finalInst = init.getFinalInst();
		antColSize = init.getAntColSize();
		nbNode = init.getNbNode();
		nestNode = init.getNestNode();
		alpha = init.getAlpha();
		beta = init.getBeta();
		delta = init.getDelta();
		eta = init.getEta();
		rho = init.getRho();
	}

	public static XML getXml() {
		return xml;
	}

	public static String getXml_path() {
		return xml_path;
	}

	public static float getpLevel() {
		return pLevel;
	}

	public static float getFinalInst() {
		return finalInst;
	}

	public static int getAntColSize() {
		return antColSize;
	}

	public static int getNbNode() {
		return nbNode;
	}

	public static int getNestNode() {
		return nestNode;
	}

	public static float getAlpha() {
		return alpha;
	}

	public static float getBeta() {
		return beta;
	}

	public static float getDelta() {
		return delta;
	}

	public static float getEta() {
		return eta;
	}

	public static float getRho() {
		return rho;
	}

	public static int getBestWeight() {
		return bestWeight;
	}

	public static void setBestPath(int[] bestPath) {
		AntSimulator.bestPath = Arrays.copyOf(bestPath, bestPath.length);
	}
	
	
	public static void setBestWeight(int bestWeight) {
		AntSimulator.bestWeight = bestWeight;
	}

	@Override
	public String toString() {
		return "AntSimulator [toString()=" + alpha + " " + beta + " " + delta + " " + eta + " " + rho + "]";
	}
	
	public static String getBestPath() {
		int aux = AntSimulator.nestNode;
		
		String s = "{";
		
		s += (aux + 1);
		s += ",";
		aux = AntSimulator.bestPath[aux];
		
		
		for(int i = 0; i< AntSimulator.bestPath.length - 2; i++) {
			
			s += (aux +1);
			s += ",";
			
			aux = AntSimulator.bestPath[aux];
		}
		
		s += (aux + 1);
		s += "}";
		
		return s;
	}

}
