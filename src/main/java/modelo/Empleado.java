/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Albeiro.Mamani
 */
public class Empleado extends Usuario {
    private String puesto;
    private ArrayList<Actividad> actividadesAsignadas;

    public Empleado(String puesto, ArrayList<Actividad> actividadesAsignadas, int id, String dni, Rol rol, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String direccion, String email, String telefono) {
        super(id, dni, rol, nombreUsuario, contrasenaUsuario, nombre, apellidos, direccion, email, telefono);
        this.puesto = puesto;
        this.actividadesAsignadas = actividadesAsignadas;
    }

    
}
