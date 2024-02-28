package com.example.motormaniacs.Controller;

import com.mysql.jdbc.Statement;

import java.sql.ResultSet;

public class Metodos {
    ConnectionClass con_class = new ConnectionClass();
    public static int devolverPuntos(int posicion){
        int puntos = 0;
        switch(posicion){
            case 1:
                puntos = 40;
                break;
            case 2:
                puntos = 35;
                break;
            case 3:
                puntos = 34;
                break;
            case 4:
                puntos = 33;
                break;
            case 5:
                puntos = 32;
                break;
            case 6:
                puntos = 31;
                break;
            case 7:
                puntos = 30;
                break;
            case 8:
                puntos = 29;
                break;
            case 9:
                puntos = 28;
                break;
            case 10:
                puntos = 27;
                break;
            case 11:
                puntos = 26;
                break;
            case 12:
                puntos = 25;
                break;
            case 13:
                puntos = 24;
                break;
            case 14:
                puntos = 23;
                break;
            case 15:
                puntos = 22;
                break;
            case 16:
                puntos = 21;
                break;
            case 17:
                puntos = 20;
                break;
            case 18:
                puntos = 19;
                break;
            case 19:
                puntos = 18;
                break;
            case 20:
                puntos = 17;
                break;
            case 21:
                puntos = 16;
                break;
            case 22:
                puntos = 15;
                break;
            case 23:
                puntos = 14;
                break;
            case 24:
                puntos = 13;
                break;
            case 25:
                puntos = 12;
                break;
            case 26:
                puntos = 11;
                break;
            case 27:
                puntos = 10;
                break;
            case 28:
                puntos = 9;
                break;
            case 29:
                puntos = 8;
                break;
            case 230:
                puntos = 7;
                break;
            case 31:
                puntos = 6;
                break;
            case 32:
                puntos = 5;
                break;
            case 33:
                puntos = 4;
                break;
            case 34:
                puntos = 3;
                break;
            case 35:
                puntos = 2;
                break;
            case 36:
                puntos = 1;
                break;
            case 37:
                puntos = 1;
                break;
            case 38:
                puntos = 1;
                break;
            case 39:
                puntos = 1;
                break;
            case 40:
                puntos = 1;
                break;
            default:
                puntos = 0;
                break;
        }
        return puntos;
    }

}
