package com.example.motormaniacs.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motormaniacs.Activity.AdminActivity;
import com.example.motormaniacs.Model.Daos.UsuarioDao;
import com.example.motormaniacs.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsuarioFragment newInstance(String param1, String param2) {
        UsuarioFragment fragment = new UsuarioFragment();
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

    UsuarioDao uDao = new UsuarioDao();
    TextView txt_usuario;
    TextView txt_contraseña;
    Button btn_logearse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_usuarios, container, false);

        txt_usuario = rootView.findViewById(R.id.txt_nombre);
        txt_contraseña = rootView.findViewById(R.id.txt_contraseña);
        btn_logearse = rootView.findViewById(R.id.btn_guardar_piloto);

        btn_logearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_usuario.getText().toString().equals("") && !txt_contraseña.getText().toString().equals("")){
                    String nick = txt_usuario.getText().toString();
                    String pass = txt_contraseña.getText().toString();
                    if(uDao.verificarUsuario(nick, pass)){
                        Intent i = new Intent(getActivity(), AdminActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getActivity(), "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Datos invalidos.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}