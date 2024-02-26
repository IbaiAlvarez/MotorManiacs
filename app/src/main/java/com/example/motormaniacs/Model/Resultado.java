package com.example.motormaniacs.Model;

public class Resultado {

    private int id_Premio;
    private Piloto piloto;
    private Equipo equipo;
    private Carrera carrera;
    private int puntos;
    private int posicion;

    public Resultado() {
    }

    public Resultado(int id_Premio, Piloto piloto, Equipo equipo, Carrera carrera, int puntos, int posicion) {
        this.id_Premio = id_Premio;
        this.piloto = piloto;
        this.equipo = equipo;
        this.carrera = carrera;
        this.puntos = puntos;
        this.posicion = posicion;
    }

    public int getId_Premio() {
        return id_Premio;
    }

    public void setId_Premio(int id_Premio) {
        this.id_Premio = id_Premio;
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

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
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
