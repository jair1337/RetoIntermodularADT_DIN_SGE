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
import org.bson.types.ObjectId;

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
    
    // --- Métodos de administración ---

    public void insertarPolideportivo(String nombre, String calle, String numero, String localidad, String provincia) throws Exception {
        dao.insertarPolideportivo(nombre, calle, numero, localidad, provincia);
    }

    public void registrarEmpleado(String dni, String nombreUsuario, String password, String nombre, String apellidos, String email, String telefono, String direccion, String puesto) throws Exception {
        dao.registrarEmpleado(dni, nombreUsuario, password, nombre, apellidos, email, telefono, direccion, puesto);
    }

    public void eliminarUsuario(String idUsuario) throws Exception {
        dao.eliminarUsuario(idUsuario);
    }

    public void modificarHorarioEmpleado(String idEmpleado, List<ObjectId> nuevasActividadesAsignadas) throws Exception {
        dao.modificarHorarioEmpleado(idEmpleado, nuevasActividadesAsignadas);
    }

    public void insertarZona(String nombre, String tipo, String idPolideportivo, double precioHora, String estado) throws Exception {
        dao.insertarZona(nombre, tipo, idPolideportivo, precioHora, estado);
    }
    public void insertarActividad(String idPolideportivo, String titulo, String tipo, String localizacion, Date fechaInicio, Date fechaFin, double precio, int aforoMaximo, int puntos, String idEmpleado) throws Exception {
        dao.insertarActividad(idPolideportivo, titulo, tipo, localizacion, fechaInicio, fechaFin, precio, aforoMaximo, puntos, idEmpleado);
}
    public List<Usuario> obtenerUsuarios() throws Exception {
        return dao.obtenerUsuarios();
    }
}