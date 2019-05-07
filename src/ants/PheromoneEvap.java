package ants;

import eventHandler.Event;
import eventHandler.PEC;
import simulator.AntSimulator;

/**
 * Classe PheromoneEvap: implementa o evento de evaporação das arestas com feromonas. Esta classe implementa a classe abstracta Event. 
 * Diminui o nível de feromonas da aresta e, caso este permaneça acima de 0, cria um novo evento de evaporação, para a mesma aresta,
 * e adiciona-lo ao PEC.
 * @author Alexandre Filipe, Sofia Salgueiro, José Rocha
 * @since 26-04-2019
 */
public class PheromoneEvap extends Event{
	
	private PheromonedEdge edge;
	private static int nr = 0;
	
	/**
	 * Default constructor - invoca o constructor da superclasse para inicializar o timestamp, instante em que o evento ocorre.
	 * Depois, na subclasse, o constructor associa a aresta, dada como parâmetro, ao evento
	 * @param timestamp - instante em que o evento ocorre
	 * @param edge - aresta associada ao evento
	 */
	public PheromoneEvap(double timestamp, PheromonedEdge edge) {
		super(timestamp);
		this.edge = edge;
	}
	
	/**
	 * Override da função dada pela classe abstracta Event. Esta função trata de todo o evento de evaporação do nível de feromonas da aresta.
	 * Diminui o nível de feromonas da aresta e, caso este permaneça acima de 0, cria um novo evento de evaporação, para a mesma aresta,
	 * e adiciona-lo ao PEC.
	 */
	@Override
	public void simulateEvent() {
		
		edge.decreasePheromoneLevel(AntSimulator.getRho());
		
		//Caso o nível de feromonas continue positivo agendar a próxima evaporação
		double newTimestamp = getNewTimestamp(timestamp);
		
		if(edge.getPheromoneLevel() > 0 && newTimestamp < AntSimulator.getFinalInst())
			AntSimulator.getPec().addEvent(new PheromoneEvap(newTimestamp, edge));
		
		// incrementa o nº de eventos de evaporação realizados
		nr++;
				
	}
	
	/**
	 * Nr Static Getter - retorna o número de eventos PheromoneEvap realizados, dado pelo atributo estático da classe, nr.
	 * @return nº de eventos PheromoneEvap realizados
	 */
	public static int getNr() {
		return nr;
	}
	
	/**
	 * Calcula o instante em que ocorrerá a próxima evaporação.
	 * O instante é calculado através de uma distribuição exponencial cuja média é dada pelo parâmetro Eta do XML.
	 * @param timestamp - instante do evento atual
	 * @return instante em que ocorrerá o próximo evento
	 */
	public static double getNewTimestamp(double timestamp) {
		float mean = AntSimulator.getEta();
		double random = new java.util.Random().nextDouble();
		double newTimestamp = timestamp + Math.log(1 - random) * (-mean);
		return newTimestamp;
	}
}
