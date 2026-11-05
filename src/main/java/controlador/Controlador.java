package controlador;
import java.util.Date;
import java.util.List;
import modelo.Actividad;
import modelo.Pista;
import modelo.Usuario;

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
    
    public List<Actividad> obtenerActividades() throws Exception {
        return dao.obtenerActividades();
    }
    
    public List<Pista> obtenerPistas() throws Exception {
        return dao.obtenerPistas();
    }
    
    public void registrarseEnActividad(String idUsuario, String idActividad) throws Exception {
    dao.registrarseEnActividad(idUsuario, idActividad);
}

    public void reservarPista(String idUsuario, String idPista, Date inicio, Date fin) throws Exception {
        dao.reservarPista(idUsuario, idPista, inicio, fin);
    }
      
}
