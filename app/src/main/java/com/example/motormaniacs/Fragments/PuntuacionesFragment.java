package com.example.motormaniacs.Fragments;

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

import com.example.motormaniacs.Controller.ReturnMethods;
import com.example.motormaniacs.Model.Daos.EquipoDao;
import com.example.motormaniacs.Model.Daos.PilotoDao;
import com.example.motormaniacs.Model.Daos.PremioDao;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.Model.Premio;
import com.example.motormaniacs.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PuntuacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PuntuacionesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PuntuacionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PuntuacionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PuntuacionesFragment newInstance(String param1, String param2) {
        PuntuacionesFragment fragment = new PuntuacionesFragment();
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

    private TableLayout tabla_puntuaciones;
    private TableLayout tabla_header;
    PremioDao pDao = new PremioDao();
    PilotoDao pilotoDao = new PilotoDao();
    EquipoDao eDao = new EquipoDao();
    ArrayList<Equipo> equipos = new ArrayList<Equipo>();
    ArrayList<Piloto> pilotos = new ArrayList<Piloto>();
    Spinner spinner_filtro_puntuaciones = null;
    ArrayAdapter<String> puntuaciones_adapter;
    ArrayList<String> filtro_lista = new ArrayList<>();
    ReturnMethods returnMethods = new ReturnMethods();
    boolean primeraCarga = true;
    String selectedFiltro = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_puntuaciones, container, false);

        tabla_puntuaciones = rootView.findViewById(R.id.tabla_puntuaciones);
        tabla_puntuaciones.removeAllViews();
        tabla_header = rootView.findViewById(R.id.tabla_header);
        tabla_header.removeAllViews();
        spinner_filtro_puntuaciones = rootView.findViewById(R.id.spinner_filtro_puntuaciones);

        if(primeraCarga){
            pilotos = pilotoDao.cargarPuntosPilotos();
            equipos = eDao.cargarPuntuacionEquipos();
            filtro_lista.add("Pilotos");
            filtro_lista.add("Equipos");
            primeraCarga = false;
        }

        //Spinner Fechas
        puntuaciones_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, filtro_lista);
        puntuaciones_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filtro_puntuaciones.setAdapter(puntuaciones_adapter);
        spinner_filtro_puntuaciones.setSelection(0);

        spinner_filtro_puntuaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedFiltro = filtro_lista.get(position);
                tabla_puntuaciones.removeAllViews();

                if(selectedFiltro.equals("Pilotos")){
                    cargarTabla();
                }else{
                    cargarTabla();
                }

                //Vuelca los datos a las tablas
                tabla_header.bringToFront();
                tabla_puntuaciones.bringToFront();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return rootView;
    }

    private void cargarTabla(){
        TableRow table_header = (TableRow) LayoutInflater.from(this.getActivity()).inflate(R.layout.table_row_premios,null,false);
        TextView header_piloto = table_header.findViewById(R.id.lbl_piloto);
        TextView header_premios = table_header.findViewById(R.id.lbl_premios);

        header_premios.setBackgroundColor(Color.parseColor("#114054"));
        header_piloto.setBackgroundColor(Color.parseColor("#114054"));

        if(selectedFiltro.equals("Pilotos")) {
            header_piloto.setText("Piloto");
            header_premios.setText("Puntuacion");

            for(int i =0;i<pilotos.size();i++){
                TableRow table_row = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.table_row_premios,null,false);
                TextView c_piloto = table_row.findViewById(R.id.lbl_piloto);
                TextView c_premio = table_row.findViewById(R.id.lbl_premios);

                c_piloto.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(7,0,0,0);
                c_piloto.setLayoutParams(params);

                if(i%2==0) {
                    c_piloto.setBackgroundColor(Color.parseColor("#6F6F6F"));
                    c_premio.setBackgroundColor(Color.parseColor("#6F6F6F"));
                }else{
                    c_piloto.setBackgroundColor(Color.parseColor("#474343"));
                    c_premio.setBackgroundColor(Color.parseColor("#474343"));
                }

                c_piloto.setText(pilotos.get(i).getNombre()+" "+pilotos.get(i).getApellido());
                c_premio.setText(String.valueOf(pilotos.get(i).getPuntos()));

                tabla_puntuaciones.addView(table_row);
            }

        }else {
            header_piloto.setText("Equipo");
            header_premios.setText("Puntuacion");

            for(int i =0;i<equipos.size();i++){
                TableRow table_row = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.table_row_premios,null,false);
                TextView c_piloto = table_row.findViewById(R.id.lbl_piloto);
                TextView c_premio = table_row.findViewById(R.id.lbl_premios);

                c_piloto.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(7,0,0,0);
                c_piloto.setLayoutParams(params);

                if(i%2==0) {
                    c_piloto.setBackgroundColor(Color.parseColor("#6F6F6F"));
                    c_premio.setBackgroundColor(Color.parseColor("#6F6F6F"));
                }else{
                    c_piloto.setBackgroundColor(Color.parseColor("#474343"));
                    c_premio.setBackgroundColor(Color.parseColor("#474343"));
                }

                c_piloto.setText(equipos.get(i).getNombre());
                c_premio.setText(String.valueOf(equipos.get(i).getPuntuacion()));

                tabla_puntuaciones.addView(table_row);
            }
        }

        header_piloto.setTypeface(header_piloto.getTypeface(), Typeface.BOLD);
        header_premios.setTypeface(header_premios.getTypeface(), Typeface.BOLD);

        tabla_header.addView(table_header);
    }
}