package com.example.motormaniacs.Model;

public class Piloto {
	private int id;
	private String nombre;
	private String apellido;
	private int numero;
	private int wins;
	private int top5;
	private int top10;
	private int campeonatos;
	private int promedio;
	private int temporadas;
	private boolean añadido;
	//private int puntos;
	
	public Piloto() {
		this.id= 0;
		this.nombre = "";
		this.apellido = "";
		this.numero = 0;
		this.wins = 0;
		this.top5 = 0;
		this.top10 = 0;
		this.campeonatos = 0;
		this.añadido = false;
		//this.puntos = 0;
	}
	public Piloto(String nombre, String apellido, int numero) {
		this.id = 0;
		this.wins = 0;
		this.top5 = 0;
		this.top10 = 0;
		this.campeonatos = 0;
		this.apellido = apellido;
		this.numero = numero;
		this.nombre = nombre;
		this.añadido = false;
		//this.puntos = 0;
	}
	public Piloto(int id, String nombre, String apellido, int numero) {
		this.id = id;
		this.wins = 0;
		this.top5 = 0;
		this.top10 = 0;
		this.campeonatos = 0;
		this.apellido = apellido;
		this.numero = numero;
		this.nombre = nombre;
		this.añadido = false;
		//this.puntos = 0;
	}
	public Piloto(int id, String nombre, String apellido, int numero, int wins, int top5, int top10, int top15, int top20, int top30, int campeonatos, int temporadas, int promedio) {
		this.id = id;
		this.wins = wins;
		this.top5 = top5;
		this.top10 = top10;
		this.campeonatos = campeonatos;
		this.temporadas = temporadas;
		this.apellido = apellido;
		this.numero = numero;
		this.nombre = nombre;
		this.promedio = promedio;
		this.añadido = false;
		//this.puntos = 0;
	}
	
	public boolean isAñadido() {
		return añadido;
	}
	public void setAñadido(boolean añadido) {
		this.añadido = añadido;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getTop5() {
		return top5;
	}
	public void setTop5(int top5) {
		this.top5 = top5;
	}
	public int getTop10() {
		return top10;
	}
	public void setTop10(int top10) {
		this.top10 = top10;
	}
	public int getTemporadas() {
		return temporadas;
	}
	public void setTemporadas(int temporadas) {
		this.temporadas = temporadas;
	}
	public int getCampeonatos() {
		return campeonatos;
	}
	public void setCampeonatos(int campeonatos) {
		this.campeonatos = campeonatos;
	}
	public int getPromedio() {
		return promedio;
	}
	public void setPromedio(int promedio) {
		this.promedio = promedio;
	}
	@Override
	public String toString() {
		return "- " + nombre + " " + apellido + " #"+ numero;
	}

	public boolean estaAñadido() {
		return añadido;
	}
	
}