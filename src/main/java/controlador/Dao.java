package controlador;

import java.util.Date;
import java.util.List;
import modelo.*;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public interface Dao {
    public Usuario logearse(String nombreUsuario, String contrasenaUsuario) throws Exception;
    
    public Usuario registrarse(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception;

    public Usuario verPerfil(String id);
    
    public List<Logro> verLogrosUsuario(String id) throws Exception;
    
    public List<Logro> verLogros() throws Exception;
    
    public List<Actividad> obtenerActividades() throws Exception;
    
    public List<Pista> obtenerPistas() throws Exception;

    public List<Reserva> obtenerReservas() throws Exception;
    
    public List<ItemReserva> obtenerReservasUsuario(String idUsuario) throws Exception;
    
    public void cancelarActividad(String idUsuario, String idActividad) throws Exception;
    
    public void cancelarReserva(String idUsuario, String idReserva) throws Exception;
    
    public void actualizarPerfil(String idUsuario, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception;

    public void registrarseEnActividad(String idUsuario, String idActividad) throws Exception;
    
    public void reservarPista(String idUsuario, String idPista, Date inicio, Date fin, double importe, Pago metodoPago) throws Exception;
    
}
