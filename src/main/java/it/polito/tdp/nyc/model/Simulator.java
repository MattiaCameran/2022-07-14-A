package it.polito.tdp.nyc.model;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event.EventType;

public class Simulator {

	//Parametri in ingresso
	private int numGiorni;						//Durata simulazione
	private Graph<NTACode, DefaultWeightedEdge> grafo;	//Grafo. Mi serve sia per avere la lista di vertici e archi sia per conoscere il peso degli archi incidenti nell'NTA di riferimento.
	private List<NTACode> listaNTA;						//listaNTA per conoscere adiacenti.
	private int d;										//Durata esistenza file condiviso in NTA.
	private double p;									//Probabilità di file condiviso.
	
	//Dati in uscita
	private List<NTACode> listaNTACondivisi;
	
	//Stato del mondo
	private List<NTACode> NTACondivisiParziale;	//Questa informazione mi serve a capire quali NTA hanno già avuto file condivisi in modo da sapere che in questi l'evento sarà diverso.
	
	//Coda degli eventi
	PriorityQueue<Event> queue;
	
	public Simulator(Graph<NTACode, DefaultWeightedEdge> grafo, List<NTACode> listaNTA) {
		super();
		//Inizializzo nel costruttore le variabili fisse, che non subiranno variazione.
		this.grafo = grafo;
		this.listaNTA = listaNTA;
		this.numGiorni = 100;	//il numero di giorni sono obbligato dalle specifiche a metterlo a 100.
	}
	
	public void init(int d, double p) {
		
		this.d = d;
		this.p = p;			//Durata file condiviso e probabilità che il file sia condiviso. Questi sono parametri in ingresso selezionati dall'utente.
		
		
		//Inizializzo gli output
		listaNTACondivisi = new LinkedList<NTACode>();
		for(NTACode n: this.grafo.vertexSet()) {
			n.setCondivisi(0);		//Riempio la lista di NTA con i vertici del grafo dicendo che tutti hanno 0 file condivisi
		}
		
		//Inizializzo stato del mondo
		NTACondivisiParziale = new LinkedList<NTACode>();
		for(NTACode n: this.grafo.vertexSet()) {
			n.setCondivisi(0);		//Riempio la lista di NTA con i vertici del grafo dicendo che tutti hanno 0 file condivisi
		}
		
		
		//Caricamento iniziale della coda.
		//Seleziono casualmente un NTACode.
		int indiceCasuale = (int)(Math.random() * NTACondivisiParziale.size());
		queue.add(new Event(0, EventType.FILE_CONDIVISO, NTACondivisiParziale.get(indiceCasuale)));
		
	}
	
	public void run() {
		
		while(!queue.isEmpty()) {	//Se la coda non è vuota.
			Event e = this.queue.poll();	//Estrai.
			processEvent(e);
			
		}
}

	private void processEvent(Event e) {
		
		int time = e.getT();
		EventType type = e.getType();
		NTACode nta = e.getCondiviso();
		
		switch(type) {
		
		case FILE_CONDIVISO:
			
			if(time < 100) {
			if(Math.random() > 1-p) {			//Se il valore estratto randomico è nel range di probabilità che l'utente ha selezionato, faccio avvenire l'evento.
												//Nota: se l'utente seleziona 0.8 vuol dire che l'80% dei casi deve avvenire l'evento, quindi tra 0 e 0.99 tutti i valori maggiori di 0.2 funzionano.
				
			nta.setCondivisi(nta.getCondivisi()+1);
			
			this.queue.add(new Event(time+1, EventType.FILE_RICONDIVISO, nta));
			this.queue.add(new Event(time+d, EventType.FILE_RIMOSSO, nta));		//Il file condiviso si rimuove dopo d giorni.
			}
			}
			break;
		
		case FILE_RICONDIVISO:
			int max = 0;
			for(NTACode n: this.NTACondivisiParziale) {
				if(n.getCondivisi() == 0) {
				 int peso = (int)this.grafo.getEdgeWeight(this.grafo.getEdge(nta, n));
				 if(peso > max) {
					 max = peso;
				 }
				}
			}
			for(NTACode n: this.NTACondivisiParziale) {
				if((int)this.grafo.getEdgeWeight(this.grafo.getEdge(n, nta)) == max) {
					this.queue.add(new Event(time + d/2, EventType.FILE_CONDIVISO, n));
				}
			}
			break;
			
		case FILE_RIMOSSO:
			nta.setCondivisi(nta.getCondivisi()-1);
			break;
		}
		
	}
}