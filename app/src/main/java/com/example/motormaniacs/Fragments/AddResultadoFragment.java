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
import android.widget.TextView;
import android.widget.Toast;

import com.example.motormaniacs.Controller.ReturnMethods;
import com.example.motormaniacs.Model.Daos.CarreraDao;
import com.example.motormaniacs.Model.Daos.ResultadoDao;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddResultadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddResultadoFragment extends Fragment {

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
    public AddResultadoFragment() {
        // Required empty public constructor
    }
    public AddResultadoFragment(FragmentManager fm, ArrayList<Piloto> pilotos) {
        this.fm = fm;
        this.pilotos = pilotos;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddResultadoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddResultadoFragment newInstance(String param1, String param2) {
        AddResultadoFragment fragment = new AddResultadoFragment();
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


    Spinner spinner_fecha_resultado;
    Spinner spinner_piloto_resultado;
    TextView txt_posicion;
    ArrayAdapter<String> fechas_adapter;
    ArrayAdapter<String> pilotos_adapter;
    ArrayList<String> fechas_carreras = new ArrayList<String>();
    CarreraDao cDao = new CarreraDao();
    String selectedFecha = "";
    String selectedPiloto = "";
    boolean primeraCarga = true;
    Button btn_guardar_resultado;
    ArrayList<String> nombres_pilotos;
    ReturnMethods returnMethods = new ReturnMethods();
    Piloto p = null;
    ResultadoDao rDao = new ResultadoDao();
    ImageView img_atras_carrera;
    int id_carrera= -1;
    ArrayList<Integer> id_pilotos_carrera = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_resultado, container, false);

        spinner_fecha_resultado = rootView.findViewById(R.id.spinner_fecha_resultado);
        spinner_piloto_resultado = rootView.findViewById(R.id.spinner_piloto_resultado);
        btn_guardar_resultado = rootView.findViewById(R.id.btn_guardar_resultado);
        txt_posicion = rootView.findViewById(R.id.txt_posicion);
        img_atras_carrera = rootView.findViewById(R.id.img_atras_carrera);

        if(primeraCarga) {
            fechas_carreras = cDao.consultarFechasIncompeltas();
            fechas_carreras.add("Selecciona Fecha");

            nombres_pilotos = returnMethods.devolverPilotosActivos(pilotos);
            nombres_pilotos.add("Seleccione Piloto");
            primeraCarga=false;
        }

        //Spinner Fechas
        fechas_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, fechas_carreras);
        fechas_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fecha_resultado.setAdapter(fechas_adapter);
        spinner_fecha_resultado.setSelection(fechas_carreras.size()-1);

        //Spinner Pilotos
        pilotos_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombres_pilotos);
        pilotos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_piloto_resultado.setAdapter(pilotos_adapter);
        spinner_piloto_resultado.setSelection(nombres_pilotos.size()-1);


        spinner_fecha_resultado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFecha = fechas_carreras.get(position);

                if(!selectedFecha.equals("Selecciona Fecha")){
                    id_carrera = cDao.consultarIdCarrera(selectedFecha);
                    id_pilotos_carrera = cDao.consultarPilotosCarrera(id_carrera);
                    nombres_pilotos = returnMethods.devolverPilotosActivos(pilotos);

                    Collections.sort(nombres_pilotos);

                    for(int i = 0;i<id_pilotos_carrera.size();i++){
                        int id_piloto_prob =id_pilotos_carrera.get(i);
                        Optional<Piloto> pilotoEncontrado = pilotos.stream().filter(pilot -> pilot.getId() == id_piloto_prob).findFirst();

                        if (pilotoEncontrado.isPresent()) {
                            Piloto p_prob = pilotoEncontrado.get();
                            String nombre_completo = p_prob.getNombre() + " " + p_prob.getApellido();
                            nombres_pilotos.remove(nombre_completo);
                        }
                    }
                    ActualizarAdaptadorPilotos();

                }else{
                    primeraCarga = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_piloto_resultado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        btn_guardar_resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedFecha.equals("Selecciona Fecha") && !selectedPiloto.equals("Seleccione Piloto") && !txt_posicion.getText().toString().equals("")){

                    int posicion = Integer.parseInt(txt_posicion.getText().toString());
                    int puntos = returnMethods.devolverPuntos(posicion);
                    if(rDao.insertarResultado(p.getId(), p.getId_equipo(),id_carrera,posicion,puntos)){
                        Toast.makeText(getActivity(), "Resultado guardado.", Toast.LENGTH_SHORT).show();
                        nombres_pilotos.remove(p.getNombre()+ " "+ p.getApellido());
                        ActualizarAdaptadorPilotos();

                    }else{
                        Toast.makeText(getActivity(), "El piloto o la posicion ya han sido guardados en esta carrera.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_atras_carrera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btn_guardar_resultado.setEnabled(false);
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

    public void ActualizarAdaptadorPilotos(){
        // Crear un nuevo adaptador con la lista actualizada de nombres de pilotos
        ArrayAdapter<String> nuevo_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombres_pilotos);
        nuevo_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el nuevo adaptador en el Spinner
        spinner_piloto_resultado.setAdapter(nuevo_adapter);
    }
}