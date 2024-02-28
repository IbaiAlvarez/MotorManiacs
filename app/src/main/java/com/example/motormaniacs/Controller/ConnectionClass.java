package com.example.motormaniacs.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    private final String userMySQL = "administrador";
    private final String passwordMySQL = "Elorrieta00";
    private final String host = "10.5.13.28";
    private final String port = "3306";
    private final String dbName = "motormaniacs";
    private final String urlGetDBParameters = "autoReconnect=true&useSSL=false";
    private final String strConnectionMySQLLocal = 	"jdbc:mysql://"+host+":"+port+"/"+dbName+"?"+urlGetDBParameters;
    private final String driverClassName = "com.mysql.jdbc.Driver";

    private Connection connection;

    public void CrearConexionMySQL() throws Exception {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(strConnectionMySQLLocal, userMySQL, passwordMySQL);

        if (connection == null){
            String message = String.format("No se pudo establecer una conexión con la DB: %s", dbName);
            throw new Exception(message);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
