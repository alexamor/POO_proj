package simulator;

import graphHandler.Graph;
import utils.Initializer;
import utils.XML;

public class AntSimulator implements ISimulator{

	static XML xml = new XML();
	//Atenção, ao mudar locais verificar se isto se mantém
	static String xml_path = System.getProperty("user.dir") + "\\\\src\\\\utils\\\\XML.xml";
	static float pLevel;
	static float finalInst;
	static int antColSize;
	static int nbNode;
	static int nestNode;
	
	public static void main (String [] args)
	{
		AntSimulator antSim = new AntSimulator();	
		
		antSim.beginSimulation(xml_path);	
		
	}

	@Override
	public void beginSimulation(String xmlFile) {
		// TODO Auto-generated method stub
		try {
			
			Initializer init = XML.LoadXML(xml_path);
			
			getParameters(init);
			
			Graph g = init.CreateGraph();
			
			g.createAdjacencyList();
			
			System.out.println(g.toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void getParameters(Initializer init) {
		pLevel = init.getPLevel();
		finalInst = init.getFinalInst();
		antColSize = init.getAntColSize();
		nbNode = init.getNbNodes();
		nestNode = init.getNestNode();
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

}
