package ants;

import eventHandler.Event;
import simulator.AntSimulator;

/**
 * Classe Observation: implementa o evento de observação da simulação. Esta classe implementa a classe abstracta Event. 
 * Imprime no terminal o nº da observação, o instante em que acontece, o nº de movimentos realizados pelas formigas, o nº
 * de evaporações ocorridos e, caso exista, o ciclo Hamiltoniano com menor peso encontrado.
 * @author Alexandre Filipe, Sofia Salgueiro, José Rocha
 * @since 26-04-2019
 */
public class Observation extends Event{
	
	/**
	 * nr - Atributo estático Variável da classe Observation que contém o número de eventos Observation realizados.
	 */
	private static int nr = 1;

	
	/**
	 * Default constructor - Invoca o constructor da superclasse para inicializar o timestamp, instante em que o evento ocorre.
	 * @param timestamp - Instante em que o evento ocorre
	 */
	public Observation(double timestamp) {
		super(timestamp);
	}

	
	/**
	 * Override da função dada pela classe abstracta Event. Esta função trata de todo o evento de observação.
	 * Imprime no terminal o nº da observação, o instante em que acontece, o nº de movimentos realizados pelas formigas, o nº
	 * de evaporações ocorridos e, caso exista, o ciclo Hamiltoniano com menor peso encontrado.
	 */
	@Override
	public void simulateEvent() {
		
		// Imprimir informação
		System.out.println("Observation " + nr + ":");
		System.out.println("                Present instant:               " + timestamp);
		System.out.println("                Number of move events:         " + AntMove.getNr());
		System.out.println("                Number of evaporation events:  " + PheromoneEvap.getNr());
		
		// Só imprime o ciclo caso algo já tenha sido determinado
		if(AntSimulator.getBestWeight() != Integer.MAX_VALUE)
			System.out.println("                Hamiltonian cycle:             " + AntSimulator.getBestPath());
		
		if(nr < 20) {
			// Colocar na pilha a próxima observação
			AntSimulator.getPec().addEvent(new Observation(timestamp + AntSimulator.getFinalInst()/20));
		}
		
		// Aumenta o nº de eventos de observação realizados
		nr++;
	}
		
	
	
}
