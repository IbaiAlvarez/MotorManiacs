package com.example.motormaniacs.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.motormaniacs.Model.Daos.CarreraDao;
import com.example.motormaniacs.Model.Daos.ResultadoDao;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Resultado;
import com.example.motormaniacs.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadoCarrerasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadoCarrerasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResultadoCarrerasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment firstFragmetn.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultadoCarrerasFragment newInstance(String param1, String param2) {
        ResultadoCarrerasFragment fragment = new ResultadoCarrerasFragment();
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

    private TableLayout tabla_carrera;
    private TableLayout tabla_header;
    ArrayList<Resultado> resultados;
    ArrayList<String> datos_carrera;
    //SharedPreferences
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String RESULTADOS_ULTIMA_CARRERA = "resultados_ultima_carrera";
    public static final String DATOS_ULTIMA_CARRERA = "datos_ultima_carrera";
    final Gson gson = new Gson();
    Spinner spinner_fecha_carrera;
    TextView txt_circuito_carrera;
    ArrayAdapter<String> fechas_adapter;
    ArrayList<String> fechas_carreras = new ArrayList<String>();
    CarreraDao cDao = new CarreraDao();
    String selectedFecha = "";
    boolean primeraCarga = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ResultadoDao rDao = new ResultadoDao();

        View rootView = inflater.inflate(R.layout.fragment_resultadocarreras, container, false);

        tabla_carrera = rootView.findViewById(R.id.tabla_carrera);
        tabla_carrera.removeAllViews();
        tabla_header = rootView.findViewById(R.id.tabla_header);
        tabla_header.removeAllViews();
        spinner_fecha_carrera = rootView.findViewById(R.id.spinner_fecha_carrera);
        txt_circuito_carrera = rootView.findViewById(R.id.txt_circuito_carrera);

        fechas_carreras = cDao.consultarFechas();

        //Spinner Fechas
        fechas_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, fechas_carreras);
        fechas_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fecha_carrera.setAdapter(fechas_adapter);
        spinner_fecha_carrera.setSelection(0);

        //Shared Preferences Recibir Datos
        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String json = sharedpreferences.getString(RESULTADOS_ULTIMA_CARRERA, "");
        Type type = new TypeToken<ArrayList<Resultado>>(){}.getType();
        resultados = gson.fromJson(json, type);

        json = sharedpreferences.getString(DATOS_ULTIMA_CARRERA, "");
        type = new TypeToken<ArrayList<String>>(){}.getType();
        datos_carrera = gson.fromJson(json, type);

        txt_circuito_carrera.setText(datos_carrera.get(1));

        //Vuelca los datos a las tablas
        cargarTabla();
        tabla_header.bringToFront();
        tabla_carrera.bringToFront();

        spinner_fecha_carrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!primeraCarga){
                    selectedFecha = fechas_carreras.get(position);

                    txt_circuito_carrera.setText(cDao.consultarCircuito(selectedFecha.toString()));
                    int id_carrera = cDao.consultarIdCarrera(selectedFecha);
                    resultados = rDao.cargarResultadosCarreraId(id_carrera);

                    tabla_carrera.removeAllViews();
                    //Vuelca los datos a las tablas
                    cargarTabla();
                    tabla_header.bringToFront();
                    tabla_carrera.bringToFront();

                }else{
                    primeraCarga = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void cargarTabla(){
        TableRow table_header = (TableRow) LayoutInflater.from(this.getActivity()).inflate(R.layout.table_row_clasificacion,null,false);
        TextView header_posicion = table_header.findViewById(R.id.lbl_posicion);
        TextView header_piloto = table_header.findViewById(R.id.lbl_piloto);
        TextView header_equipo = table_header.findViewById(R.id.lbl_equipo);
        TextView header_puntos = table_header.findViewById(R.id.lbl_puntos);

        header_posicion.setBackgroundColor(Color.parseColor("#114054"));
        header_piloto.setBackgroundColor(Color.parseColor("#114054"));
        header_equipo.setBackgroundColor(Color.parseColor("#114054"));
        header_puntos.setBackgroundColor(Color.parseColor("#114054"));

        header_posicion.setText("Posicion");
        header_piloto.setText("Piloto");
        header_equipo.setText("Equipo");
        header_puntos.setText("Puntos");

        header_posicion.setTypeface(header_posicion.getTypeface(), Typeface.BOLD);
        header_piloto.setTypeface(header_piloto.getTypeface(), Typeface.BOLD);
        header_equipo.setTypeface(header_equipo.getTypeface(), Typeface.BOLD);
        header_puntos.setTypeface(header_puntos.getTypeface(), Typeface.BOLD);

        tabla_header.addView(table_header);

        for(int i =0;i<resultados.size();i++){
            TableRow table_row = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.table_row_clasificacion,null,false);
            TextView c_posicion = table_row.findViewById(R.id.lbl_posicion);
            TextView c_piloto = table_row.findViewById(R.id.lbl_piloto);
            TextView c_equipo = table_row.findViewById(R.id.lbl_equipo);
            TextView c_puntos = table_row.findViewById(R.id.lbl_puntos);

            c_piloto.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            c_equipo.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            if(i%2==0) {
                c_posicion.setBackgroundColor(Color.parseColor("#6F6F6F"));
                c_piloto.setBackgroundColor(Color.parseColor("#6F6F6F"));
                c_equipo.setBackgroundColor(Color.parseColor("#6F6F6F"));
                c_puntos.setBackgroundColor(Color.parseColor("#6F6F6F"));
            }else{
                c_posicion.setBackgroundColor(Color.parseColor("#474343"));
                c_piloto.setBackgroundColor(Color.parseColor("#474343"));
                c_equipo.setBackgroundColor(Color.parseColor("#474343"));
                c_puntos.setBackgroundColor(Color.parseColor("#474343"));
            }

            c_posicion.setText(String.valueOf(resultados.get(i).getPosicion()));
            c_piloto.setText(resultados.get(i).getPiloto().getNombre()+" "+resultados.get(i).getPiloto().getApellido());
            c_equipo.setText(resultados.get(i).getEquipo().getNombre());
            c_puntos.setText(String.valueOf(resultados.get(i).getPuntos()));

            tabla_carrera.addView(table_row);
        }
    }
}