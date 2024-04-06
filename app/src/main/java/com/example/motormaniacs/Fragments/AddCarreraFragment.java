package com.example.motormaniacs.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motormaniacs.Model.Carrera;
import com.example.motormaniacs.Model.Daos.CarreraDao;
import com.example.motormaniacs.R;

import java.security.MessageDigestSpi;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCarreraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCarreraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCarreraFragment() {
        // Required empty public constructor
    }

    public AddCarreraFragment(FragmentManager fm) {
        this.fm = fm;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCarreraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCarreraFragment newInstance(String param1, String param2) {
        AddCarreraFragment fragment = new AddCarreraFragment();
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
    Carrera c = new Carrera();
    CarreraDao cDao = new CarreraDao();
    TextView txt_circuito;
    TextView txt_fecha;
    Button btn_guardar_carrera;
    ImageView img_calendario;
    ImageView img_atras_carrera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_carrera, container, false);

        txt_circuito = rootView.findViewById(R.id.txt_circuito);
        txt_fecha = rootView.findViewById(R.id.txt_fecha);
        btn_guardar_carrera = rootView.findViewById(R.id.btn_guardar_carrera);
        img_calendario = rootView.findViewById(R.id.img_calendario);
        img_atras_carrera = rootView.findViewById(R.id.img_atras_carrera);

        txt_fecha.setEnabled(false);

        img_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txt_fecha.setText(MessageFormat.format("{0}-{1}-{2}",String.valueOf(year),String.format("%02d", (month + 1)),String.format("%02d", (dayOfMonth))));
                    }
                },year,month,day);
                dialog.show();
            }
        });

        btn_guardar_carrera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_circuito.getText().toString().equals("") && !txt_fecha.getText().toString().equals("")){
                    String circuito = txt_circuito.getText().toString();
                    String fecha = txt_fecha.getText().toString();
                    if(!cDao.consultarFechaLibre(fecha)){
                        cDao.insertarCarrera(circuito,fecha);
                        Toast.makeText(getActivity(), "Carrera guardada.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Ya existe una carrera en esta fecha.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Datos invalidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_atras_carrera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btn_guardar_carrera.setEnabled(false);
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