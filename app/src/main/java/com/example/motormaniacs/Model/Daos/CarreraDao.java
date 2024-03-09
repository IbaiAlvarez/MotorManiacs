package com.example.motormaniacs.Model.Daos;

import android.os.AsyncTask;

import com.example.motormaniacs.Model.Carrera;
import com.example.motormaniacs.Model.Conexion.ConexionDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CarreraDao {

    private final String url = ConexionDatos.getUrl();
    private final String user = ConexionDatos.getUser();
    private final String password = ConexionDatos.getPassword();
    private final String TABLA_EQUIPOS = ConexionDatos.getTABLA_EQUIPOS();
    private final String TABLA_RESULTADOS = ConexionDatos.getTABLA_RESULTADOS();
    private final String TABLA_CARRERAS = ConexionDatos.getTABLA_CARRERAS();
    public String  nombre_equipo_param = "";
    public int  equipo_id_param = -1;
    public static int carrera_id_param = -1;
    public boolean  carga_terminada = false;
    public String  circuito_param = "";
    public String  fecha_param = "";
    public ArrayList<Carrera>  carreras_param = new ArrayList<Carrera>();
    public Carrera  carrera_param = new Carrera();


    //region Llamada metodos Select

    public ArrayList<Carrera> cargarCarreras() {
        try {
            ArrayList<Carrera> carreras = new obtenerCarreras().execute().get();

            for(int i=0;i<carreras.size();i++){
                ResultadoDao rDao = new ResultadoDao();
                carreras.get(i).setPosicion_pilotos(rDao.cargarResultadosCarreraId(carreras.get(i).getId()));
            }

            return carreras;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Carrera cargarCarreraId(int carrera_id) {
        try {
            this.carrera_id_param = carrera_id;

            Carrera c = new obtenerCarreraById().execute().get();

            ResultadoDao rDao = new ResultadoDao();
            c.setPosicion_pilotos(rDao.cargarResultadosCarreraId(c.getId()));

            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int obtenerUltimaCarreraId() {
        try {
            return new cargarUltimaCarreraId().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //endregion

    //region Llamada metodos Insert
    public boolean insertarCarrera(String circuito, String fecha,Carrera carrera) {
        try {
            this.circuito_param = circuito;
            this.fecha_param = fecha;
            this.carrera_param = carrera;

            return new guardarCarrera().execute().get();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //endregion


    private class obtenerCarreras extends AsyncTask<Void, Void, ArrayList<Carrera>> {
        @Override
        protected ArrayList<Carrera> doInBackground(Void... voids) {
            ArrayList<Carrera> carreras = new ArrayList<Carrera>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM "+TABLA_CARRERAS+";");

                while (rs.next()){
                    Carrera c= new Carrera();
                    c.setId(rs.getInt(1));
                    c.setCircuito(rs.getString(2));
                    c.setFecha(rs.getString(3));
                    carreras.add(c);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return carreras;
        }
    }
    private class obtenerCarreraById extends AsyncTask<Void, Void, Carrera> {
        @Override
        protected Carrera doInBackground(Void... voids) {
            Carrera c = new Carrera();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM "+TABLA_CARRERAS+" where carrera_id="+carrera_id_param+";");

                if (rs.next()){
                    c.setId(rs.getInt(1));
                    c.setCircuito(rs.getString(2));
                    c.setFecha(rs.getString(3));
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return c;
        }
    }

    private class guardarCarrera extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean guardado = false;
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT COUNT(*) FROM "+TABLA_CARRERAS+" where fecha='"+fecha_param+"';");

                if(req.next()) {
                    if(req.getInt(1)==0){
                        Statement stmt2 = conn.createStatement();
                        int req2 = stmt.executeUpdate("INSERT INTO "+TABLA_CARRERAS+" (circuito, fecha) VALUES ('"+circuito_param+"','"+fecha_param+"')");
                        guardado = true;
                    }
                }

                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return guardado;
        }
    }

    private class cargarUltimaCarreraId extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            int carrera_id = -1;
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery( "SELECT carrera_id FROM "+TABLA_CARRERAS+" order BY fecha DESC LIMIT 1;");

                if(req.next()) {
                    carrera_id=req.getInt(1);
                }

                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return carrera_id;
        }
    }


}
