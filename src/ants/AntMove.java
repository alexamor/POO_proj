package ants;

import eventHandler.Event;

public class AntMove extends Event {

	public static final int alfa;
	public static final int beta;
	public static final int delta;
	static{
		alfa = 1; //a mudar depois
		beta = 1;
		delta = 1;
	
	}
	
	// Cada evento de movimento de formiga está associado a uma formiga
	private Ant ant;

	AntMove(Ant ant, float timestamp) {
		super(timestamp);
		this.ant = ant;
	}

	Ant getAnt() {
		return ant;
	}

	
	
	

	
}
