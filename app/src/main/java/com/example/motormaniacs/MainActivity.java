package com.example.motormaniacs;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.example.motormaniacs.Controller.SQLMethods;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Resultado;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.enableDefaults();
        Equipo e= new Equipo();

        SQLMethods sqlQuery = new SQLMethods();
        /*e = sqlQuery.cargarEquipoNombre("Hendrick Motorsports");
        sqlQuery.añadirPiloto("pepe","perez");
        ArrayList<String> equipos =  sqlQuery.obtenerListaEquipos();
        ArrayList<Resultado> resultados = sqlQuery.cargarResultadosUltimaCarrera();
        sqlQuery.añadirPremio(1,"campeonato","temporada 1");
        sqlQuery.InsertarUsuario("PEPE","Perez","pepe","contra","admin");*/
        sqlQuery.cargarCarrera(1);
    }
}