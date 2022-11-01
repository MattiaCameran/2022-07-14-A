package it.polito.tdp.nyc.model;

public class NTAFileCondivisi {

	//Mi creo questa classe per poter tenere traccia nella simulazione del numero di file condivisi nel singolo vertice del grafo.
	NTACode n;
	int fileCondivisi;
	public NTAFileCondivisi(NTACode n, int fileCondivisi) {
		super();
		this.n = n;
		this.fileCondivisi = fileCondivisi;
	}
	public NTACode getN() {
		return n;
	}
	public void setN(NTACode n) {
		this.n = n;
	}
	public int getFileCondivisi() {
		return fileCondivisi;
	}
	public void setFileCondivisi(int fileCondivisi) {
		this.fileCondivisi = fileCondivisi;
	}
	
	public String toString() {
		return "NTA "+this.n + " con "+this.fileCondivisi+" file condivisi.";
	}
}
