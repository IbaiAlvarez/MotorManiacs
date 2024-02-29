package com.example.motormaniacs.Model;

public class CarreraMostrar {

    private int posicion;
    private String nombre;
    private String apellido;
    private String equipo;
    private int puntos;

    public CarreraMostrar() {

    }

    public CarreraMostrar(int posicion, String nombre, String apellido, String equipo, int puntos) {
        this.posicion = posicion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.equipo = equipo;
        this.puntos = puntos;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
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

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
