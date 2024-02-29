package com.example.motormaniacs.Model;

public class PosicionPilotos {

    private int id;
    private int puntos;
    private String nombre;
    private String apellido;
    private int numero;

    public PosicionPilotos(int id, int puntos, String nombre, String apellido, int numero) {
        this.id = id;
        this.puntos = puntos;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
    }

    public PosicionPilotos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
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
}
