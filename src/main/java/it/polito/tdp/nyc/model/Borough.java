package it.polito.tdp.nyc.model;

import java.util.LinkedList;
import java.util.List;

public class Borough implements Comparable<Borough>{

	private String nome;
	private List<NTACode> listaNTA;
	
	public Borough(String nome) {
		this.nome = nome;
		listaNTA = new LinkedList<NTACode>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}

	public List<NTACode> getListaNTA() {
		return listaNTA;
	}

	public void setListaNTA(List<NTACode> listaNTA) {
		this.listaNTA = listaNTA;
	}

	@Override
	public int compareTo(Borough o) {
		// TODO Auto-generated method stub
		return this.nome.compareTo(o.getNome());
	}
	
	
}
