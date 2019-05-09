package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.InputSource;

import ants.PheromonedEdge;

/**
 * Classe XML : parse do doxumento XML. A partir do ficheiro disponilizado, ser�
 * instanciada a classe Initializer que posteriormente ser� utilizada para construir
 * o sistema de simula��o
 *  
 * @author Alexandre Filipe, Sofia Salgueiro, Jos� Rocha
 * @since 07-05-2019
 */

public class XML
{		
	
	/**
	 * Initializer LoadXML() - devolve um objeto do tipo Initializer com os atributos obtidos atrav�s do ficheiro XML.
	 * @param xml_path - ficheiro a ser lido
	 * @return Initializer
	 * @throws Exception - parser inválido
	 */
	public static Initializer LoadXML(String xml_path) throws Exception 
	{

		String path = xml_path; 
		Initializer init = new Initializer();
		
		try {
			
			//LOAD XML
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setValidating(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			
			builder.setErrorHandler(
			          new ErrorHandler() {
			            public void warning(SAXParseException e) throws SAXException {
			              System.out.println("WARNING : " + e.getMessage()); // do nothing
			            }

			            public void error(SAXParseException e) throws SAXException {
			              System.out.println("ERROR : " + e.getMessage());
			              throw e;
			            }

			            public void fatalError(SAXParseException e) throws SAXException {
			              System.out.println("FATAL : " + e.getMessage());
			              throw e;
			            }
			          }
					);
			
			
			
			
			
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
		catch (ParserConfigurationException pce) {
	      throw pce;
	    } 
	    catch (IOException io) {
	      throw io;
	    }
	    /*catch (SAXException se){
	      return false;*/
		catch (Exception e) { 
			throw new Exception("PARSER - GRAFO INVALIDO: " + e);
		} 
	}
	
	/**
	 * Initializer getAntColSize() - devolve o atributo antcolsize. Se o valor encontrado for negativo lan�a uma excep��o.
	 * @param node - no a desserializar
	 * @return antcolsize  - atributo antcolsize
	 * @throws Exception - antcolsize tem de ser um número positivo
	 */
	public static int getAntColSize(Node node) throws Exception 
	{
		int antcolsize = Integer.parseInt(getNodeValue("antcolsize", node));		
		if (antcolsize < 0) 
		{ 
			throw new Exception ("antcolsize must be >= 0"); 
		}		
		return antcolsize;
	}

	/**
	 * Initializer getpLevel() - devolve o atributo plevel.
	 * @param node - no a desserializar
	 * @return plevel - atributo plevel
	 */
	public static float getpLevel(Node node)
	{
		return Float.valueOf(getNodeValue("plevel", node));
	}
	
	/**
	 * Initializer getFinalInst() - devolve o atributo finalinst. Se o valor encontrado for negativo lan�a uma excep��o.
	 * @param node - no a desserializar
	 * @return finalinst - atributo finalinst
	 * @throws Exception - o instante final tem de ser um nº positivo
	 */	
	public static float getFinalInst(Node node) throws Exception
	{
		float finalinst = Float.valueOf(getNodeValue("finalinst", node));		
		if (finalinst <= 0)
		{
			throw new Exception("finalinst must be >= 0");
		}				
		return finalinst;
	}
	
	/**
	 * Initializer getnbNodes() - devolve o atributo nbnodes. 
	 * @param node - no a desserializar
	 * @return nbnodes - atributo nbnodes
	 */		
	public static int getnbNodes(Node node)
	{
		return Integer.parseInt(getNodeValue("nbnodes", node));
	}
	
	/**
	 * Initializer getNestNode() - devolve o atributo nestnode. 
	 * @param node - no a desserializar
	 * @return nestnode - atributo nestnode
	 */		
	public static int getNestNode(Node node)
	{
		return Integer.parseInt(getNodeValue("nestnode", node)) - 1;
	}
	
	/**
	 * Initializer getAlpha() - devolve o atributo alpha. 
	 * @param node - no a desserializar
	 * @return alpha - atributo alpha
	 */	
	public static float getAlpha(Node node)
	{
		return Float.valueOf(getNodeValue("alpha", node));
	}
	
	/**
	 * Initializer getBeta() - devolve o atributo beta. 
	 * @param node - no a desserializar
	 * @return beta - atributo beta
	 */	
	public static float getBeta(Node node)
	{
		return Float.valueOf(getNodeValue("beta", node));
	}
	
	/**
	 * Initializer getDelta() - devolve o atributo delta. 
	 * @param node - no a desserializar
	 * @return delta - atributo delta
	 */	
	public static float getDelta(Node node)
	{
		return Float.valueOf(getNodeValue("delta", node));
	}
	
	/**
	 * Initializer getRHO() - devolve o atributo rho. 
	 * @param node - n� a desserializar
	 * @return rho - atributo rho
	 */		
	public static float getRHO(Node node)
	{
		return Float.valueOf(getNodeValue("rho", node));
	}
	
	/**
	 * Initializer getETA() - devolve o atributo eta. 
	 * @param node - no a desserializar
	 * @return eta - atributo eta
	 */		
	public static float getETA(Node node)
	{
		return Float.valueOf(getNodeValue("eta", node));
	}
	
	/**
	 * Initializer getNodeIDX() - devolve o atributo nodeidx. Se o valor encontrado for menor que 1 lan�a uma excep��o.
	 * @param node - no a desserializar
	 * @return nodeidx - atributo nodeidx
	 * @exception Exception - o nó tem de ser positivo
	 */		
	public static int getNodeIDX(Node node) throws Exception
	{
		int nodeidx = Integer.parseInt(getNodeValue("nodeidx", node));
		if(nodeidx < 1)
		{
			throw new Exception("node must be > 0");
		}		
		return nodeidx - 1;
	}
	
	/**
	 * Initializer getTargetNode() - devolve o atributo targetnode. Se o valor encontrado for menor que 1 lan�a uma excep��o.
	 * @param node - no a desserializar
	 * @return targetnode - atributo targetnode
	 * @throws Exception - o nó destino tem de ser positivo
	 */		
	public static int getTargetNode(Node node) throws Exception
	{
		int targetnode = Integer.parseInt(getNodeValue("targetnode", node));
		if(targetnode < 1)
		{
			throw new Exception("node must be > 0");
		}		
		return targetnode - 1;
	}
	
	/**
	 * Initializer getEdgeWeight() - devolve o atributo weight. 
	 * @param node - no a desserializar
	 * @return weight - atributo weight
	 */		
	public static int getEdgeWeight(Node node)
	{
		return Integer.parseInt(node.getTextContent());
	}
	
	/**
	 * Initializer getNodeList() - devolve uma lista de nos. 
	 * @param TagName - no pai da lista
	 * @param doc - documento XML base
	 * @return NodeList - lista dos n�s encontrados
	 */		
	public static NodeList getNodeList(String TagName, org.w3c.dom.Document doc)
	{
		return doc.getElementsByTagName(TagName);
	}
	
	/**
	 * Initializer getNodeValue() - devolve o valor de um no
	 * @param Name - atributo a desserializar
	 * @param node - no a desserializar
	 * @return String - valor do no
	 */		
	public static String getNodeValue(String Name, Node node)
	{
		return (node.getAttributes().getNamedItem(Name).getNodeValue());
	}
	
}