package controlador;

import java.util.List;
import modelo.*;

public interface Dao {
    public Usuario logearse(String nombreUsuario, String contrasenaUsuario) throws Exception;
    public Usuario registrarse(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception;
    public List<Actividad> obtenerActividades() throws Exception;
}
