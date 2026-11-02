package modelo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Albeiro.Mamani
 */
public class Actividad {
    private String id;
    private String titulo;
    private String tipo;
    private String pistaZona;
    private String localizacion;
    private Date fechaInicio;
    private Date fechaFin;
    private double precio;
    private int aforoMaximo;
    private int puntos;
    private String idEmpleado;
    private List<Cliente> participantes;
    

    public Actividad(String id, String titulo, String tipo, String pistaZona, String localizacion, Date fechaInicio, Date fechaFin, double precio, int aforoMaximo, int puntos, String idEmpleado, List<Cliente> participantes) {
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
    
    public Actividad() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPistaZona() {
        return pistaZona;
    }

    public void setPistaZona(String pistaZona) {
        this.pistaZona = pistaZona;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public List<Cliente> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Cliente> participantes) {
        this.participantes = participantes;
    }  
}
