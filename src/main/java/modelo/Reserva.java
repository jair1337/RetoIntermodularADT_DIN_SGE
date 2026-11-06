/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Albeiro.Mamani
 */
public class Reserva {
    
    private String id;
    private String idCliente;
    private String idPista;
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private double importe;
    private Pago metodoPago;
    private EstadoReserva estado;
    private Date fechaReserva;

    public Reserva(String id, String idCliente, String idPista, Date fechaHoraInicio, Date fechaHoraFin, double importe, Pago metodoPago, EstadoReserva estado, Date fechaReserva) {
        this.id = id;
        this.idCliente = idCliente;
        this.idPista = idPista;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.importe = importe;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fechaReserva = fechaReserva;
    }

    public Reserva() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Pago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(Pago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    
    
}
