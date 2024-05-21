package com.example.motormaniacs.Model;

public class Premio {
	private int id;
	private String nombre;
	private Piloto piloto = new Piloto();
	private Equipo equipo = new Equipo();
	private String temporada;
	private int cantidad;
	
	public Premio (int id, String nombre, Piloto piloto, Equipo equipo, String temporada) {
		this.id = id;
		this.nombre = nombre;
		this.piloto = piloto;
		this.equipo = equipo;
		this.temporada = temporada;
	}
	public Premio (String nombre, Piloto piloto) {
		this.id = 0;
		this.nombre = nombre;
		this.piloto = piloto;
	}

	public Premio(){}

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

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
