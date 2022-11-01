package it.polito.tdp.nyc.model;

public class TestModel {

	public static void main(String[] args) {

		Borough b = new Borough("BK");
		Model m = new Model();
		System.out.println(m.creaGrafo(b));

	}

}
