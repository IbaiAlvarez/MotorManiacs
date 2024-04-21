package com.example.motormaniacs.Model.Daos;

import android.media.audiofx.DynamicsProcessing;
import android.os.AsyncTask;

import com.example.motormaniacs.Model.Conexion.ConexionDatos;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.Model.Premio;
import com.example.motormaniacs.Model.Resultado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class PremioDao  extends Thread{

    private final String url = ConexionDatos.getUrl();
    private final String user = ConexionDatos.getUser();
    private final String password = ConexionDatos.getPassword();
    private final String TABLA_EQUIPOS = ConexionDatos.getTABLA_EQUIPOS();
    private final String TABLA_PREMIOS = ConexionDatos.getTABLA_PREMIOS();
    public String  premio_param = "";
    public int  piloto_id_param = -1;
    public int  equipo_id_param = -1;
    public ArrayList<Piloto>  pilotos_param = null;
    public boolean  carga_terminada = false;


    //region Llamada metodos Select
    public ArrayList<Premio> cargarPremiosPilotos(ArrayList<Piloto>  pilotos) {
        try {
            this.pilotos_param = pilotos;
            return new obtenerPremiosPilotos().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Premio> cargarPremioLista(ArrayList<Piloto>  pilotos, String tipo_premio) {
        try {
            this.pilotos_param = pilotos;
            this.premio_param = tipo_premio;
            return new obtenerPremioLista().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region Llamada metodos Insert
    public boolean insertarPremio(String premio_nombre, int piloto_id, int equipo_id) {
        try {
            this.piloto_id_param = piloto_id;
            this.premio_param = premio_nombre;
            this.equipo_id_param = equipo_id;
            return new guardarPremio().execute().get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    //endregion

    private class obtenerPremiosPilotos extends AsyncTask<Void, Void, ArrayList<Premio>> {
        @Override
        protected ArrayList<Premio> doInBackground(Void... voids) {
            ArrayList<Premio> premios = new ArrayList<Premio>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Piloto_id, COUNT(*) AS Cantidad_de_Registros FROM Premios GROUP BY Piloto_id;");

                while(rs.next()){
                    Premio p = new Premio();
                    int id_piloto_query = rs.getInt(1);
                    Optional<Piloto> pilotoEncontrado = pilotos_param.stream().filter(piloto -> piloto.getId() == id_piloto_query).findFirst();

                    p.setPiloto(pilotoEncontrado.get());
                    p.setCantidad(rs.getInt(2));
                    premios.add(p);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return premios;
        }
    }

    private class obtenerPremioLista extends AsyncTask<Void, Void, ArrayList<Premio>> {
        @Override
        protected ArrayList<Premio> doInBackground(Void... voids) {
            ArrayList<Premio> premios = new ArrayList<Premio>();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Piloto_id, COUNT(*) AS Cantidad_de_Registros FROM Premios WHERE PREMIO = '"+premio_param+"' GROUP BY Piloto_id;");

                while(rs.next()){
                    Premio p = new Premio();
                    int id_piloto_query = rs.getInt(1);
                    Optional<Piloto> pilotoEncontrado = pilotos_param.stream().filter(piloto -> piloto.getId() == id_piloto_query).findFirst();

                    p.setPiloto(pilotoEncontrado.get());
                    p.setCantidad(rs.getInt(2));
                    premios.add(p);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return premios;
        }
    }

    private class guardarPremio extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean guardado = false;
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                int req = stmt.executeUpdate("INSERT INTO "+TABLA_PREMIOS+" (piloto_id,equipo_id,premio) VALUES ("+piloto_id_param+","+equipo_id_param+",'"+premio_param+"')");
                guardado = true;

                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return guardado;
        }
    }

}
