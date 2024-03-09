package com.example.motormaniacs.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.motormaniacs.Fragments.SecondFragment;
import com.example.motormaniacs.Fragments.UsuarioFragment;
import com.example.motormaniacs.Fragments.ResultadoCarrerasFragment;
import com.example.motormaniacs.Model.Daos.CarreraDao;
import com.example.motormaniacs.Model.Daos.ResultadoDao;
import com.example.motormaniacs.Model.Resultado;
import com.example.motormaniacs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends AppCompatActivity {

    ResultadoCarrerasFragment fragmentCarreras = new ResultadoCarrerasFragment();
    SecondFragment secondFragment = new SecondFragment();
    UsuarioFragment thirdFragment = new UsuarioFragment();

    private static final int carrerasFragment = R.id.btn_carreras;

    private static final int puntuacionesFragment = R.id.btn_ountuaciones;
    private static final int usuarios = R.id.btn_usuarios;
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment;

    //SharedPreferences
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String RESULTADOS_ULTIMA_CARRERA = "resultados_ultima_carrera";
    final Gson gson = new Gson();
    private ResultadoDao rDao = new ResultadoDao();
    private CarreraDao cDao = new CarreraDao();
    ArrayList<Resultado> resultados_ultima_carrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navegacion);

        fragment = fm.findFragmentById(R.id.fragment_container);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment fragment_nuevo= new ResultadoCarrerasFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment_nuevo);
        fragmentTransaction.commit();

        //Shared Preferences Recibir Datos
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String json = sharedpreferences.getString(RESULTADOS_ULTIMA_CARRERA, "");
        Type type = new TypeToken<ArrayList<Resultado>>(){}.getType();
        resultados_ultima_carrera = gson.fromJson(json, type);

        //Shared Pref obtiene y guarda los resultados ultima carrera
        int ultima_carrera_id = cDao.obtenerUltimaCarreraId();

        if(resultados_ultima_carrera==null || resultados_ultima_carrera.size()==0 || ultima_carrera_id!=resultados_ultima_carrera.get(0).getCarreraId() || ultima_carrera_id!=-1){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            resultados_ultima_carrera = rDao.cargarResultadosUltimaCarrera();
            String json_guardar = gson.toJson(resultados_ultima_carrera);
            editor.putString(RESULTADOS_ULTIMA_CARRERA, json_guardar);

            editor.apply();
        }


        navigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            fragment = fm.findFragmentById(R.id.fragment_container);
            FragmentTransaction fragmentTransaction_2 = fm.beginTransaction();

            Fragment fragment_nuevo_2= new ResultadoCarrerasFragment();
            if (itemId == carrerasFragment) {
                fragment_nuevo_2 = new ResultadoCarrerasFragment();
            } else if (itemId == puntuacionesFragment) {
                fragment_nuevo_2 = new SecondFragment();

            } else if (itemId == usuarios) {
                fragment_nuevo_2 = new UsuarioFragment();
            }
            fragmentTransaction_2.replace(R.id.fragment_container, fragment_nuevo_2);
            fragmentTransaction_2.commit();

            return true;
        });


        StrictMode.enableDefaults();

    }
}