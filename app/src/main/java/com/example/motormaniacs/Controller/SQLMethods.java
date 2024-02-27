package com.example.motormaniacs.Controller;


import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.util.ArrayList;

import kotlin.contracts.Returns;

public class SQLMethods {

    ConnectionClass con_class = new ConnectionClass();
    ReturnMethods returnMethods = new ReturnMethods();

    public Equipo cargarEquipo(String nombre_equipo){
        Equipo e = new Equipo();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT equipo_id,nombre,estado FROM equipos where nombre='"+nombre_equipo+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                e.setId(req.getInt(1));
                e.setNombre(req.getString(2));
                e.setEstado(req.getString(3));
            }

            kargarPilotosEquipo(e.getId());

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
            String query =  "SELECT * FROM pilotos where equipo_id='"+id_equipo_seleccionado+"';";
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

    public int[] consultarVictoriasPiloto(int piloto_id){
        int[] victorias = new int[3];

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT COUNT(CASE WHEN Posicion = 1 THEN 1 END) AS 'TOP 1', COUNT(CASE WHEN Posicion <= 5 THEN 1 END) AS 'TOP 5', COUNT(CASE WHEN Posicion <= 10 THEN 1 END) AS 'TOP 10' FROM resultados WHERE piloto_id="+piloto_id+";";
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

            String query =  "SELECT COUNT(*) FROM premios WHERE Piloto_id="+piloto_id+" and Nombre='Campeonato'; ";
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

}
