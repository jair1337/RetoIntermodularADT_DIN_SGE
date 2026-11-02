package controlador;
import java.util.List;
import modelo.Actividad;
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
    
    public List<Actividad> obtenerActividades() throws Exception {
        return dao.obtenerActividades();
    }
    
    public Usuario registrarse(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception {
        return dao.registrarse(dni, nombreUsuario, contrasenaUsuario, nombre, apellidos, email, telefono, direccion);
    }
}
