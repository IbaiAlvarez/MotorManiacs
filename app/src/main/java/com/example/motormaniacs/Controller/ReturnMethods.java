package com.example.motormaniacs.Controller;

import com.example.motormaniacs.Model.Piloto;

public class ReturnMethods {


    public int calcularValoracion(Piloto p){
        int valoracion = 0;

        valoracion+= p.getTop1()*50;
        valoracion+= p.getTop5()*20;
        valoracion+= p.getTop10()*5;
        valoracion+= p.getCampeonatos()*100;

        return valoracion;
    }

}
