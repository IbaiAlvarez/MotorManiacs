package com.example.motormaniacs.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.motormaniacs.Controller.ReturnMethods;
import com.example.motormaniacs.Model.Daos.PremioDao;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPremioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPremioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentManager fm;
    Fragment fragment;
    ArrayList<Piloto> pilotos;
    public AddPremioFragment() {
        // Required empty public constructor
    }
    public AddPremioFragment(FragmentManager fm, ArrayList<Piloto> pilotos) {
        this.fm = fm;
        this.pilotos = pilotos;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPremioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPremioFragment newInstance(String param1, String param2) {
        AddPremioFragment fragment = new AddPremioFragment();
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

    Spinner spinner_premio;
    Spinner spinner_piloto_premio;
    ArrayAdapter<String> premio_adapter;
    ArrayAdapter<String> pilotos_adapter;
    String selectedPremio = "";
    String selectedPiloto = "";
    boolean primeraCarga = true;
    Button btn_guardar_premio;
    ArrayList<String> nombres_pilotos;
    ArrayList<String> premios;
    ReturnMethods returnMethods = new ReturnMethods();
    Piloto p = null;
    PremioDao pDao = new PremioDao();
    ImageView img_atras_premio;
    int id_carrera= -1;
    ArrayList<Integer> id_pilotos_carrera = new ArrayList<Integer>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_premio, container, false);

        spinner_premio = rootView.findViewById(R.id.spinner_fecha_premio);
        spinner_piloto_premio = rootView.findViewById(R.id.spinner_piloto_premio);
        btn_guardar_premio = rootView.findViewById(R.id.btn_guardar_premio);
        img_atras_premio = rootView.findViewById(R.id.img_atras_premio);

        if(primeraCarga) {
            premios = returnMethods.cargarPremios();
            premios.add("Seleccione Premio");

            nombres_pilotos = returnMethods.devolverPilotosActivos(pilotos);
            nombres_pilotos.add("Seleccione Piloto");
            primeraCarga=false;
        }

        //Spinner Fechas
        premio_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, premios);
        premio_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_premio.setAdapter(premio_adapter);
        spinner_premio.setSelection(premios.size()-1);

        //Spinner Pilotos
        pilotos_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombres_pilotos);
        pilotos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_piloto_premio.setAdapter(pilotos_adapter);
        spinner_piloto_premio.setSelection(nombres_pilotos.size()-1);


        spinner_premio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPremio = premios.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spinner_piloto_premio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedPiloto = nombres_pilotos.get(position);
                if(!selectedPiloto.equals("Seleccione Piloto")) {
                    //Busca el piloto en la lista
                    Optional<Piloto> p_opt = pilotos.stream().filter(x -> selectedPiloto.equals(x.getNombre() + " " + x.getApellido())).findFirst();
                    if (p_opt.isPresent()) {
                        p = p_opt.get();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });


        btn_guardar_premio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedPremio.equals("Seleccione Premio") && !selectedPiloto.equals("Seleccione Piloto")){

                    if(pDao.insertarPremio(selectedPremio,p.getId(), p.getId_equipo())){
                        Toast.makeText(getActivity(), "Resultado guardado.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Error al guardar el premio..", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_atras_premio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btn_guardar_premio.setEnabled(false);
                    fragment = new MenuFragment(fm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                fm.beginTransaction().replace(R.id.fragmentContainerAdmins, fragment).commit();
            }
        });

        return rootView;
    }
}