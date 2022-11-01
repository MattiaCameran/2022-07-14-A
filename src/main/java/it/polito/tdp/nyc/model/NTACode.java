package it.polito.tdp.nyc.model;

public class NTACode {

	String nome;
	int condivisi;
	public NTACode(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getCondivisi() {
		return condivisi;
	}

	public void setCondivisi(int condivisi) {
		this.condivisi = condivisi;
	}

	public String toString() {
		return this.nome;
	}
}
