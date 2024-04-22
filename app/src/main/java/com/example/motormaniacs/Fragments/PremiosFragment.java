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
import com.example.motormaniacs.Model.Daos.PilotoDao;
import com.example.motormaniacs.Model.Daos.PremioDao;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.Model.Premio;
import com.example.motormaniacs.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PremiosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PremiosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PremiosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PremiosFragment newInstance(String param1, String param2) {
        PremiosFragment fragment = new PremiosFragment();
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

    private TableLayout tabla_premios;
    private TableLayout tabla_header;
    PremioDao pDao = new PremioDao();
    PilotoDao pilotoDao = new PilotoDao();
    ArrayList<Premio> premios = new ArrayList<Premio>();
    ArrayList<Piloto> pilotos = new ArrayList<Piloto>();
    Spinner spinner_premios = null;
    ArrayAdapter<String> premios_adapter;
    ArrayList<String> premios_lista = new ArrayList<>();
    ReturnMethods returnMethods = new ReturnMethods();
    boolean primeraCarga = true;
    String selectedPremio = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_premios, container, false);

        tabla_premios = rootView.findViewById(R.id.tabla_premios);
        tabla_premios.removeAllViews();
        tabla_header = rootView.findViewById(R.id.tabla_header);
        tabla_header.removeAllViews();
        spinner_premios = rootView.findViewById(R.id.spinner_premios);

        pilotos = pilotoDao.cargarPilotos();
        premios = pDao.cargarPremiosPilotos(pilotos);
        premios_lista = returnMethods.cargarPremios();
        premios_lista.add("Todos los premios");

        //Spinner Fechas
        premios_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, premios_lista);
        premios_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_premios.setAdapter(premios_adapter);
        spinner_premios.setSelection(premios_lista.size()-1);

        spinner_premios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!primeraCarga){
                    selectedPremio = premios_lista.get(position);

                    if(selectedPremio.equals("Todos los premios")){
                        premios = pDao.cargarPremiosPilotos(pilotos);
                    }else{
                        premios = pDao.cargarPremioLista(pilotos,selectedPremio);
                    }

                    tabla_premios.removeAllViews();
                    //Vuelca los datos a las tablas
                    cargarTabla();
                    tabla_header.bringToFront();
                    tabla_premios.bringToFront();

                }else{
                    primeraCarga = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Vuelca los datos a las tablas
        cargarTabla();
        tabla_header.bringToFront();
        tabla_premios.bringToFront();

        return rootView;
    }
    private void cargarTabla(){
        TableRow table_header = (TableRow) LayoutInflater.from(this.getActivity()).inflate(R.layout.table_row_premios,null,false);
        TextView header_piloto = table_header.findViewById(R.id.lbl_piloto);
        TextView header_premios = table_header.findViewById(R.id.lbl_premios);

        header_premios.setBackgroundColor(Color.parseColor("#114054"));
        header_piloto.setBackgroundColor(Color.parseColor("#114054"));

        header_piloto.setText("Piloto");
        header_premios.setText("Premios");

        header_piloto.setTypeface(header_piloto.getTypeface(), Typeface.BOLD);
        header_premios.setTypeface(header_premios.getTypeface(), Typeface.BOLD);

        tabla_header.addView(table_header);

        for(int i =0;i<premios.size();i++){
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

            c_piloto.setText(premios.get(i).getPiloto().getNombre()+" "+premios.get(i).getPiloto().getApellido());
            c_premio.setText(String.valueOf(premios.get(i).getCantidad()));

            tabla_premios.addView(table_row);
        }
    }
}