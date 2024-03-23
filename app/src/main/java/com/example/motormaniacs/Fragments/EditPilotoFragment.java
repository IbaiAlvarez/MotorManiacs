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
import com.example.motormaniacs.Model.Daos.EquipoDao;
import com.example.motormaniacs.Model.Daos.PilotoDao;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPilotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPilotoFragment extends Fragment {

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
    ArrayList<Equipo> equipos;

    public EditPilotoFragment() {
        // Required empty public constructor
    }
    public EditPilotoFragment(FragmentManager fm, ArrayList<Piloto> pilotos, ArrayList<Equipo> equipos) {
        this.fm = fm;
        this.pilotos = pilotos;
        this.equipos = equipos;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPilotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPilotoFragment newInstance(String param1, String param2) {
        EditPilotoFragment fragment = new EditPilotoFragment();
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
    Button btn_edit_piloto;
    ImageView img_atras_editpiloto;
    PilotoDao pDao = new PilotoDao();
    EquipoDao eDao = new EquipoDao();
    ReturnMethods returnMethods = new ReturnMethods();
    Spinner spinner_piloto;
    Spinner spinner_equipo;
    Spinner spinner_estado;
    ArrayAdapter<String> pilotos_adapter;
    ArrayAdapter<String> equipos_adapter;
    ArrayAdapter<String> estado_adapter;
    ArrayList<String> nombres_pilotos;
    ArrayList<String> nombres_equipos;
    ArrayList<String> estados = new ArrayList<String>();
    Piloto p = new Piloto();
    Equipo e = new Equipo();
    Piloto p_anterior = new Piloto();
    Equipo e_anterior = new Equipo();
    String selectedPiloto;
    String selectedEquipo;
    String selectedEstado;
    String selectedEstado_anterior = "";
    boolean carga = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_piloto, container, false);

        spinner_piloto = rootView.findViewById(R.id.spinner_piloto);
        spinner_equipo = rootView.findViewById(R.id.spinner_equipo);
        spinner_estado = rootView.findViewById(R.id.spinner_estado);
        img_atras_editpiloto= rootView.findViewById(R.id.img_atras_editpiloto);
        btn_edit_piloto = rootView.findViewById(R.id.btn_edit_piloto);

        nombres_pilotos = returnMethods.devolverNombrePiltos(pilotos);
        nombres_pilotos.add("Seleccione Piloto");
        estados.add("Seleccione Piloto");
        estados.add("compitiendo");
        estados.add("retirado");
        nombres_equipos = returnMethods.devolverNombreEquipos(equipos);
        nombres_equipos.add("Sin Equipo");
        nombres_equipos.add("Seleccione Piloto");

        //Spinner Pilotos
        pilotos_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombres_pilotos);
        pilotos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_piloto.setAdapter(pilotos_adapter);
        spinner_piloto.setSelection(nombres_pilotos.size()-1);

        //Spinner Estados
        estado_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, estados);
        estado_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estado.setAdapter(estado_adapter);
        spinner_estado.setSelection(0);
        spinner_estado.setEnabled(false);

        //Spinner Equipos
        equipos_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombres_equipos);
        equipos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_equipo.setAdapter(equipos_adapter);
        spinner_equipo.setSelection(nombres_equipos.size()-1);
        spinner_equipo.setEnabled(false);

        spinner_piloto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedPiloto = nombres_pilotos.get(position);


                if(!selectedPiloto.equals("Seleccione Piloto")) {
                    //Busca el piloto en la lista
                    Optional<Piloto> p_opt = pilotos.stream().filter(x -> selectedPiloto.equals(x.getNombre() + " " + x.getApellido())).findFirst();
                    if (p_opt.isPresent()) {
                        p = p_opt.get();
                        p_anterior = p;
                        if (p.getEstado().equals("compitiendo")) {
                            spinner_estado.setSelection(1);
                        } else {
                            spinner_estado.setSelection(2);
                        }
                        spinner_estado.setEnabled(true);
                        spinner_equipo.setEnabled(true);
                    }
                }else {
                    int index_p = pilotos.indexOf(p_anterior);
                    if(index_p!=-1) {
                        spinner_piloto.setSelection(index_p);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedEstado = estados.get(position);

                if(!selectedEstado.equals("Seleccione Piloto")) {
                    selectedEstado_anterior = selectedEstado;
                    if (selectedEstado.equals("retirado")) {
                        spinner_equipo.setEnabled(false);
                        spinner_equipo.setSelection(nombres_equipos.size()-2);
                    }else {
                        spinner_equipo.setEnabled(true);
                        //Busca el equipo en la lista
                        Optional<Equipo> e_opt = equipos.stream().filter(x -> p.getId_equipo() == x.getId()).findFirst();
                        if (e_opt.isPresent()) {
                            e = e_opt.get();
                            e_anterior = e;
                            int index_e = equipos.indexOf(e);

                            spinner_equipo.setSelection(index_e);
                        }else{
                            spinner_equipo.setSelection(nombres_equipos.size()-2);
                        }
                    }
                }else{
                    int index_e = estados.indexOf(selectedEstado_anterior);
                    spinner_estado.setSelection(index_e);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinner_equipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedEquipo = nombres_equipos.get(position);

                if(!selectedEquipo.equals("Seleccione Piloto")){
                    //Busca el equipo en la lista
                    Optional<Equipo> e_opt = equipos.stream().filter(x -> x.getNombre().equals(selectedEquipo)).findFirst();
                    if (e_opt.isPresent()) {
                        e = e_opt.get();
                        e_anterior = e;
                    }
                }else{
                    int index_e = equipos.indexOf(e_anterior);
                    spinner_equipo.setSelection(index_e);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        btn_edit_piloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedPiloto.equals("Seleccione Piloto")) {
                    if (selectedEstado.equals("compitiendo") && selectedEquipo.equals("Sin Equipo")) {
                        pDao.editarPiloto(pilotos, p.getId(), -1, "compitiendo", p.getNumero());
                    } else if (selectedEstado.equals("compitiendo")) {
                        pDao.editarPiloto(pilotos, p.getId(), e.getId(), "compitiendo", p.getNumero());
                    } else {
                        pDao.editarPiloto(pilotos, p.getId(), -1, "retirado", p.getNumero());
                    }
                    pilotos = pDao.cargarPilotos();
                    equipos = eDao.cargarEquipos();
                    nombres_pilotos = returnMethods.devolverNombrePiltos(pilotos);
                    nombres_pilotos.add("Seleccione Piloto");
                    nombres_equipos = returnMethods.devolverNombreEquipos(equipos);
                    nombres_equipos.add("Sin Equipo");
                    nombres_equipos.add("Seleccione Piloto");
                    Toast.makeText(getActivity(), "Piloto editado.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Datos invalidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Boton para volver atras
        img_atras_editpiloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btn_edit_piloto.setEnabled(false);
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