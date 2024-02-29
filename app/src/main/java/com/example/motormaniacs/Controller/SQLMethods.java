package com.example.motormaniacs.Controller;


import static android.content.ContentValues.TAG;

import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;

import com.example.motormaniacs.Model.Carrera;
import com.example.motormaniacs.Model.CarreraMostrar;
import com.example.motormaniacs.Model.Equipo;
import com.example.motormaniacs.Model.Piloto;
import com.example.motormaniacs.Model.PosicionPilotos;
import com.example.motormaniacs.Model.Premio;
import com.example.motormaniacs.Model.Resultado;
import com.example.motormaniacs.Model.Usuario;
import com.mysql.jdbc.Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class SQLMethods extends AsyncTask<Void,Void, Collection> {

    private final String TABLA_CARRERAS = "carreras";
    private final String TABLA_EQUIPOS = "equipos";
    private final String TABLA_PILOTOS = "pilotos";
    private final String TABLA_PREMIOS = "premios";
    private final String TABLA_RESULTADOS = "resultados";
    private final String TABLA_USUARIOS = "usuarios";
    ConnectionClass con_class = new ConnectionClass();
    ReturnMethods returnMethods = new ReturnMethods();


    // ╔═════════════════════════════════════════════════════════ CARGA REGION ═════════════════════════════════════════════════════════╗

    public Equipo cargarEquipoNombre(String nombre_equipo){
        Equipo e = new Equipo();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT equipo_id,nombre,estado FROM "+TABLA_EQUIPOS+" where nombre='"+nombre_equipo+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                e.setId(req.getInt(1));
                e.setNombre(req.getString(2));
                e.setEstado(req.getString(3));
            }

            e.setPilotos(kargarPilotosEquipo(e.getId()));

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return e;
    }
    public Equipo kargaEquipos(){
        Equipo e = new Equipo();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT * FROM "+TABLA_EQUIPOS+";";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                e.setId(req.getInt(1));
                e.setNombre(req.getString(2));
                e.setEstado(req.getString(3));
            }

            e.setPilotos(kargarPilotosEquipo(e.getId()));

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return e;
    }

    public ArrayList<Piloto> kargarPilotosEquipo (int id_equipo_seleccionado){
        ArrayList<Piloto> pilotos = new ArrayList<Piloto>();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT * FROM "+TABLA_PILOTOS+" where equipo_id='"+id_equipo_seleccionado+"';";
            ResultSet req = comand.executeQuery(query);

            while(req.next()) {
                Piloto p = new Piloto();

                p.setId(req.getInt(1));
                p.setId_equipo(req.getInt(2));
                p.setNombre(req.getString(3));
                p.setApellido(req.getString(4));
                p.setEstado(req.getString(5));
                p.setNumero(req.getInt(6));

                int[] posiciones = consultarVictoriasPiloto(p.getId());

                p.setTop1(posiciones[0]);
                p.setTop5(posiciones[1]);
                p.setTop10(posiciones[2]);
                p.setCampeonatos(consultarCampeonatosPiloto(p.getId()));
                p.setValoracion(returnMethods.calcularValoracion(p));

                pilotos.add(p);
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return pilotos;
    }
    public ArrayList<Piloto> kargarPilotos(){
        ArrayList<Piloto> pilotos = new ArrayList<Piloto>();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT * FROM "+TABLA_PILOTOS+";";
            ResultSet req = comand.executeQuery(query);

            while(req.next()) {
                Piloto p = new Piloto();

                p.setId(req.getInt(1));
                p.setId_equipo(req.getInt(2));
                p.setNombre(req.getString(3));
                p.setApellido(req.getString(4));
                p.setEstado(req.getString(5));
                p.setNumero(req.getInt(6));

                int[] posiciones = consultarVictoriasPiloto(p.getId());

                p.setTop1(posiciones[0]);
                p.setTop5(posiciones[1]);
                p.setTop10(posiciones[2]);
                p.setCampeonatos(consultarCampeonatosPiloto(p.getId()));
                p.setValoracion(returnMethods.calcularValoracion(p));

                pilotos.add(p);
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return pilotos;
    }

    public Piloto kargarUnicoPilotoEquipo (int id_piloto,int id_equipo_seleccionado){
        Piloto p = new Piloto();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT * FROM "+TABLA_PILOTOS+" where Piloto_id="+id_piloto+" and equipo_id='"+id_equipo_seleccionado+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {

                p.setId(req.getInt(1));
                p.setId_equipo(req.getInt(2));
                p.setNombre(req.getString(3));
                p.setApellido(req.getString(4));
                p.setEstado(req.getString(5));
                p.setNumero(req.getInt(6));

                int[] posiciones = consultarVictoriasPiloto(p.getId());

                p.setTop1(posiciones[0]);
                p.setTop5(posiciones[1]);
                p.setTop10(posiciones[2]);
                p.setCampeonatos(consultarCampeonatosPiloto(p.getId()));
                p.setValoracion(returnMethods.calcularValoracion(p));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return p;
    }

    public ArrayList<String> obtenerListaEquipos(){
        ArrayList<String> equipos = new ArrayList<String>();

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT nombre from "+TABLA_EQUIPOS+";";
            ResultSet req = comand.executeQuery(query);

            while(req.next()){
                equipos.add(req.getString(1));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return equipos;
    }

    public ArrayList<Resultado> cargarResultadosUltimaCarrera(){
        ArrayList<Resultado> resultados = new ArrayList<Resultado>();
        int carrera_id = -1;

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT carrera_id FROM "+TABLA_CARRERAS+" order BY fecha DESC LIMIT 1;";
            ResultSet req = comand.executeQuery(query);

            if(req.next()){
                carrera_id = req.getInt(1);
            }

            String query_resultado =  "SELECT * FROM "+TABLA_RESULTADOS+" where carrera_id="+carrera_id+";";
            ResultSet req_resultado = comand.executeQuery(query_resultado);

            while (req_resultado.next()){
                Resultado res = new Resultado();

                res.setId_resultado(req_resultado.getInt(1));
                res.setPiloto(kargarUnicoPilotoEquipo(req_resultado.getInt(2),req_resultado.getInt(3)));
                res.setEquipo(cargarEquipoId(req_resultado.getInt(3)));
                res.setPosicion(req_resultado.getInt(5));
                res.setPuntos(req_resultado.getInt(6));
                resultados.add(res);
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }

        return resultados;
    }

    public ArrayList<Resultado> cargarResultadosCarreraID(int id_carrera){
        ArrayList<Resultado> resultados = new ArrayList<Resultado>();

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query_resultado =  "SELECT * FROM "+TABLA_RESULTADOS+" where carrera_id="+id_carrera+";";
            ResultSet req_resultado = comand.executeQuery(query_resultado);

            while (req_resultado.next()){
                Resultado res = new Resultado();

                res.setId_resultado(req_resultado.getInt(1));
                res.setPiloto(kargarUnicoPilotoEquipo(req_resultado.getInt(2),req_resultado.getInt(3)));
                res.setEquipo(cargarEquipoId(req_resultado.getInt(3)));
                res.setPosicion(req_resultado.getInt(5));
                res.setPuntos(req_resultado.getInt(6));
                resultados.add(res);
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }

        return resultados;
    }

    public Carrera cargarCarrera(int carrera_id){
        Carrera c = new Carrera();

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query_resultado =  "SELECT * FROM "+TABLA_CARRERAS+" where carrera_id="+carrera_id+";";
            ResultSet req_resultado = comand.executeQuery(query_resultado);

            if (req_resultado.next()){

                c.setId(req_resultado.getInt(1));
                c.setCircuito(req_resultado.getString(2));
                c.setFecha(req_resultado.getString(3));
                c.setPosicion_pilotos(cargarResultadosCarreraID(c.getId()));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }

        return c;
    }

    public Equipo cargarEquipoId(int id_equipo){
        Equipo e = new Equipo();

        try {
            con_class.CrearConexionMySQL();

            //Datu baseari konexioa eta Bezeroa logeatzeko kontsulta egiten dugu
            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT equipo_id,nombre,estado FROM "+TABLA_EQUIPOS+" where equipo_id='"+id_equipo+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                e.setId(req.getInt(1));
                e.setNombre(req.getString(2));
                e.setEstado(req.getString(3));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return e;
    }


    // ╔═════════════════════════════════════════════════════════ Insert REGION ═════════════════════════════════════════════════════════╗


    public ArrayList<Piloto> añadirPiloto(ArrayList<Piloto> pilotos, String nombre, String apellido) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT COUNT(*) FROM "+TABLA_PILOTOS+" where nombre='"+nombre+"' and apellido='"+apellido+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                if(req.getInt(1)==0){
                    Piloto p = new Piloto();
                    p.setId(req.getInt(1));
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setEstado("retirado");
                    comand = (Statement) con_class.getConnection().createStatement();
                    query =  "INSERT INTO "+TABLA_PILOTOS+" (Nombre, Apellido, Estado) VALUES ('"+nombre+"','"+apellido+"','retirado')";
                    comand.executeUpdate(query);
                    pilotos.add(p);
                    guardado = true;
                }
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return pilotos;
    }

    public ArrayList<Equipo> añadirEquipo(ArrayList<Equipo> equipos, String nombre) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT COUNT(*) FROM "+TABLA_EQUIPOS+" where nombre='"+nombre+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                if(req.getInt(1)==0){
                    Equipo e = new Equipo();
                    e.setId(req.getInt(1));
                    e.setNombre(nombre);
                    e.setEstado("retirado");
                    comand = (Statement) con_class.getConnection().createStatement();
                    query =  "INSERT INTO "+TABLA_EQUIPOS+" (Nombre, Estado) VALUES ('"+nombre+"', 'retirado')";
                    comand.executeUpdate(query);
                    equipos.add(e);
                    guardado = true;
                }
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return equipos;
    }

    public ArrayList<Carrera> añadirCarrera(ArrayList<Carrera> carreras, String nombre, String fecha) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT COUNT(*) FROM "+TABLA_CARRERAS+" where nombre='"+nombre+"';";
            ResultSet req = comand.executeQuery(query);

            if(req.next()) {
                if(req.getInt(1)==0){
                    Carrera c = new Carrera();
                    c.setId(req.getInt(1));
                    c.setCircuito(nombre);
                    c.setFecha(fecha);
                    comand = (Statement) con_class.getConnection().createStatement();
                    query =  "INSERT INTO "+TABLA_CARRERAS+" (Nombre, Fecha) VALUES ('"+nombre+"','"+fecha+"')";
                    comand.executeUpdate(query);
                    carreras.add(c);
                    guardado = true;
                }
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return carreras;
    }

    public boolean añadirResultado(int piloto, int carrera, int posicion) {
        boolean guardado = false;

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "SELECT COUNT(*) FROM "+TABLA_RESULTADOS+" where (Piloto_id, Carrera_id, Posicion) VALUES ('"+piloto+"','"+carrera+"','"+posicion+"')";
            ResultSet req = comand.executeQuery(query);
            if(req.next()) {
                if(req.getInt(1)==0){
                    int puntos = Metodos.devolverPuntos(posicion);
                    int equipo = devolverIdEquipo(piloto);
                    comand = (Statement) con_class.getConnection().createStatement();
                    query =  "INSERT INTO "+TABLA_CARRERAS+" (Piloto_id, Eqiopo_id, Carrera_id, Posicion, Puntos) VALUES ('"+piloto+"','"+equipo+"','"+carrera+"','"+posicion+"','"+puntos+"')";
                    comand.executeUpdate(query);
                    guardado = true;
                }
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return guardado;
    }


    // ╔═════════════════════════════════════════════════════════ SELECT REGION ═════════════════════════════════════════════════════════╗


    public int devolverIdEquipo(int piloto) {
        int e = 0;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query = "SELECT Equipo_id FROM " + TABLA_PILOTOS + " where Piloto_id='" + piloto + "';";
            ResultSet req = comand.executeQuery(query);

            if (req.next()) {
                e = (req.getInt(1));
            }

            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return e;
    }

    public int[] consultarVictoriasPiloto(int piloto_id){
        int[] victorias = new int[3];

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT COUNT(CASE WHEN Posicion = 1 THEN 1 END) AS 'TOP 1', COUNT(CASE WHEN Posicion <= 5 THEN 1 END) AS 'TOP 5', COUNT(CASE WHEN Posicion <= 10 THEN 1 END) AS 'TOP 10' FROM "+TABLA_RESULTADOS+" WHERE piloto_id="+piloto_id+";";
            ResultSet req_vicotrias = comand.executeQuery(query);

            if(req_vicotrias.next()){
                victorias[0]=(req_vicotrias.getInt(1));
                victorias[1]=(req_vicotrias.getInt(2));
                victorias[2]=(req_vicotrias.getInt(3));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return victorias;
    }

    public int consultarCampeonatosPiloto(int piloto_id){
        int victorias = 0;


        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query =  "SELECT COUNT(*) FROM "+TABLA_PREMIOS+" WHERE Piloto_id="+piloto_id+" and Nombre LIKE '%Campeon%'; ";
            ResultSet req_vicotrias = comand.executeQuery(query);

            if(req_vicotrias.next()){
                victorias=(req_vicotrias.getInt(1));
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return victorias;
    }

    public ArrayList<CarreraMostrar> obtenerResultadosCarrera(int id_carrera){
        ArrayList<CarreraMostrar> resultados = new ArrayList<CarreraMostrar>();

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query_resultado =  "SELECT r.Posicion, p.Nombre, p.Apellido, e.Nombre AS Equipo, r.Puntos\n" +
                    "        FROM "+TABLA_RESULTADOS+" r\n" +
                    "        JOIN "+TABLA_PILOTOS+" p ON r.Piloto_id = p.Piloto_id\n" +
                    "        JOIN "+TABLA_EQUIPOS+" e ON p.Equipo_id = e.Equipo_id\n" +
                    "        WHERE r.Carrera_id = '"+id_carrera+"'\n" +
                    "        ORDER BY r.Posicion;\n";
            ResultSet req_resultado = comand.executeQuery(query_resultado);

            while (req_resultado.next()){
                CarreraMostrar car = new CarreraMostrar();
                car.setPosicion(req_resultado.getInt(1));
                car.setNombre(req_resultado.getString(2));
                car.setApellido(req_resultado.getString(3));
                car.setEquipo(req_resultado.getString(4));
                car.setPuntos(req_resultado.getInt(5));

                resultados.add(car);
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return resultados;
    }

    public ArrayList<PosicionPilotos> obtenerPilotosPorPuntos(){
        ArrayList<PosicionPilotos> resultados = new ArrayList<PosicionPilotos>();

        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();

            String query_resultado =  "SELECT p.Piloto_id, p.Nombre, p.Apellido, p.Numero, SUM(r.Puntos) AS TotalPuntos\n" +
                    "FROM "+TABLA_PILOTOS+" p\n" +
                    "LEFT JOIN "+TABLA_RESULTADOS+" r ON p.Piloto_id = r.Piloto_id\n" +
                    "LEFT JOIN "+TABLA_CARRERAS+" c ON r.Carrera_id = c.carrera_id\n" +
                    "WHERE p.Estado = 'compitiendo' AND c.fecha BETWEEN '2024-02-01' AND '2024-11-30'\n" +
                    "GROUP BY p.Piloto_id, p.Nombre, p.Apellido, p.Numero\n" +
                    "ORDER BY TotalPuntos DESC;\n";
            ResultSet req_resultado = comand.executeQuery(query_resultado);

            while (req_resultado.next()){
                PosicionPilotos pil = new PosicionPilotos();
                pil.setId(req_resultado.getInt(1));
                pil.setNombre(req_resultado.getString(2));
                pil.setApellido(req_resultado.getString(3));
                pil.setNumero(req_resultado.getInt(4));
                pil.setPuntos(req_resultado.getInt(5));

                resultados.add(pil);
            }

            con_class.getConnection().close();
        }catch(Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }
        return resultados;
    }


    // ╔═════════════════════════════════════════════════════════ UPDATE REGION ═════════════════════════════════════════════════════════╗


    public ArrayList<Piloto> editarEquipoPiloto (ArrayList<Piloto> pilotos, int idPiloto, int id_EquipoNuevo) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "UPDATE "+TABLA_PILOTOS+" SET Equipo_id = "+id_EquipoNuevo+" WHERE Piloto_id = "+idPiloto+";";
            int filasAfectadas = comand.executeUpdate(query);

            if (filasAfectadas > 0) {
                boolean encontrado = false;
                for (int i = 0;i<pilotos.size()&&encontrado;i++){
                    if(pilotos.get(i).getId() == idPiloto){
                        pilotos.get(i).setId_equipo(id_EquipoNuevo);
                        encontrado = true;
                    }
                }
                guardado = true;
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return pilotos;
    }

    public ArrayList<Piloto> editarPilotoNumero (ArrayList<Piloto> pilotos, int idPiloto, int nuevoNumero) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "UPDATE "+TABLA_PILOTOS+" SET Numero = "+nuevoNumero+" WHERE Piloto_id = "+idPiloto+";";
            int filasAfectadas = comand.executeUpdate(query);

            if (filasAfectadas > 0) {
                boolean encontrado = false;
                for (int i = 0;i<pilotos.size()&&encontrado;i++){
                    if(pilotos.get(i).getId() == idPiloto){
                        pilotos.get(i).setNumero(nuevoNumero);
                        encontrado = true;
                    }
                }
                guardado = true;
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return pilotos;
    }

    public ArrayList<Piloto> editarEstadoPiloto (ArrayList<Piloto> pilotos, int idPiloto, String estado) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "UPDATE "+TABLA_PILOTOS+" SET Estado = "+estado+" WHERE Piloto_id = "+idPiloto+";";
            int filasAfectadas = comand.executeUpdate(query);

            if (filasAfectadas > 0) {
                boolean encontrado = false;
                for (int i = 0;i<pilotos.size()&&encontrado;i++){
                    if(pilotos.get(i).getId() == idPiloto){
                        pilotos.get(i).setEstado(estado);
                        encontrado = true;
                    }
                }
                guardado = true;
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return pilotos;
    }

    public ArrayList<Equipo> editarEstadoEquipo (ArrayList<Equipo> equipos, int idEquipo, String estado) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "UPDATE "+TABLA_EQUIPOS+" SET Estado = "+estado+" WHERE Equipo_id = "+idEquipo+";";
            int filasAfectadas = comand.executeUpdate(query);

            if (filasAfectadas > 0) {
                boolean encontrado = false;
                for (int i = 0;i<equipos.size()&&encontrado;i++){
                    if(equipos.get(i).getId() == idEquipo){
                        equipos.get(i).setEstado(estado);
                        encontrado = true;
                    }
                }
                guardado = true;
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return equipos;
    }

    public ArrayList<Equipo> editarNombreEquipo (ArrayList<Equipo> equipos, int idEquipo, String nombre) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "UPDATE "+TABLA_EQUIPOS+" SET Estado = "+nombre+" WHERE Equipo_id = "+idEquipo+";";
            int filasAfectadas = comand.executeUpdate(query);

            if (filasAfectadas > 0) {
                boolean encontrado = false;
                for (int i = 0;i<equipos.size()&&encontrado;i++){
                    if(equipos.get(i).getId() == idEquipo){
                        equipos.get(i).setNombre(nombre);
                        encontrado = true;
                    }
                }
                guardado = true;
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return equipos;
    }


    // ╔═════════════════════════════════════════════════════════ DELETE REGION ═════════════════════════════════════════════════════════╗


    public boolean eliminarUsuario(Usuario usuario) {
        boolean guardado = false;
        try {
            con_class.CrearConexionMySQL();

            Statement comand = (Statement) con_class.getConnection().createStatement();
            String query =  "DELETE FROM "+TABLA_USUARIOS+" where usuario_id='"+usuario.getId()+"';";
            int filasAfectadas = comand.executeUpdate(query);

            if (filasAfectadas > 0) {
                guardado = true;
            }
            con_class.getConnection().close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return guardado;
    }


    // ╔═════════════════════════════════════════════════════════ OTROS REGION ═════════════════════════════════════════════════════════╗


    @Override
    protected Collection doInBackground(Void... voids) {
        return null;
    }
}
