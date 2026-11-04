/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Albeiro.Mamani
 */
public class Pista {
    
    private String id;
    private String nombre;
    private String tipo;
    private String localizacion;
    private double precioHora;
    private Estado estado;

    public Pista(String id, String nombre, String tipo, String localizacion, double precioHora, Estado estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.localizacion = localizacion;
        this.precioHora = precioHora;
        this.estado = estado;
    }
    
    public Pista() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

   
    
    
}
