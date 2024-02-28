package com.example.motormaniacs;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.example.motormaniacs.Controller.SQLMethods;
import com.example.motormaniacs.Model.Resultado;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.enableDefaults();

        SQLMethods sqlQuery = new SQLMethods();
        /*sqlQuery.cargarEquipoNombre("Hendrick Motorsports");
        ArrayList<String> equipos =  sqlQuery.obtenerListaEquipos();
        ArrayList<Resultado> resultados = sqlQuery.cargarResultadosUltimaCarrera();*/
    }
}