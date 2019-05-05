package utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ants.PheromonedEdge;

public class XML
{
	
		
	public static Initializer LoadXML(String xml_path) throws Exception 
	{

		String path = xml_path; 
		Initializer init = new Initializer();
		
		try {
			
			//LOAD XML
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fact.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(new File(path));
 			
			// GET SIMULATION DATA
			NodeList header = getNodeList("simulation", doc);
			Node node = header.item(0);
			init.antColSize = getAntColSize(node);
			init.pLevel = getpLevel(node);
			init.finalInst = getFinalInst(node);
			
			//GET GRAPH NODES
			NodeList graphInfo = getNodeList("graph", doc); 
			Node nodeG = graphInfo.item(0);
			init.nbNode = getnbNodes(nodeG);
			init.nestNode = getNestNode(nodeG); 
					
			//GET EDGES
			ArrayList<PheromonedEdge> edges = new ArrayList<PheromonedEdge>();		
			NodeList nodes = getNodeList("weight", doc);
			
			for(int i=0; i< nodes.getLength(); i++) {
				
				Node nodeA = nodes.item(i);
				Node nodeB = nodeA.getParentNode();
								
				PheromonedEdge e = new PheromonedEdge(
					getNodeIDX(nodeB),
					getTargetNode(nodeA),
					getEdgeWeight(nodeA)
				);
				edges.add(e);
			}
			
			init.edges = edges;
			
			
			//GET EVENTOS			
			//MOVE
			NodeList moveInfo = getNodeList("move", doc);
			Node nodeM = moveInfo.item(0);
			init.alpha = getAlpha(nodeM); 	
			init.beta = getBeta(nodeM); 
			init.delta = getDelta(nodeM);
			
			//EVAPORATION
			NodeList evapInfo = getNodeList("evaporation", doc);
			Node nodeE = evapInfo.item(0);
			init.eta = getETA(nodeE);
			init.rho = getRHO(nodeE);
			
			return init;						
		}
		catch (Exception e) { 
			throw new Exception("PARSER - GRAFO INVÁLIDO: " + e);
		} 
	}
	
	public static int getAntColSize(Node node) throws Exception 
	{
		int antcolsize = Integer.parseInt(getNodeValue("antcolsize", node));		
		if (antcolsize < 0) 
		{ 
			throw new Exception ("antcolsize must be >= 0"); 
		}		
		return antcolsize;
	}
	
	public static float getpLevel(Node node)
	{
		return Float.valueOf(getNodeValue("plevel", node));
	}
	
	public static float getFinalInst(Node node) throws Exception
	{
		float finalinst = Float.valueOf(getNodeValue("finalinst", node));		
		if (finalinst <= 0)
		{
			throw new Exception("finalinst must be >= 0");
		}				
		return finalinst;
	}
	
	public static int getnbNodes(Node node)
	{
		return Integer.parseInt(getNodeValue("nbnodes", node));
	}
	
	public static int getNestNode(Node node)
	{
		return Integer.parseInt(getNodeValue("nestnode", node)) - 1;
	}
	
	public static float getAlpha(Node node)
	{
		return Float.valueOf(getNodeValue("alpha", node));
	}
	
	public static float getBeta(Node node)
	{
		return Float.valueOf(getNodeValue("beta", node));
	}
	
	public static float getDelta(Node node)
	{
		return Float.valueOf(getNodeValue("delta", node));
	}
	
	public static float getRHO(Node node)
	{
		return Float.valueOf(getNodeValue("rho", node));
	}
	
	public static float getETA(Node node)
	{
		return Float.valueOf(getNodeValue("eta", node));
	}
	
	public static int getNodeIDX(Node node) throws Exception
	{
		int nodeidx = Integer.parseInt(getNodeValue("nodeidx", node));
		if(nodeidx < 1)
		{
			throw new Exception("node must be > 0");
		}		
		return nodeidx - 1;
	}
	
	public static int getTargetNode(Node node) throws Exception
	{
		int targetnode = Integer.parseInt(getNodeValue("targetnode", node));
		if(targetnode < 1)
		{
			throw new Exception("node must be > 0");
		}		
		return targetnode - 1;
	}
	
	public static int getEdgeWeight(Node node)
	{
		return Integer.parseInt(node.getTextContent());
	}
	
	public static NodeList getNodeList(String TagName, org.w3c.dom.Document doc)
	{
		return doc.getElementsByTagName(TagName);
	}
	
	public static String getNodeValue(String Name, Node node)
	{
		return (node.getAttributes().getNamedItem(Name).getNodeValue());
	}
	
}