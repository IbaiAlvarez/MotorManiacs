package com.example.motormaniacs.Model;

import java.io.Serializable;

public class Resultado implements Serializable {

    private int id_resultado;
    private Piloto piloto;
    private Equipo equipo;
    private int id_carrera;
    private int puntos;
    private int posicion;

    public Resultado() {
    }

    public Resultado(int id_resultado, Piloto piloto, Equipo equipo, int carrera, int puntos, int posicion) {
        this.id_resultado = id_resultado;
        this.piloto = piloto;
        this.equipo = equipo;
        this.id_carrera = carrera;
        this.puntos = puntos;
        this.posicion = posicion;
    }

    public int getId_resultado() {
        return id_resultado;
    }

    public void setId_resultado(int id_resultado) {
        this.id_resultado = id_resultado;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getCarreraId() {
        return id_carrera;
    }

    public void setCarreraId(int carrera) {
        this.id_carrera = carrera;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
