package com.example.motormaniacs.Model;

import java.util.ArrayList;

public class Carrera {
	private int id;
	private String nombre;
	private ArrayList<Piloto>  Posicion_pilotos =new ArrayList<Piloto>();
	
	
	
	public Carrera(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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
	public ArrayList<Piloto> getPosicion_pilotos() {
		return Posicion_pilotos;
	}
	public void setPosicion_pilotos(ArrayList<Piloto> posicion_pilotos) {
		Posicion_pilotos = posicion_pilotos;
	}
	
	

}
