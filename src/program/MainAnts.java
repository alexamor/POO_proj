package program;

import graphHandler.Graph;
import utils.Initializer;
import utils.XML;

public class MainAnts 
{

	static XML xml = new XML();
	//Atenção, ao mudar locais verificar se isto se mantém
	static String xml_path = System.getProperty("user.dir") + "\\\\src\\\\utils\\\\XML.xml";
	
	public static void main (String [] args)
	{
			
		try {
			
			Initializer init = XML.LoadXML(xml_path);
			
			Graph g = init.CreateGraph();
			
			g.createAdjacencyList();
			
			System.out.println(g.toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
