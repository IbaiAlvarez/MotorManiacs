package com.example.motormaniacs.Model.Conexion;

public class ConexionDatos {

    private static final String url = "jdbc:mysql://192.168.1.35:3306/motormaniacs?autoReconnect=true&useSSL=false";
    private static final String user = "administrador";
    private static final String password = "Elorrieta00";
    private static final String TABLA_CARRERAS = "carreras";
    private static final String TABLA_EQUIPOS = "equipos";
    private static final String TABLA_PILOTOS = "pilotos";
    private static final String TABLA_PREMIOS = "premios";
    private static final String TABLA_RESULTADOS = "resultados";
    private static final String TABLA_USUARIOS = "usuarios";

    public ConexionDatos(){}

    public static String getUrl() {
        return url;
    }
    public static String getUser() {
        return user;
    }
    public static String getPassword() {
        return password;
    }

    public static String getTABLA_CARRERAS() {
        return TABLA_CARRERAS;
    }

    public static String getTABLA_EQUIPOS() {
        return TABLA_EQUIPOS;
    }

    public static String getTABLA_PILOTOS() {
        return TABLA_PILOTOS;
    }

    public static String getTABLA_PREMIOS() {
        return TABLA_PREMIOS;
    }

    public static String getTABLA_RESULTADOS() {
        return TABLA_RESULTADOS;
    }

    public static String getTABLA_USUARIOS() {
        return TABLA_USUARIOS;
    }
}
