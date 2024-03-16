package com.example.motormaniacs.Model.Daos;

import android.os.AsyncTask;

import com.example.motormaniacs.Model.Conexion.ConexionDatos;
import com.example.motormaniacs.Model.Equipo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EquipoDao {

    private static final String url = ConexionDatos.getUrl();
    private static final String user = ConexionDatos.getUser();
    private static final String password = ConexionDatos.getPassword();
    private static final String TABLA_EQUIPOS = ConexionDatos.getTABLA_EQUIPOS();
    private static final String TABLA_PILOTOS = ConexionDatos.getTABLA_PILOTOS();
    public static String  nombre_equipo_param = "";
    public static String  estado_equipo_param = "";
    public static int  equipo_id_param = -1;
    public  ArrayList<Equipo>  equipos_param = new ArrayList<Equipo>();

    //region Llamada metodos Select
    public ArrayList<Equipo> cargarEquipos() {
        try {
            ArrayList<Equipo> equipos = new ObtenerEquipos().execute().get();
            for(int i=0;i<equipos.size();i++){
                PilotoDao pDao = new PilotoDao();
                equipos.get(i).setPilotos(pDao.cargarPiltosEquipo(equipos.get(i).getId()));
            }
            return equipos;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Equipo cargarEquipoNombre(String nombre_equipo) {
        try {
            this.nombre_equipo_param = nombre_equipo;
            Equipo e = new ObtenerEquipoNombre().execute().get();
            PilotoDao pDao = new PilotoDao();
            e.setPilotos(pDao.cargarPiltosEquipo(e.getId()));
            return e;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Equipo cargarEquipoId(int id_equipo) {
        try {
            this.equipo_id_param = id_equipo;
            Equipo e = new ObtenerEquipoById().execute().get();
            PilotoDao pDao = new PilotoDao();
            e.setPilotos(pDao.cargarPiltosEquipo(e.getId()));
            return e;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> cargarNombresEquipos() {
        try {
            return new ObtenerNombresEquipos().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    //endregion

    //region Llamada metodos Insert

    public ArrayList<Equipo> añadirEquipo(String nombre_equipo,ArrayList<Equipo>  equipos) {
        try {
            this.nombre_equipo_param = nombre_equipo;
            this.equipos_param = equipos;
            return new insertarEquipo().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion

    //region Llamada metodos Update

    public ArrayList<Equipo> editarEquipo(int idEquipo,String nombre_equipo,String estado_equipo, ArrayList<Equipo>  equipos) {
        try {
            this.equipo_id_param  = idEquipo;
            this.nombre_equipo_param = nombre_equipo;
            this.estado_equipo_param = estado_equipo;
            this.equipos_param = equipos;
            return new cambiarEquipo().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion
    private class ObtenerEquipos extends AsyncTask<Void, Void, ArrayList<Equipo>> {
        @Override
        protected ArrayList<Equipo> doInBackground(Void... voids) {
            ArrayList<Equipo> equipos = new ArrayList<Equipo>();
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT equipo_id,nombre,estado FROM "+TABLA_EQUIPOS+";");

                while(rs.next()) {
                    Equipo e = new Equipo();
                    e.setId(rs.getInt(1));
                    e.setNombre(rs.getString(2));
                    e.setEstado(rs.getString(3));
                    equipos.add(e);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return equipos;
        }
    }
    private class ObtenerEquipoNombre extends AsyncTask<Void, Void, Equipo> {
        @Override
        protected Equipo doInBackground(Void... voids) {
            Equipo e = new Equipo();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT equipo_id,nombre,estado FROM "+TABLA_EQUIPOS+" where nombre='"+nombre_equipo_param+"';");

                if(rs.next()) {
                    e.setId(rs.getInt(1));
                    e.setNombre(rs.getString(2));
                    e.setEstado(rs.getString(3));
                }
                equipo_id_param = e.getId();

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return e;
        }
    }

    private class ObtenerEquipoById extends AsyncTask<Void, Void, Equipo> {
        @Override
        protected Equipo doInBackground(Void... voids) {
            Equipo e = new Equipo();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT equipo_id,nombre,estado FROM "+TABLA_EQUIPOS+" where Equipo_id='"+equipo_id_param+"';");

                if(rs.next()) {
                    e.setId(rs.getInt(1));
                    e.setNombre(rs.getString(2));
                    e.setEstado(rs.getString(3));
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return e;
        }
    }


    private class ObtenerNombresEquipos extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> nombres = new ArrayList<String>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nombre FROM "+TABLA_EQUIPOS+";");

                while(rs.next()) {
                    nombres.add(rs.getString(1));
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return nombres;
        }
    }

    private class insertarEquipo extends AsyncTask<Void, Void, ArrayList<Equipo>> {
        @Override
        protected ArrayList<Equipo> doInBackground(Void... voids) {

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT COUNT(*) FROM "+TABLA_EQUIPOS+" where nombre='"+nombre_equipo_param+"';");

                if(req.next()) {
                    if(req.getInt(1)==0){
                        Statement stmt2 = conn.createStatement();
                        int req2 = stmt2.executeUpdate("INSERT INTO "+TABLA_EQUIPOS+" (Nombre, Estado) VALUES ('"+nombre_equipo_param+"','retirado');");
                        Equipo e = new Equipo();
                        e.setNombre(nombre_equipo_param);
                        e.setEstado("retirado");

                        equipos_param.add(e);
                    }
                }

                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return equipos_param;
        }
    }

    private class cambiarEquipo extends AsyncTask<Void, Void, ArrayList<Equipo>> {
        @Override
        protected ArrayList<Equipo> doInBackground(Void... voids) {

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT COUNT(*) FROM "+TABLA_EQUIPOS+" where nombre='"+nombre_equipo_param+"';");

                if(req.next()) {
                    if(req.getInt(1)==0){
                        Statement stmt2 = conn.createStatement();
                        int req2 = stmt2.executeUpdate("UPDATE "+TABLA_EQUIPOS+" SET Estado = '"+estado_equipo_param+"', Nombre='"+nombre_equipo_param+"' WHERE Equipo_id = "+equipo_id_param+";");

                        if(estado_equipo_param.equals("retirado")){
                            int req3 = stmt2.executeUpdate("UPDATE "+TABLA_PILOTOS+" SET Equipo_Id = NULL WHERE Equipo_Id = "+equipo_id_param+";");
                        }

                        boolean encontrado = false;
                        for (int i = 0;i<equipos_param.size() && encontrado;i++){
                            if(equipos_param.get(i).getId() == equipo_id_param){
                                equipos_param.get(i).setEstado(estado_equipo_param);
                                equipos_param.get(i).setNombre(nombre_equipo_param);
                                encontrado = true;
                            }
                        }
                    }
                }

                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return equipos_param;
        }
    }
}
