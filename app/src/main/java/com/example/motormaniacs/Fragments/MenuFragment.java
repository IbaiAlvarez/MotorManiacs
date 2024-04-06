package com.example.motormaniacs.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.motormaniacs.Activity.MainActivity;
import com.example.motormaniacs.Model.Daos.EquipoDao;
import com.example.motormaniacs.Model.Daos.PilotoDao;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    public MenuFragment(FragmentManager fm) {
        // Required empty public constructor+
        this.fm = fm;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFratment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentManager fm;
    Fragment fragment;
    Button btn_añadir_piloto;
    Button btn_añadir_equipo;
    Button btn_añadir_premio;
    Button btn_añadir_resultado;
    Button btn_añadir_carrera;
    Button btn_editar_piloto;
    Button btn_editar_equipo;
    ImageView img_atras_menu;
    ArrayList<Piloto> pilotos = new ArrayList<Piloto>();
    ArrayList<Equipo> equipos = new ArrayList<Equipo>();
    PilotoDao pDao = new PilotoDao();
    EquipoDao eDao = new EquipoDao();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        btn_añadir_piloto= rootView.findViewById(R.id.btn_añadir_piloto);
        btn_añadir_equipo= rootView.findViewById(R.id.btn_añadir_equipo);
        btn_añadir_premio= rootView.findViewById(R.id.btn_añadir_premio);
        btn_añadir_resultado= rootView.findViewById(R.id.btn_añadir_resultado);
        btn_añadir_carrera= rootView.findViewById(R.id.btn_añadir_carrera);
        btn_editar_piloto= rootView.findViewById(R.id.btn_editar_piloto);
        btn_editar_equipo= rootView.findViewById(R.id.btn_editar_equipo);
        img_atras_menu = rootView.findViewById(R.id.img_atras_menu);

        // Crear una instancia de tu clase PilotoDao
        PilotoDao pilotoDao = new PilotoDao("obtenerPilotos");

        // Crear un ExecutorService con un solo hilo
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Enviar la tarea al ExecutorService
        Future<Object> future = executor.submit(pilotoDao);

        try {
            // Obtener el resultado
            pilotos = (ArrayList<Piloto>) future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // Apagar el ExecutorService cuando ya no se necesite
        executor.shutdown();

        //pilotos = pDao.cargarPilotos();
        equipos = eDao.cargarEquipos();


        btn_añadir_piloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment = new AddPilotoFragment(fm,pilotos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                fm.beginTransaction().replace(R.id.fragmentContainerAdmins, fragment).commit();
            }
        });

        btn_editar_piloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment = new EditPilotoFragment(fm,pilotos,equipos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                fm.beginTransaction().replace(R.id.fragmentContainerAdmins, fragment).commit();
            }
        });

        btn_añadir_equipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment = new AddEquipoFragment(fm,equipos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                fm.beginTransaction().replace(R.id.fragmentContainerAdmins, fragment).commit();
            }
        });

        btn_editar_equipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment = new EditEquipoFragment(fm,equipos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                fm.beginTransaction().replace(R.id.fragmentContainerAdmins, fragment).commit();
            }
        });

        btn_añadir_carrera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment = new AddCarreraFragment(fm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                fm.beginTransaction().replace(R.id.fragmentContainerAdmins, fragment).commit();
            }
        });

        //Boton para volver atras
        img_atras_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        return rootView;
    }
}