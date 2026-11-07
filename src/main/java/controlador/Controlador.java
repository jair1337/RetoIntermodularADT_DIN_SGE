package controlador;
import java.util.Date;
import java.util.List;
import modelo.Actividad;
import modelo.ItemReserva;
import modelo.Logro;
import modelo.Pago;
import modelo.Pista;
import modelo.Reserva;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class Controlador {
    private Dao dao;

    public Controlador() {
        this.dao = new DaoImplementacion();
        System.out.println("-> [Controlador] Usando persistencia: Base de Datos MongoDB directa");
    }

    public Usuario logearse(String nombreUsuario, String contrasenaUsuario) throws Exception {
        return dao.logearse(nombreUsuario, contrasenaUsuario);
    }
    
    public Usuario registrarse(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception {
        return dao.registrarse(dni, nombreUsuario, contrasenaUsuario, nombre, apellidos, email, telefono, direccion);
    }
    
    public Usuario verPerfil(String id) {
        return dao.verPerfil(id);
    }
    
    public List<Logro> verLogrosUsuario(String id) throws Exception {
        return dao.verLogrosUsuario(id);
    }
    
    public List<Logro> verLogros() throws Exception {
        return dao.verLogros();
    }
    
    public List<Actividad> obtenerActividades() throws Exception {
        return dao.obtenerActividades();
    }
    
    public List<Pista> obtenerPistas() throws Exception {
        return dao.obtenerPistas();
    }
    
    public List<Reserva> obtenerReservas() throws Exception {
        return dao.obtenerReservas();
    }
    
    public List<ItemReserva> obtenerReservasUsuario(String idUsuario) throws Exception {
        return dao.obtenerReservasUsuario(idUsuario);
    }
    
    public void cancelarActividad(String idUsuario, String idActividad) throws Exception {
        dao.cancelarActividad(idUsuario, idActividad);
    }

    public void cancelarReserva(String idUsuario, String idReserva) throws Exception {
        dao.cancelarReserva(idUsuario, idReserva);
    }
    
    public void actualizarPerfil(String idUsuario, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception {
        dao.actualizarPerfil(idUsuario, nombreUsuario, contrasenaUsuario, nombre, apellidos, email, telefono, direccion);
    }
    
    public void registrarseEnActividad(String idUsuario, String idActividad) throws Exception {
    dao.registrarseEnActividad(idUsuario, idActividad);
    }

    public void reservarPista(String idUsuario, String idPista, Date inicio, Date fin, double importe, Pago metodoPago) throws Exception {
        dao.reservarPista(idUsuario, idPista, inicio, fin, importe, metodoPago);
    }
      
}
