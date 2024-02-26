package com.example.motormaniacs.Model;

import java.util.ArrayList;

public class Equipo {
	private int id;
	private String nombre;
	private ArrayList<Piloto> pilotos = new ArrayList<Piloto>();
	
	public Equipo () {
		this.id = 0;
		this.nombre= "";
		this.pilotos = new ArrayList<Piloto>();
	}
	public Equipo (int id, String nombre, ArrayList<Piloto> pilotos) {
		this.id= id;
		this.nombre= nombre;
		this.pilotos = pilotos;
	}
	public Equipo (int id, String nombre, ArrayList<Piloto> pilotos, int piloto1, int piloto2, int piloto3, int piloto4) {
		this.id= id;
		this.nombre= nombre;
		this.pilotos = pilotos;
	}
	
	
	public Equipo (String nombre, ArrayList<Piloto> pilotos) {
		this.id = 0;
		this.nombre= nombre;
		this.pilotos = pilotos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Piloto> getPilotos() {
		return pilotos;
	}
	public void setPilotos(ArrayList<Piloto> pilotos) {
		this.pilotos = pilotos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return nombre + " #" + pilotos.get(0).getNumero() + " #" + pilotos.get(1).getNumero() + " #" + pilotos.get(2).getNumero() +" #" + pilotos.get(3).getNumero();
	}

}

