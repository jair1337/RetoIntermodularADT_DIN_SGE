package controlador;

import java.util.Date;
import java.util.List;
import modelo.*;
import org.bson.types.ObjectId;

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
    
    public List<Actividad> obtenerActividadesPorEmpleado(String idEmpleado) throws Exception;
    
    public List<Pista> obtenerPistas() throws Exception;

    public List<Reserva> obtenerReservas() throws Exception;
    
    public List<ItemReserva> obtenerReservasUsuario(String idUsuario) throws Exception;
    
    public void cancelarActividad(String idUsuario, String idActividad) throws Exception;
    
    public void cancelarReserva(String idUsuario, String idReserva) throws Exception;
    
    public void actualizarPerfil(String idUsuario, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception;

    public void registrarseEnActividad(String idUsuario, String idActividad) throws Exception;
    
    public void reservarPista(String idUsuario, String idPista, Date inicio, Date fin, double importe, Pago metodoPago) throws Exception;
    
    public void insertarPolideportivo(String nombre, String calle, String numero, String localidad, String provincia) throws Exception;
    
    public void registrarEmpleado(String dni, String nombreUsuario, String password, String nombre, String apellidos, String email, String telefono, String direccion, String puesto) throws Exception;
    
    public void eliminarUsuario(String idUsuario) throws Exception;
    
    public void modificarHorarioEmpleado(String idEmpleado, List<ObjectId> nuevasActividadesAsignadas) throws Exception;
    
    public void insertarZona(String nombre, String tipo, String idPolideportivo, double precioHora, String estado) throws Exception;
    
public void insertarActividad(String idPolideportivo, String titulo, String tipo, String localizacion, Date fechaInicio, Date fechaFin, double precio, int aforoMaximo, int puntos, String idEmpleado) throws Exception;    
    public List<Usuario> obtenerUsuarios() throws Exception;
    
    public List<Polideportivo> obtenerPolideportivos() throws Exception;
}