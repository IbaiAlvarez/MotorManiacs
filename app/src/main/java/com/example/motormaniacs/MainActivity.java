package com.example.motormaniacs;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;

import com.example.motormaniacs.Controller.SQLMethods;
import com.example.motormaniacs.Fragments.SecondFragment;
import com.example.motormaniacs.Fragments.ThirdFragment;
import com.example.motormaniacs.Fragments.firstFragmetn;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Resultado;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    firstFragmetn firstFragment = new firstFragmetn();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();

    private static final int id1 = R.id.firstFragment;

    private static final int id2 = R.id.firstFragment;
    private static final int id3 = R.id.firstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navegacion);


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
        sqlQuery.obtenerResultadosCarrera(1);
    }
    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        int itemId = item.getItemId();

        if (itemId == id1) {
            loadFragment(firstFragment);

            return true;
        } else if (itemId == id2) {
            loadFragment(secondFragment);

            return true;
        } else if (itemId == id3) {
            loadFragment(thirdFragment);
            return true;
        }
        return false;
    };
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}