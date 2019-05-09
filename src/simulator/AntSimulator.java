package simulator;

import graphHandler.Graph;
import utils.Initializer;
import utils.XML;

import java.util.Arrays;

import ants.*;
import eventHandler.Event;
import eventHandler.PEC;


public class AntSimulator implements ISimulator{
	/**ficheiro XML a ser lido*/
	static XML xml = new XML();
	/**endereço completo do ficheiro XML*/
	static String xml_path = System.getProperty("user.dir") + "/"/* + "\\\\src\\\\utils\\\\XML.xml"*/;
	/**nível de feromonas*/
	static float pLevel;
	/**duração da simulação*/
	static float finalInst;
	/**quantidade de formigas*/
	static int antColSize;
	/**quantidade de nós no grafo*/
	static int nbNode;
	/**nó inicial*/
	static int nestNode;
	/**parametros de movimento e evaporação*/
	static float alpha, beta, delta, eta, rho;
	/**peso do melhor ciclo*/
	static int bestWeight;
	/**melhor ciclo*/
	static int[] bestPath;
	/**grafo*/
	static Graph g;
	/**pilha ordenada de eventos*/
	static PEC pec = new PEC();
	
	public static void main (String [] args)
	{
		AntSimulator antSim = new AntSimulator();
		
		//Adicionar à diretoria o xml a ser lido
		xml_path += args[0];
		
		antSim.beginSimulation(xml_path);	
		
	}

	
	/**
	 * Executa a simulação da colónia das formigas nos grafo por completo
	 * @param xmlFile - ficheiro XML de dados da simulação a ser lido
	 */
	@Override
	public void beginSimulation(String xmlFile) {
		// TODO Auto-generated method stub
		try {
			
			//Leitura do XML
			Initializer init = XML.LoadXML(xml_path);
			
			getParameters(init);
			
			g = init.CreateGraph();
			g.createAdjacencyList();

			//Criação da colónia de formigas
			Ant[] ants	= new Ant[AntSimulator.antColSize];
			for(int i = 0; i < ants.length; i++) { 
				ants[i] = new Ant();
				pec.addEvent(new AntMove(ants[i], 0));
			}
			//Adicionar os eventos de movimento da formiga
			pec.addEvent(new Observation(finalInst/20));
			
			bestWeight = Integer.MAX_VALUE;
			bestPath = new int[AntSimulator.nbNode];
			
			Event curEvent;
			
			
			//Só termina o programa quando não existirem mais eventos na pilha
			while(!pec.isEmpty()) {
				curEvent = (Event) pec.removeEvent();
				curEvent.simulateEvent();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return pilha de eventos
	 */
	public static PEC getPec() {
		return pec;
	}

	/**
	 * 
	 * @return grafo
	 */
	public static Graph getG() {
		return g;
	}

	/**
	 * Copia para o antSimulator todos os dados necessários
	 * @param init - classe inicializadora que contém os dados lidos do fichero XML 
	 */
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

	/**
	 * 
	 * @return ficheiro XML selecionado
	 */
	public static XML getXml() {
		return xml;
	}
	
	/**
	 * 
	 * @return endereço completo do ficheiro XML selecionado
	 */
	public static String getXml_path() {
		return xml_path;
	}

	/**
	 * 
	 * @return nível de feromonas
	 */
	public static float getpLevel() {
		return pLevel;
	}

	/**
	 * 
	 * @return duração total da simulação
	 */
	public static float getFinalInst() {
		return finalInst;
	}

	/**
	 * 
	 * @return número de formigas a percorrer o grafo
	 */
	public static int getAntColSize() {
		return antColSize;
	}

	/**
	 * 
	 * @return número de nós do grafo
	 */
	public static int getNbNode() {
		return nbNode;
	}
	
	/**
	 * 
	 * @return nó inicial
	 */
	public static int getNestNode() {
		return nestNode;
	}
	
	/**
	 * 
	 * @return variável de movimento alfa
	 */
	public static float getAlpha() {
		return alpha;
	}

	/**
	 * 
	 * @return variável de movimento beta
	 */
	public static float getBeta() {
		return beta;
	}
	
	/**
	 * 
	 * @return variável de movimento delta
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * 
	 * @return variável de evaporação eta
	 */
	public static float getEta() {
		return eta;
	}

	/**
	 * 
	 * @return variável de evaporação rho
	 */
	public static float getRho() {
		return rho;
	}

	/**
	 * 
	 * @return peso do atual melhor ciclo
	 */
	public static int getBestWeight() {
		return bestWeight;
	}
	
	/**
	 * 
	 * @param bestPath - novo melhor ciclo hamiltoniano
	 */
	public static void setBestPath(int[] bestPath) {
		AntSimulator.bestPath = Arrays.copyOf(bestPath, bestPath.length);
	}
	
	/**
	 * 
	 * @param bestWeight - novo melhor peso
	 */
	public static void setBestWeight(int bestWeight) {
		AntSimulator.bestWeight = bestWeight;
	}

	@Override
	public String toString() {
		return "AntSimulator [toString()=" + alpha + " " + beta + " " + delta + " " + eta + " " + rho + "]";
	}
	
	
	/**
	 * 
	 * @return string com o melhor ciclo formatada
	 */
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
