package com.example.motormaniacs.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motormaniacs.Controller.ReturnMethods;
import com.example.motormaniacs.Model.Daos.PilotoDao;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPilotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPilotoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPilotoFragment() {
        // Required empty public constructor
    }

    public AddPilotoFragment(FragmentManager fm, ArrayList<Piloto> pilotos) {
        this.fm = fm;
        this.pilotos = pilotos;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPilotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPilotoFragment newInstance(String param1, String param2) {
        AddPilotoFragment fragment = new AddPilotoFragment();
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
    TextView txt_nombre;
    TextView txt_apellido;
    TextView txt_numero;
    Button btn_guardar_piloto;
    ImageView img_atras_addpiloto;
    PilotoDao pDao = new PilotoDao();
    ReturnMethods returnMethods = new ReturnMethods();
    ArrayList<Piloto> pilotos;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_piloto, container, false);

        txt_nombre = rootView.findViewById(R.id.txt_nombre);
        txt_apellido = rootView.findViewById(R.id.txt_apellido);
        txt_numero = rootView.findViewById(R.id.txt_numero);
        btn_guardar_piloto  = rootView.findViewById(R.id.btn_guardar_piloto);
        img_atras_addpiloto= rootView.findViewById(R.id.img_atras_addpiloto);

        btn_guardar_piloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_nombre.getText().toString().equals("") && !txt_apellido.getText().toString().equals("") && !txt_numero.getText().toString().equals("")){
                    String nombre = txt_nombre.getText().toString();
                    String apellido = txt_apellido.getText().toString();
                    String numero = txt_numero.getText().toString();

                    int validacion = returnMethods.comprobarPiloto(nombre,apellido,Integer.valueOf(numero),pilotos);

                    if(validacion==-1){
                        pilotos = pDao.añadirPiloto(nombre,apellido,Integer.valueOf(numero),pilotos);
                        Toast.makeText(getActivity(), "Piloto guardado.",Toast.LENGTH_SHORT).show();
                    }else if(validacion==1){
                        Toast.makeText(getActivity(), "El piloto ya existe.",Toast.LENGTH_SHORT).show();
                    }else if(validacion==2){
                        Toast.makeText(getActivity(), "El numero seleccionado lo está utilizando otro corredor.",Toast.LENGTH_SHORT).show();
                    }
                                    }else{
                    Toast.makeText(getActivity(), "Datos invalidos.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_atras_addpiloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btn_guardar_piloto.setEnabled(false);
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