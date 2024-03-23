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
import com.example.motormaniacs.Model.Daos.EquipoDao;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.R;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditEquipoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditEquipoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditEquipoFragment() {
        // Required empty public constructor
    }

    public EditEquipoFragment(FragmentManager fm, ArrayList<Equipo> equipos) {
        this.fm = fm;
        this.equipos = equipos;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditEquipoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditEquipoFragment newInstance(String param1, String param2) {
        EditEquipoFragment fragment = new EditEquipoFragment();
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
    Spinner spinner_equipo_edit;
    Spinner spinner_estado_edit;
    Button btn_guardar_equipo_edit;
    ImageView img_atras_editequipo;
    TextView txt_nombre_equipo_edit;
    EquipoDao eDao = new EquipoDao();
    ReturnMethods returnMethods = new ReturnMethods();
    ArrayList<Equipo> equipos;
    ArrayList<String> estados = new ArrayList<String>();
    ArrayAdapter<String> estado_adapter;
    ArrayAdapter<String> equipos_adapter;
    String selected_estado = "retirado";
    ArrayList<String> nombres_equipos;
    String selectedEquipo;
    String selectedEstado;
    Equipo e_anterior = new Equipo();
    Equipo e = new Equipo();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_equipo, container, false);


        spinner_equipo_edit = rootView.findViewById(R.id.spinner_equipo_edit);
        spinner_estado_edit = rootView.findViewById(R.id.spinner_estado_edit);
        btn_guardar_equipo_edit = rootView.findViewById(R.id.btn_guardar_carrera);
        img_atras_editequipo = rootView.findViewById(R.id.img_atras_carrera);
        txt_nombre_equipo_edit = rootView.findViewById(R.id.txt_circuito);

        estados.add("compitiendo");
        estados.add("retirado");

        nombres_equipos = returnMethods.devolverNombreEquipos(equipos);
        nombres_equipos.add("Seleccione Equipo");

        //Spinner Equipos
        equipos_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombres_equipos);
        equipos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_equipo_edit.setAdapter(equipos_adapter);
        spinner_equipo_edit.setSelection(nombres_equipos.size()-1);

        //Spinner Estados
        estado_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, estados);
        estado_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estado_edit.setAdapter(estado_adapter);
        spinner_estado_edit.setSelection(0);
        spinner_estado_edit.setEnabled(false);

        txt_nombre_equipo_edit.setEnabled(false);

        spinner_equipo_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEquipo = nombres_equipos.get(position);

                if(!selectedEquipo.equals("Seleccione Equipo")) {
                    Optional<Equipo> e_opt = equipos.stream().filter(x -> selectedEquipo.equals(x.getNombre())).findFirst();
                    if (e_opt.isPresent()) {
                        e = e_opt.get();
                        e_anterior = e;
                        if (e.getEstado().equals("compitiendo")) {
                            spinner_estado_edit.setSelection(0);
                        } else {
                            spinner_estado_edit.setSelection(1);
                        }
                        spinner_estado_edit.setEnabled(true);
                        txt_nombre_equipo_edit.setEnabled(true);
                        txt_nombre_equipo_edit.setText(e.getNombre());
                    }
                }else{
                    int index_p = equipos.indexOf(e_anterior);
                    if(index_p!=-1) {
                        spinner_equipo_edit.setSelection(index_p);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_estado_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEstado = estados.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_guardar_equipo_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedEquipo.equals("Seleccione Piloto") && !txt_nombre_equipo_edit.getText().toString().equals("")) {
                    String nombre = txt_nombre_equipo_edit.getText().toString();
                    if(!eDao.comprobarEquipoExiste(nombre)) {
                        e.setNombre(txt_nombre_equipo_edit.getText().toString());
                        e.setEstado(selectedEstado);
                        equipos = eDao.editarEquipo(e.getId(), e.getNombre(), e.getEstado(), equipos);
                        Toast.makeText(getActivity(), "Equipo guardado.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Ya existe un equipo con este nombre.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Datos invalidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_atras_editequipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btn_guardar_equipo_edit.setEnabled(false);
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