package com.example.motormaniacs.Model.Daos;

import android.media.audiofx.DynamicsProcessing;
import android.os.AsyncTask;

import com.example.motormaniacs.Model.Conexion.ConexionDatos;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.Model.Resultado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ResultadoDao  extends Thread{

    private final String url = ConexionDatos.getUrl();
    private final String user = ConexionDatos.getUser();
    private final String password = ConexionDatos.getPassword();
    private final String TABLA_EQUIPOS = ConexionDatos.getTABLA_EQUIPOS();
    private final String TABLA_RESULTADOS = ConexionDatos.getTABLA_RESULTADOS();
    private final String TABLA_CARRERAS = ConexionDatos.getTABLA_CARRERAS();
    private final String TABLA_PILOTOS = ConexionDatos.getTABLA_PILOTOS();
    public String  nombre_equipo_param = "";
    public int  equipo_id_param = -1;
    public int  piloto_id_param = -1;
    public int  carrera_id_param = -1;
    public int  posicion_param = -1;
    public int  puntos_param = -1;
    public boolean  carga_terminada = false;


    //region Llamada metodos Select
    public ArrayList<Resultado> cargarResultadosUltimaCarrera() {
        try {
            return new obtenerResultadosUltimaCarrera().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Resultado> cargarResultadosCarreraId(int id_carrera) {
        try {
            this.carrera_id_param = id_carrera;
            ArrayList<Resultado> resultados = new obtenerResultadosCarreraId().execute().get();

            return resultados;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion


    //region Llamada metodos Insert
    public boolean insertarResultado(int piloto_id, int equipo_id, int carrera, int posicion, int puntos) {
        try {
            this.piloto_id_param = piloto_id;
            this.carrera_id_param = carrera;
            this.posicion_param = posicion;
            this.puntos_param = puntos;
            this.equipo_id_param = equipo_id;
            return new guardarResultado().execute().get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    //endregion

    private class obtenerResultadosUltimaCarrera extends AsyncTask<Void, Void, ArrayList<Resultado>> {
        @Override
        protected ArrayList<Resultado> doInBackground(Void... voids) {
            ArrayList<Resultado> resultados = new ArrayList<Resultado>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT r.Resultado_id,r.Piloto_id, r.Equipo_id, r.Carrera_id, r.Posicion, r.Puntos, \n" +
                        "       p.Nombre AS Nombre_Piloto, p.Apellido AS Apellido_Piloto, p.Estado AS Estado_Piloto, p.Numero AS Numero_Piloto,\n" +
                        "       e.Nombre AS Nombre_Equipo, e.Estado AS Estado_Equipo\n" +
                        "FROM resultados r\n" +
                        "JOIN pilotos p ON r.Piloto_id = p.Piloto_id\n" +
                        "JOIN equipos e ON r.Equipo_id = e.Equipo_id\n" +
                        "WHERE r.Carrera_id = (SELECT carrera_id FROM carreras ORDER BY fecha DESC LIMIT 1);");

                while(rs.next()){
                    Resultado r = new Resultado();
                    Equipo e = new Equipo();
                    Piloto p = new Piloto();

                    r.setId_resultado(rs.getInt(1));
                    p.setId(rs.getInt(2));
                    p.setId_equipo(rs.getInt(3));

                    e.setId(rs.getInt(3));

                    r.setCarreraId(rs.getInt(4));
                    r.setPosicion(rs.getInt(5));
                    r.setPuntos(rs.getInt(6));

                    p.setNombre(rs.getString(7));
                    p.setApellido(rs.getString(8));
                    p.setEstado(rs.getString(9));
                    p.setNumero(rs.getInt(10));

                    e.setNombre(rs.getString(11));
                    e.setEstado(rs.getString(12));

                    r.setEquipo(e);
                    r.setPiloto(p);

                    resultados.add(r);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return resultados;
        }
    }

    private class obtenerResultadosCarreraId extends AsyncTask<Void, Void, ArrayList<Resultado>> {
        @Override
        protected ArrayList<Resultado> doInBackground(Void... voids) {
            ArrayList<Resultado> resultados = new ArrayList<Resultado>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);

                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery("select c.carrera_id C_ID, c.circuito C_CIRCUITO, c.fecha C_FECHA, r.Resultado_id R_ID, r.Posicion R_POSICION,r.Puntos R_PUNTOS,p.Piloto_id P_ID,p.Nombre P_NOMBRE, p.Apellido P_APELLIDO ,p.Numero P_NUMERO, e.Equipo_id E_ID, e.Nombre E_NOMBRE from "+TABLA_CARRERAS+" c join "+TABLA_RESULTADOS+" r on c.carrera_id=r.Carrera_id join "+TABLA_PILOTOS+" p on r.Piloto_id=p.Piloto_id join "+TABLA_EQUIPOS+" e on r.Equipo_id=e.Equipo_id where c.carrera_id = "+carrera_id_param+" ORDER BY `fecha` DESC; ");

                while (rs2.next()){
                    Resultado res = new Resultado();

                    res.setId_resultado(rs2.getInt(4));
                    res.setPosicion(rs2.getInt(5));
                    res.setPuntos(rs2.getInt(6));

                    Piloto p = new Piloto();
                    p.setId(rs2.getInt(7));
                    p.setNombre(rs2.getString(8));
                    p.setApellido(rs2.getString(9));
                    p.setNumero(rs2.getInt(10));
                    res.setPiloto(p);

                    Equipo e = new Equipo();
                    e.setId(rs2.getInt(11));
                    e.setNombre(rs2.getString(12));
                    res.setEquipo(e);

                    resultados.add(res);
                }

                rs2.close();
                stmt2.close();
                conn.close();
            } catch (SQLException ex) {
            }
            return resultados;
        }
    }


    private class guardarResultado extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean guardado = false;
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT COUNT(*) FROM "+TABLA_RESULTADOS+" where carrera_id="+carrera_id_param+" and (piloto_id="+piloto_id_param+" OR posicion="+posicion_param+");");

                if(req.next()) {
                    if(req.getInt(1)==0){
                        Statement stmt2 = conn.createStatement();
                        int req2 = stmt.executeUpdate("INSERT INTO "+TABLA_RESULTADOS+" (piloto_id,equipo_id,carrera_id,posicion,puntos) VALUES ("+piloto_id_param+","+equipo_id_param+","+carrera_id_param+","+posicion_param+","+puntos_param+")");
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

}
