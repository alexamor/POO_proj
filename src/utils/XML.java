package utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import program.Edge;

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
			
			init.AntColSize = Integer.parseInt(node.getAttributes().getNamedItem("antcolsize").getNodeValue());
			init.pLevel = Double.valueOf(node.getAttributes().getNamedItem("plevel").getNodeValue());
			init.FinalInst = Double.valueOf(node.getAttributes().getNamedItem("finalinst").getNodeValue());
						
			//get edges
			ArrayList<Edge> edges = new ArrayList<Edge>();		
			NodeList nodes = doc.getElementsByTagName("weight");
			
			for(int i=0; i< nodes.getLength(); i++) {
				
				Node nodeA = nodes.item(i);
				Node nodeB = nodeA.getParentNode();
								
				Edge e = new Edge(
					Integer.parseInt(nodeB.getAttributes().getNamedItem("nodeidx").getNodeValue()),
					Integer.parseInt(nodeA.getAttributes().getNamedItem("targetnode").getNodeValue()),
					Integer.parseInt(nodeA.getTextContent()),
					0
				);
				
				edges.add(e);
			}
			
			init.Edges = edges;
			
			return init;						
		}
		catch (Exception e) { 
			throw new Exception(e);
		} 
	}
	
}