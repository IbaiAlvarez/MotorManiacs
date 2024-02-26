package com.example.motormaniacs.Model;

public class Premio {
	private int id;
	private String nombre;
	private Piloto piloto = new Piloto();
	
	public Premio (int id, String nombre, Piloto piloto) {
		this.id = id;
		this.nombre = nombre;
		this.piloto = piloto;
	}
	public Premio (String nombre, Piloto piloto) {
		this.id = 0;
		this.nombre = nombre;
		this.piloto = piloto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Piloto getPilotos() {
		return piloto;
	}

	public void setPilotos(Piloto pilotos) {
		this.piloto = pilotos;
	}
	
	@Override
	public String toString() {
		return nombre + " /"+ piloto ;
	}
}
