/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Albeiro.Mamani
 */
public class Actividad {
    private int id;
    private String titulo;
    private String tipo;
    private String pistaZona;
    private String localizacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precio;
    private int aforoMaximo;
    private int puntos;
    private String idEmpleado;
    private ArrayList<Cliente> participantes;

    public Actividad(int id, String titulo, String tipo, String pistaZona, String localizacion, LocalDate fechaInicio, LocalDate fechaFin, double precio, int aforoMaximo, int puntos, String idEmpleado, ArrayList<Cliente> participantes) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.pistaZona = pistaZona;
        this.localizacion = localizacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precio = precio;
        this.aforoMaximo = aforoMaximo;
        this.puntos = puntos;
        this.idEmpleado = idEmpleado;
        this.participantes = participantes;
    }
    
    
}
