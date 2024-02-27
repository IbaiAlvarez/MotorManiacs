package com.example.motormaniacs.Controller;


import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.Model.Resultado;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.util.ArrayList;

import kotlin.contracts.Returns;

public class SQLMethods {

    private final String TABLA_CARRERAS = "carreras";
    private final String TABLA_EQUIPOS = "equipos";
    private final String TABLA_PILOTOS = "pilotos";
    private final String TABLA_PREMIOS = "premios";
    private final String TABLA_RESULTADOS = "resultados";
    ConnectionClass con_class = new ConnectionClass();
    ReturnMethods returnMethods = new ReturnMethods();

    public Equipo cargarEquipoNombre(String nombre_equipo){
        Equipo e = new Equipo();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT equipo_id,nombre,estado FROM "+TABLA_EQUIPOS+" where nombre='"+nombre_equipo+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                e.setId(req.getInt(1));
                e.setNombre(req.getString(2));
                e.setEstado(req.getString(3));
            }

            e.setPilotos(kargarPilotosEquipo(e.getId()));

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return e;
    }

    public ArrayList<Piloto> kargarPilotosEquipo (int id_equipo_seleccionado){
        ArrayList<Piloto> pilotos = new ArrayList<Piloto>();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT * FROM "+TABLA_PILOTOS+" where equipo_id='"+id_equipo_seleccionado+"';";
            ResultSet req = comand.executeQuery(query);

            while(req.next()) {
                Piloto p = new Piloto();

                p.setId(req.getInt(1));
                p.setId_equipo(req.getInt(2));
                p.setNombre(req.getString(3));
                p.setApellido(req.getString(4));
                p.setEstado(req.getString(5));
                p.setNumero(req.getInt(6));

                int[] posiciones = consultarVictoriasPiloto(p.getId());

                p.setTop1(posiciones[0]);
                p.setTop5(posiciones[1]);
                p.setTop10(posiciones[2]);
                p.setCampeonatos(consultarCampeonatosPiloto(p.getId()));
                p.setValoracion(returnMethods.calcularValoracion(p));

                pilotos.add(p);
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return pilotos;
    }

    public Piloto kargarUnicoPilotoEquipo (int id_piloto,int id_equipo_seleccionado){
        Piloto p = new Piloto();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT * FROM "+TABLA_PILOTOS+" where Piloto_id="+id_piloto+" and equipo_id='"+id_equipo_seleccionado+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {

                p.setId(req.getInt(1));
                p.setId_equipo(req.getInt(2));
                p.setNombre(req.getString(3));
                p.setApellido(req.getString(4));
                p.setEstado(req.getString(5));
                p.setNumero(req.getInt(6));

                int[] posiciones = consultarVictoriasPiloto(p.getId());

                p.setTop1(posiciones[0]);
                p.setTop5(posiciones[1]);
                p.setTop10(posiciones[2]);
                p.setCampeonatos(consultarCampeonatosPiloto(p.getId()));
                p.setValoracion(returnMethods.calcularValoracion(p));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return p;
    }

    public int[] consultarVictoriasPiloto(int piloto_id){
        int[] victorias = new int[3];

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT COUNT(CASE WHEN Posicion = 1 THEN 1 END) AS 'TOP 1', COUNT(CASE WHEN Posicion <= 5 THEN 1 END) AS 'TOP 5', COUNT(CASE WHEN Posicion <= 10 THEN 1 END) AS 'TOP 10' FROM "+TABLA_RESULTADOS+" WHERE piloto_id="+piloto_id+";";
            ResultSet req_vicotrias = comand.executeQuery(query);

            if(req_vicotrias.next()){
                victorias[0]=(req_vicotrias.getInt(1));
                victorias[1]=(req_vicotrias.getInt(2));
                victorias[2]=(req_vicotrias.getInt(3));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return victorias;
    }

    public int consultarCampeonatosPiloto(int piloto_id){
        int victorias = 0;


        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT COUNT(*) FROM "+TABLA_PREMIOS+" WHERE Piloto_id="+piloto_id+" and Nombre='Campeonato'; ";
            ResultSet req_vicotrias = comand.executeQuery(query);

            if(req_vicotrias.next()){
                victorias=(req_vicotrias.getInt(1));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return victorias;
    }

    public ArrayList<String> obtenerListaEquipos(){
        ArrayList<String> equipos = new ArrayList<String>();

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT nombre from "+TABLA_EQUIPOS+";";
            ResultSet req = comand.executeQuery(query);

            while(req.next()){
                equipos.add(req.getString(1));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return equipos;
    }

    public ArrayList<Resultado> cargarResultadosUltimaCarrera(){
        ArrayList<Resultado> resultados = new ArrayList<Resultado>();
        int carrera_id = -1;

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT carrera_id FROM "+TABLA_CARRERAS+" order BY fecha DESC LIMIT 1;";
            ResultSet req = comand.executeQuery(query);

            if(req.next()){
                carrera_id = req.getInt(1);
            }

            String query_resultado =  "SELECT * FROM "+TABLA_RESULTADOS+" where carrera_id="+carrera_id+";";
            ResultSet req_resultado = comand.executeQuery(query_resultado);

            while (req_resultado.next()){
                Resultado res = new Resultado();

                res.setId_resultado(req_resultado.getInt(1));
                res.setPiloto(kargarUnicoPilotoEquipo(req_resultado.getInt(2),req_resultado.getInt(3)));
                res.setEquipo(cargarEquipoId(req_resultado.getInt(3)));
                res.setPosicion(req_resultado.getInt(5));
                res.setPuntos(req_resultado.getInt(6));
                resultados.add(res);
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }

        return resultados;
    }

    public Equipo cargarEquipoId(int id_equipo){
        Equipo e = new Equipo();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT equipo_id,nombre,estado FROM "+TABLA_EQUIPOS+" where equipo_id='"+id_equipo+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                e.setId(req.getInt(1));
                e.setNombre(req.getString(2));
                e.setEstado(req.getString(3));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return e;
    }


}