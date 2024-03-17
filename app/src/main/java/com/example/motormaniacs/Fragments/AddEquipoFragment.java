package com.example.motormaniacs.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.motormaniacs.Model.Daos.PilotoDao;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.R;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEquipoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEquipoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddEquipoFragment() {
        // Required empty public constructor
    }
    public AddEquipoFragment(FragmentManager fm, ArrayList<Equipo> equipos) {
        this.fm = fm;
        this.equipos = equipos;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEquipoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEquipoFragment newInstance(String param1, String param2) {
        AddEquipoFragment fragment = new AddEquipoFragment();
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
    TextView txt_nombre_equipo;
    Spinner spinner_estado_equipo;
    Button btn_guardar_equipo;
    ImageView img_atras_addequipo;
    EquipoDao eDao = new EquipoDao();
    ReturnMethods returnMethods = new ReturnMethods();
    ArrayList<Equipo> equipos;
    ArrayList<String> estados = new ArrayList<String>();
    ArrayAdapter<String> estado_adapter;
    String selected_estado = "retirado";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_equipo, container, false);


        txt_nombre_equipo = rootView.findViewById(R.id.txt_nombre_equipo);
        img_atras_addequipo = rootView.findViewById(R.id.img_atras_addequipo);
        btn_guardar_equipo = rootView.findViewById(R.id.btn_guardar_equipo);
        spinner_estado_equipo = rootView.findViewById(R.id.spinner_estado_equipo);

        estados.add("retirado");
        estados.add("compitiendo");

        //Spinner Estados
        estado_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, estados);
        estado_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estado_equipo.setAdapter(estado_adapter);
        spinner_estado_equipo.setSelection(0);
        spinner_estado_equipo.setEnabled(false);


        txt_nombre_equipo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txt_nombre_equipo.getText().toString().equals("")){
                    spinner_estado_equipo.setEnabled(true);
                }else{
                    spinner_estado_equipo.setEnabled(false);
                    spinner_estado_equipo.setSelection(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        spinner_estado_equipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_estado = estados.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_guardar_equipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_nombre_equipo.getText().toString().equals("")){
                    String nombre_equipo = txt_nombre_equipo.getText().toString();
                    if(!eDao.comprobarEquipoExiste(nombre_equipo)){
                        equipos = eDao.añadirEquipo(nombre_equipo,equipos,selected_estado);
                        Toast.makeText(getActivity(), "El equipo ha sido guardado.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "El equipo ya existe.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Datos invalidos.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_atras_addequipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
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