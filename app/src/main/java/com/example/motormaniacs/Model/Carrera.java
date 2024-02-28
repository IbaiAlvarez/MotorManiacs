package com.example.motormaniacs.Model;

import java.util.ArrayList;

public class Carrera {
	private int id;
	private String circuito;
	private String fecha;
	private ArrayList<Resultado>  Posicion_pilotos =new ArrayList<Resultado>();

	public Carrera() {
	}

	public Carrera(int id, String circuito, String fecha, ArrayList<Resultado> posicion_pilotos) {
		this.id = id;
		this.circuito = circuito;
		this.fecha = fecha;
		Posicion_pilotos = posicion_pilotos;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return circuito;
	}
	public void setNombre(String nombre) {
		this.circuito = nombre;
	}
	public ArrayList<Resultado> getPosicion_pilotos() {
		return Posicion_pilotos;
	}
	public void setPosicion_pilotos(ArrayList<Resultado> posicion_pilotos) {
		Posicion_pilotos = posicion_pilotos;
	}

	public String getCircuito() {
		return circuito;
	}

	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
