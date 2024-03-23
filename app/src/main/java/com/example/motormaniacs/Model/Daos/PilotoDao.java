package com.example.motormaniacs.Model.Daos;


import android.os.AsyncTask;

import com.example.motormaniacs.Controller.ReturnMethods;
import com.example.motormaniacs.Model.Conexion.ConexionDatos;
import com.example.motormaniacs.Model.Piloto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PilotoDao  extends Thread{

    private  final String url = ConexionDatos.getUrl();
    private  final String user = ConexionDatos.getUser();
    private  final String password = ConexionDatos.getPassword();
    private  final String TABLA_RESULTADOS = ConexionDatos.getTABLA_RESULTADOS();
    private  final String TABLA_PILOTOS = ConexionDatos.getTABLA_PILOTOS();
    private  final String TABLA_PREMIOS = ConexionDatos.getTABLA_PREMIOS();
    public  int  equipo_id_param = -1;
    public  int  piloto_id_param = -1;
    public  int  piloto_numero_param = -1;
    public  String  piloto_apellido_param = "";
    public  String  piloto_nombre_param = "";
    public  String  piloto_estado_param = "";
    public  ArrayList<Piloto>  pilotos_param = new ArrayList<Piloto>();


    //region Llamada metodos Select

    public ArrayList<Piloto> cargarPilotos() {
        try {
            ArrayList<Piloto> pilotos = new obtenerPilotos().execute().get();
            return pilotos;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Piloto> cargarPiltosEquipo(int id_equipo) {
        try {
            this.equipo_id_param = id_equipo;
            ArrayList<Piloto> pilotos = new cargarPilotosEquipo().execute().get();
            return pilotos;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Piloto cargarUnicoPilotoEquipo(int id_piloto, int id_equipo) {
        try {
            this.piloto_id_param = id_piloto;
            this.equipo_id_param = id_equipo;
            Piloto pilotos = new cargarUnPilotoEquipo().execute().get();
            return pilotos;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    public int cargarIdEquipo(int id_piloto) {
        try {
            this.piloto_id_param = id_piloto;
            return new ObtenerIdEquipo().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int verificarPiloto(String nombre, String apellido, int numero) {
        try {
            this.piloto_nombre_param = nombre;
            this.piloto_apellido_param = apellido;
            this.piloto_numero_param = numero;
            return new ComprobarPiloto().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    //endregion

    //region Llamada metodos Insert

    public ArrayList<Piloto> añadirPiloto(String nombre, String apellido, int numero, ArrayList<Piloto> pilotos){
        try {
            this.piloto_nombre_param = nombre;
            this.piloto_apellido_param = apellido;
            this.piloto_numero_param = numero;
            this.pilotos_param = pilotos;
            pilotos = new insertarPiloto().execute().get();
            return pilotos;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion

    //region Llamada metodos Update
    public ArrayList<Piloto> editarPiloto(ArrayList<Piloto> pilotos, int idPiloto, int id_EquipoNuevo,String estado,int numero){
        try {
            this.piloto_id_param = idPiloto;
            this.piloto_estado_param = estado;
            this.equipo_id_param = id_EquipoNuevo;
            this.pilotos_param = pilotos;
            this.piloto_numero_param = numero;
            pilotos = new cambiarPiloto().execute().get();
            return pilotos;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion

    private class obtenerPilotos extends AsyncTask<Void, Void, ArrayList<Piloto>> {
        @Override
        protected ArrayList<Piloto> doInBackground(Void... voids) {
            ArrayList<Piloto> pilotos = new ArrayList<Piloto>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT * FROM "+TABLA_PILOTOS+";");

                while(req.next()) {
                    Piloto p = new Piloto();

                    p.setId(req.getInt(1));
                    p.setId_equipo(req.getInt(2));
                    p.setNombre(req.getString(3));
                    p.setApellido(req.getString(4));
                    p.setEstado(req.getString(5));
                    p.setNumero(req.getInt(6));


                    //Obtiene las victorias del piloto
                    Statement stmt2 = conn.createStatement();
                    ResultSet req2 = stmt2.executeQuery("SELECT COUNT(CASE WHEN Posicion = 1 THEN 1 END) AS 'TOP 1', COUNT(CASE WHEN Posicion <= 5 THEN 1 END) AS 'TOP 5', COUNT(CASE WHEN Posicion <= 10 THEN 1 END) AS 'TOP 10' FROM " + TABLA_RESULTADOS + " WHERE piloto_id=" + p.getId() + ";");

                    if (req2.next()) {
                        p.setTop1(req2.getInt(1));
                        p.setTop5(req2.getInt(2));
                        p.setTop10(req2.getInt(3));
                        stmt2.close();
                        req2.close();
                    }

                    //Obtiene los campeonatos ganados del piloto
                    Statement stmt3 = conn.createStatement();
                    ResultSet req3 = stmt3.executeQuery("SELECT COUNT(*) FROM "+TABLA_PREMIOS+" WHERE Piloto_id="+p.getId()+" and Nombre='Campeonato'; ");

                    if(req3.next()){
                        p.setCampeonatos(req3.getInt(1));
                        stmt3.close();
                        req3.close();
                    }

                    ReturnMethods rm = new ReturnMethods();
                    p.setValoracion(rm.calcularValoracion(p));

                    pilotos.add(p);
                }

                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return pilotos;
        }
    }

    private class cargarPilotosEquipo extends AsyncTask<Void, Void, ArrayList<Piloto>> {
        @Override
        protected ArrayList<Piloto> doInBackground(Void... voids) {
            ArrayList<Piloto> pilotos = new ArrayList<Piloto>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT * FROM "+TABLA_PILOTOS+" where equipo_id='"+equipo_id_param+"';");

                while(req.next()) {
                    Piloto p = new Piloto();

                    p.setId(req.getInt(1));
                    p.setId_equipo(req.getInt(2));
                    p.setNombre(req.getString(3));
                    p.setApellido(req.getString(4));
                    p.setEstado(req.getString(5));
                    p.setNumero(req.getInt(6));


                    //Obtiene las victorias del piloto
                    Statement stmt2 = conn.createStatement();
                    ResultSet req2 = stmt2.executeQuery("SELECT COUNT(CASE WHEN Posicion = 1 THEN 1 END) AS 'TOP 1', COUNT(CASE WHEN Posicion <= 5 THEN 1 END) AS 'TOP 5', COUNT(CASE WHEN Posicion <= 10 THEN 1 END) AS 'TOP 10' FROM " + TABLA_RESULTADOS + " WHERE piloto_id=" + p.getId() + ";");

                    if (req2.next()) {
                        p.setTop1(req2.getInt(1));
                        p.setTop5(req2.getInt(2));
                        p.setTop10(req2.getInt(3));
                        stmt2.close();
                        req2.close();
                    }

                    //Obtiene los campeonatos ganados del piloto
                    Statement stmt3 = conn.createStatement();
                    ResultSet req3 = stmt3.executeQuery("SELECT COUNT(*) FROM "+TABLA_PREMIOS+" WHERE Piloto_id="+p.getId()+" and Nombre='Campeonato'; ");

                    if(req3.next()){
                        p.setCampeonatos(req3.getInt(1));
                        stmt3.close();
                        req3.close();
                    }

                    ReturnMethods rm = new ReturnMethods();
                    p.setValoracion(rm.calcularValoracion(p));

                    pilotos.add(p);
                }

                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return pilotos;
        }
    }

    private class cargarUnPilotoEquipo extends AsyncTask<Void, Void, Piloto> {
        @Override
        protected Piloto doInBackground(Void... voids) {
            Piloto p = new Piloto();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT * FROM "+TABLA_PILOTOS+" where Piloto_id="+piloto_id_param+" and equipo_id='"+equipo_id_param+"';");

                if(req.next()) {

                    p.setId(req.getInt(1));
                    p.setId_equipo(req.getInt(2));
                    p.setNombre(req.getString(3));
                    p.setApellido(req.getString(4));
                    p.setEstado(req.getString(5));
                    p.setNumero(req.getInt(6));


                    //Obtiene las victorias del piloto
                    Statement stmt2 = conn.createStatement();
                    ResultSet req2 = stmt2.executeQuery("SELECT COUNT(CASE WHEN Posicion = 1 THEN 1 END) AS 'TOP 1', COUNT(CASE WHEN Posicion <= 5 THEN 1 END) AS 'TOP 5', COUNT(CASE WHEN Posicion <= 10 THEN 1 END) AS 'TOP 10' FROM " + TABLA_RESULTADOS + " WHERE piloto_id=" + p.getId() + ";");

                    if (req2.next()) {
                        p.setTop1(req2.getInt(1));
                        p.setTop5(req2.getInt(2));
                        p.setTop10(req2.getInt(3));
                        stmt2.close();
                        req2.close();
                    }

                    //Obtiene los campeonatos ganados del piloto
                    Statement stmt3 = conn.createStatement();
                    ResultSet req3 = stmt3.executeQuery("SELECT COUNT(*) FROM "+TABLA_PREMIOS+" WHERE Piloto_id="+p.getId()+" and Nombre='Campeonato'; ");

                    if(req3.next()){
                        p.setCampeonatos(req3.getInt(1));
                        stmt3.close();
                        req3.close();
                    }

                    ReturnMethods rm = new ReturnMethods();
                    p.setValoracion(rm.calcularValoracion(p));

                }

                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return p;
        }
    }

    private class insertarPiloto extends AsyncTask<Void, Void, ArrayList<Piloto>> {
        @Override
        protected ArrayList<Piloto> doInBackground(Void... voids) {

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT COUNT(*) FROM "+TABLA_PILOTOS+" where nombre='"+piloto_nombre_param+"' and apellido='"+piloto_apellido_param+"' and numero="+piloto_numero_param+";");

                if(req.next()) {
                    if(req.getInt(1)==0){
                        Statement stmt2 = conn.createStatement();
                        int req2 = stmt.executeUpdate("INSERT INTO "+TABLA_PILOTOS+" (Nombre, Apellido, Estado, Numero) VALUES ('"+piloto_nombre_param+"','"+piloto_apellido_param+"','retirado',"+piloto_numero_param+")");
                        Piloto p = new Piloto();
                        p.setNombre(piloto_nombre_param);
                        p.setApellido(piloto_apellido_param);
                        p.setEstado("retirado");
                        p.setNumero(piloto_numero_param);

                        pilotos_param.add(p);
                    }
                }

                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return pilotos_param;
        }
    }

    private class ObtenerIdEquipo extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            int id=-1;

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT equipo_id FROM "+TABLA_PILOTOS+" where piloto_id="+piloto_id_param+";");

                if(rs.next()) {
                    id = rs.getInt(1);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return id;
        }
    }

    private class ComprobarPiloto extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            int cant=-1;

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM "+TABLA_PILOTOS+" where Nombre='"+piloto_nombre_param+"' AND Apellido='"+piloto_apellido_param+"';");

                if(rs.next()) {
                    cant = rs.getInt(1);

                    if(cant ==0) {

                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT COUNT(*) FROM " + TABLA_PILOTOS + " where Numero=" + piloto_numero_param + ";");

                        if (rs2.next()) {
                            cant = rs.getInt(1);

                            if(cant == 0){
                                cant = -1;
                            }
                        }
                    }else{
                        cant = 0;
                    }
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return cant;
        }
    }

    private class cambiarPiloto extends AsyncTask<Void, Void, ArrayList<Piloto>> {
        @Override
        protected ArrayList<Piloto> doInBackground(Void... voids) {

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                if(equipo_id_param!=-1) {
                    int req = stmt.executeUpdate("UPDATE " + TABLA_PILOTOS + " SET Equipo_id = '" + equipo_id_param + "', estado = '" + piloto_estado_param + "', numero=" + piloto_numero_param + " WHERE Piloto_id = " + piloto_id_param + ";");
                }else{
                    int req = stmt.executeUpdate("UPDATE "+TABLA_PILOTOS+" SET Equipo_id = NULL, estado = '"+piloto_estado_param+"', numero="+piloto_numero_param+" WHERE Piloto_id = "+piloto_id_param+";");
                }
                boolean encontrado = false;
                for (int i = 0;i<pilotos_param.size()&&encontrado;i++){
                    if(pilotos_param.get(i).getId() == piloto_id_param){
                        pilotos_param.get(i).setId_equipo(equipo_id_param);
                        pilotos_param.get(i).setEstado(piloto_estado_param);
                        pilotos_param.get(i).setNumero(piloto_numero_param);
                        encontrado = true;
                    }
                }
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return pilotos_param;
        }
    }

}
