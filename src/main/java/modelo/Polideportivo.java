/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Modelo para los Polideportivos de TxandaSport
 * 
 * @author Inés / Albeiro
 */
public class Polideportivo {
    
    private String id;
    private String nombre;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    public Polideportivo(String id, String nombre, String calle, String numero, String localidad, String provincia) {
        this.id = id;
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Polideportivo() {
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Método auxiliar para unificar la dirección y mostrarla cómodamente en la TableView.
     */
    public String getDireccionCompleta() {
        return calle + " nº " + numero + ", " + localidad + " (" + provincia + ")";
    }

    @Override
    public String toString() {
        return nombre;
    }
}