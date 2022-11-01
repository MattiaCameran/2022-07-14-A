package it.polito.tdp.nyc.model;

public class Adiacenza implements Comparable<Adiacenza>{

	String nome1;
	String nome2;
	int peso;
	public Adiacenza(String n1, String n2, int peso) {
		super();
		this.nome1 = n1;
		this.nome2 = n2;
		this.peso = peso;
;
	}
	public String getNome1() {
		return nome1;
	}
	public String getNome2() {
		return nome2;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return nome1 + ", " + nome2 + ", peso=" + peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		
		return -(this.peso-o.getPeso());
	}
	
}
