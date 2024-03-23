package com.example.motormaniacs.Model.Daos;

import android.os.AsyncTask;

import com.example.motormaniacs.Model.Conexion.ConexionDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

public class UsuarioDao  extends Thread{

    private static final String url = ConexionDatos.getUrl();
    private static final String user = ConexionDatos.getUser();
    private static final String password = ConexionDatos.getPassword();
    private static final String TABLA_USUARIOS = ConexionDatos.getTABLA_USUARIOS();
    public static String  nombre_usuario_param = "";
    public static String  apellido_usuario_param = "";
    public static String  tipo_usuario_param = "";
    public static String  nick_usuario_param = "";
    public static String  contraseña_usuario_param = "";


    public boolean verificarUsuario(String nick, String contraseña) {
        try {
            this.contraseña_usuario_param = contraseña;
            this.nick_usuario_param = nick;
            return new validarUsuario().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarUsuario(String nombre,String apellido, String  tipo, String nick) {
        try {
            this.nombre_usuario_param  = nombre;
            this.apellido_usuario_param = apellido;
            this.tipo_usuario_param = tipo;
            this.nick_usuario_param = nick;
            return new cambiarUsuario().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(String nick) {
        try {
            this.nick_usuario_param = nick;
            return new borrarUsuario().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    private class cambiarUsuario extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean cambiado = false;
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT COUNT(*) FROM "+TABLA_USUARIOS+" where usuario_nick='"+nick_usuario_param+"';");

                if(req.next()) {
                    if(req.getInt(1)==0){
                        Statement stmt2 = conn.createStatement();
                        int req2 = stmt2.executeUpdate("UPDATE "+TABLA_USUARIOS+" SET Nombre = '"+nombre_usuario_param+"', Apellido='"+apellido_usuario_param+"', tipo = '"+tipo_usuario_param+"' WHERE usuario_nick = "+nick_usuario_param+";");
                        cambiado = true;
                    }
                }
                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return cambiado;
        }
    }

    private class validarUsuario extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean valido = false;
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet req = stmt.executeQuery("SELECT COUNT(*) FROM "+TABLA_USUARIOS+" where usuario_nick='"+nick_usuario_param+"' and contraseña='"+contraseña_usuario_param+"';");

                if(req.next()) {
                    if(req.getInt(1)==1){
                        valido = true;
                    }
                }
                req.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return valido;
        }
    }

    private class borrarUsuario extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean cambiado = false;
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                int req = stmt.executeUpdate("DELETE FROM "+TABLA_USUARIOS+" WHERE usuario_nick = "+nick_usuario_param+";");
                cambiado = true;

                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return cambiado;
        }
    }

}
