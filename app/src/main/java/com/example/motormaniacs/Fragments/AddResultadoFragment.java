package com.example.motormaniacs.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.motormaniacs.Model.Daos.CarreraDao;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.R;

import java.util.ArrayList;

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
    TextView txt_circuito_carrera;
    ArrayAdapter<String> fechas_adapter;
    ArrayList<String> fechas_carreras = new ArrayList<String>();
    CarreraDao cDao = new CarreraDao();
    String selectedFecha = "";
    boolean primeraCarga = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_piloto, container, false);


        fechas_carreras = cDao.consultarFechas();
        fechas_carreras.add("Selecciona Fecha");

        //Spinner Fechas
        fechas_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, fechas_carreras);
        fechas_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fecha_resultado.setAdapter(fechas_adapter);
        spinner_fecha_resultado.setSelection(fechas_carreras.size()-1);



        spinner_fecha_resultado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFecha = fechas_carreras.get(position);

                if(selectedFecha.equals("Selecciona Fecha")){


                }else{
                    primeraCarga = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return rootView;
    }
}