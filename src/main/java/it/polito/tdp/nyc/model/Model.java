package it.polito.tdp.nyc.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private Graph<NTACode, DefaultWeightedEdge> grafo;
	private NYCDao dao;
	
	private Map<String, NTACode> idMap;
	
	public Model() {
		
		this.dao = new NYCDao();
		this.idMap = new HashMap<String, NTACode>();
	}
	
	public List<Borough> getAllBoroughs(){
		
		List<Borough> temp = new ArrayList<Borough>(this.dao.getAllBoroughs());
		Collections.sort(temp);
		
		return temp;
	}
	
	public String creaGrafo(Borough b) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungere vertici

		
		List<NTACode> vertici = this.dao.getNTACodes(b);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		//Creo gli archi
		
		int peso = 0;
		for(NTACode n1: this.grafo.vertexSet()) {
			for(NTACode n2: this.grafo.vertexSet()) {
				if(!n1.equals(n2)) {
					peso = this.dao.getPeso(n1, n2);
					if(peso > 0) {
					Graphs.addEdgeWithVertices(this.grafo, n1, n2, peso);
					}
				}
			}
		}
		return "Grafo creato!\nNumero vertici " +this.grafo.vertexSet().size()+
		"\nNumero archi " + this.grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getArchiPesanti(){
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		double peso = 0;
		double media = 0;
		
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			
			peso += this.grafo.getEdgeWeight(e);
			
		}
		media = peso / this.grafo.edgeSet().size();
		
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			
			if(this.grafo.getEdgeWeight(e) > media) {
				NTACode n1 = this.grafo.getEdgeSource(e);
				NTACode n2 = this.grafo.getEdgeTarget(e);
				Adiacenza a = new Adiacenza(n1.getNome(), n2.getNome(), (int)this.grafo.getEdgeWeight(e));
				result.add(a);
			}
		}
		Collections.sort(result);
		return result;
	}
}
