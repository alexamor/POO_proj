package utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import graphHandler.Edge;
import ants.PheromonedEdge;

public class XML
{
    public double TIME_END;
	public int ANT_COL_SIZE;
	public boolean P_LEVEL;	

	public static Initializer LoadXML(String xml_path) throws Exception 
	{

		String path = xml_path; 
		
		try {
			
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fact.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(new File(path));
 
			Initializer init = new Initializer();
			
			// get common data
			NodeList header = doc.getElementsByTagName("simulation");
			Node node = header.item(0);
			
			init.antColSize = Integer.parseInt(node.getAttributes().getNamedItem("antcolsize").getNodeValue());
			init.pLevel = Double.valueOf(node.getAttributes().getNamedItem("plevel").getNodeValue());
			init.finalInst = Double.valueOf(node.getAttributes().getNamedItem("finalinst").getNodeValue());
			
			//get nr of nodes and start node
			NodeList graphInfo = doc.getElementsByTagName("graph");
			Node nodeG = graphInfo.item(0);
			
			init.nbNode = Integer.parseInt(nodeG.getAttributes().getNamedItem("nbnodes").getNodeValue());
			init.startNode = Integer.parseInt(nodeG.getAttributes().getNamedItem("nestnode").getNodeValue()) - 1;
					
			//get edges
			ArrayList<PheromonedEdge> edges = new ArrayList<PheromonedEdge>();		
			NodeList nodes = doc.getElementsByTagName("weight");
			
			for(int i=0; i< nodes.getLength(); i++) {
				
				Node nodeA = nodes.item(i);
				Node nodeB = nodeA.getParentNode();
								
				PheromonedEdge e = new PheromonedEdge(
					Integer.parseInt(nodeB.getAttributes().getNamedItem("nodeidx").getNodeValue()) -1 ,
					Integer.parseInt(nodeA.getAttributes().getNamedItem("targetnode").getNodeValue()) -1,
					Integer.parseInt(nodeA.getTextContent())
				);
				
				edges.add(e);
			}
			
			init.edges = edges;
			
			return init;						
		}
		catch (Exception e) { 
			throw new Exception(e);
		} 
	}
	
}