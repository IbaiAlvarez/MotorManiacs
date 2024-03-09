package com.example.motormaniacs.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.motormaniacs.Model.Daos.PilotoDao;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.R;

import java.util.ArrayList;

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
    ArrayList<Piloto> pilotos = new ArrayList<Piloto>();
    PilotoDao pDao = new PilotoDao();

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

        pilotos = pDao.cargarPilotos();

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

        return rootView;
    }
}