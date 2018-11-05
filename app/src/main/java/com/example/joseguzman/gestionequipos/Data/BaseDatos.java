package com.example.joseguzman.gestionequipos.Data;

import com.example.joseguzman.gestionequipos.Modelo.Equipo;
import com.example.joseguzman.gestionequipos.Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    public static ArrayList<Usuario> losUsuarios = new ArrayList<>() ;
    static ArrayList<Equipo> losEquipos = new ArrayList<>();
    static ArrayList<List> equiposUsuario = new ArrayList<List>();



    public static void cargarDatos(){
        losUsuarios.add(new Usuario("user1","Juan","García","Finanzas","123"));
        losUsuarios.add(new Usuario("user2","Jose","Perez","Informatica","123"));
        losUsuarios.add(new Usuario("user3","Guillermo","Hernandez","Gerencia","123"));

        losEquipos.add(new Equipo("12345MON","Monitor Bajo Costo",25000));
        losEquipos.add(new Equipo("12345MOU","Mouse Inalámbrico",5000));
        losEquipos.add(new Equipo("12345NOT","Notebook Dell Empresarial",400000));
        losEquipos.add(new Equipo("12345TJT","Tarjeta de red",30000));
        losEquipos.add(new Equipo("12345AUD","Audífonos Clear Conference",20000));
        losEquipos.add(new Equipo("12345BAT","Batería Externa Emergency",15000));
        losEquipos.add(new Equipo("12345NOT2","Notebook Marca BaratoBarato",100000));
        losEquipos.add(new Equipo("12345CAU","Caucho Repuesto",50000));
        losEquipos.add(new Equipo("12345CON","Pata Conejo p/Suerte",25000));
        losEquipos.add(new Equipo("12345FOR","Fórmula secreta para adelgazar",500000));
    }

    public static List<Equipo> equiposPorUsuario(String usuario){
        List<Equipo> equipos = new ArrayList<>();
        for (List l : equiposUsuario
             ) {
            if(((String)l.get(0)).equals(usuario)){
                for (Equipo e: losEquipos
                     ) {
                    if(((String)l.get(1)).equals(e.getSerie())){
                        equipos.add(e);
                    }
                }
            }
        }
        return equipos;
    }


    public static boolean cargarEquipoAlUsuario(String usuario, String serie){
        List<String> array = new ArrayList<>();
        array.add(usuario);
        array.add(serie);
        equiposUsuario.add(array);
        return true;
    }

    public static boolean descargarEquipoAlUsuario(String usuario, String serie){
        for(int i = 0; i<equiposUsuario.size();i++){
            if(equiposUsuario.get(i).get(0).equals(usuario) && equiposUsuario.get(i).get(1).equals(serie)){
                equiposUsuario.remove(i);
            }
         }
        return true;
    }

    public static List<Equipo> equiposDisponibles(){
        List<Equipo> equipos = new ArrayList<>();
        boolean ocupado=false;
        for (Equipo e: losEquipos
             ) {
            ocupado = false;
            for (List eu:equiposUsuario
                 ) {
                if(e.getSerie().equals(eu.get(1))){
                    ocupado =  true;
                    break;
                }

            }
            if(!ocupado){
                equipos.add(e);
            }
        }
        return equipos;
    }

    public boolean añadirUsuario(Usuario u){
        losUsuarios.add(u);
        return true;
    }

    public Usuario buscarUsuario(String usuario){
        for (Usuario u : losUsuarios
             ) {
            if(u.getUsuario().equals(usuario)){
                return u;
            }
        }
        return null;
    }

    public boolean añadirEquipo(Equipo e){
        losEquipos.add(e);
        return true;
    }

    public static Equipo buscarEquipo(String serie){
        for (Equipo e : losEquipos
                ) {
            if(e.getSerie().equals(serie)){
                return e;
            }
        }
        return null;
    }

}
