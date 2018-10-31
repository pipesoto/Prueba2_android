package com.example.joseguzman.gestionequipos.Data;

import com.example.joseguzman.gestionequipos.Modelo.Equipo;
import com.example.joseguzman.gestionequipos.Modelo.Usuario;

import java.util.ArrayList;

public class BaseDatos {

    static ArrayList<Usuario> losUsuarios;
    static ArrayList<Equipo> losEquipos;

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

    public Equipo buscarEquipo(String serie){
        for (Equipo e : losEquipos
                ) {
            if(e.getSerie().equals(serie)){
                return e;
            }
        }
        return null;
    }

}
