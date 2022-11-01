package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event>{

	public enum EventType{
		FILE_CONDIVISO,
		FILE_RICONDIVISO,
		FILE_RIMOSSO;
	}
	
	private int T;				//Variabile che mi serve a tenere traccia del giorno in cui mi trovo.
	private EventType type;		//Variabile che mi dice il tipo di evento.
	private NTACode condiviso;	//Variabile che mi dice su che NTACode si è verificato l'evento.
	
	public Event(int t, EventType type, NTACode condiviso) {
		super();
		T = t;
		this.type = type;
		this.condiviso = condiviso;
	}

	public int getT() {
		return T;
	}

	public void setT(int t) {
		T = t;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public NTACode getCondiviso() {
		return condiviso;
	}

	public void setCondiviso(NTACode condiviso) {
		this.condiviso = condiviso;
	}

	@Override
	public int compareTo(Event o) {
		
		return this.T- o.getT();
	}
	
	
}
