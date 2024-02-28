package com.example.motormaniacs.Model;

import java.util.ArrayList;

public class Piloto {
	private int id;
	private int id_equipo;
	private String nombre;
	private String apellido;
	private int numero;
	private int top1;
	private int top5;
	private int top10;
	private int campeonatos;
	private int valoracion;
	private int temporadas;
	private String estado;
	//private int puntos;
	private ArrayList<Resultado> ResultadosPiloto =new ArrayList<Resultado>();


	public Piloto() {
	}
	public Piloto(String nombre, String apellido) {
		nombre = this.nombre;
		apellido = this.apellido;
	}

	public Piloto(int id, int id_equipo, String nombre, String apellido, int numero, int top1, int top5, int top10, int campeonatos, int valoracion, int temporadas, String estado, ArrayList<Resultado> resultadosPiloto) {
		this.id = id;
		this.id_equipo = id_equipo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.numero = numero;
		this.top1 = top1;
		this.top5 = top5;
		this.top10 = top10;
		this.campeonatos = campeonatos;
		this.valoracion = valoracion;
		this.temporadas = temporadas;
		this.estado = estado;
		ResultadosPiloto = resultadosPiloto;
	}

	public Piloto(String nombre, String apellido) {
		this.nombre=nombre;
		this.apellido=apellido;
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
		return top1;
	}
	public void setWins(int wins) {
		this.top1 = wins;
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
	public int getValoracion() {
		return valoracion;
	}
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public int getTop1() {
		return top1;
	}

	public void setTop1(int top1) {
		this.top1 = top1;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ArrayList<Resultado> getResultadosPiloto() {
		return ResultadosPiloto;
	}

	public void setResultadosPiloto(ArrayList<Resultado> resultadosPiloto) {
		ResultadosPiloto = resultadosPiloto;
	}

	public int getId_equipo() {
		return id_equipo;
	}

	public void setId_equipo(int id_equipo) {
		this.id_equipo = id_equipo;
	}

	@Override
	public String toString() {
		return "- " + nombre + " " + apellido + " #"+ numero;
	}
	
}