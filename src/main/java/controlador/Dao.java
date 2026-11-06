package controlador;

import java.util.Date;
import java.util.List;
import modelo.*;

public interface Dao {
    public Usuario logearse(String nombreUsuario, String contrasenaUsuario) throws Exception;
    public Usuario registrarse(String dni, String nombreUsuario, String contrasenaUsuario, String nombre, String apellidos, String email, String telefono, String direccion) throws Exception;
    public Usuario verPerfil(String id);
    public Usuario verLogrosUsuario(String id);
    
    public List<Logro> verLogros() throws Exception;
    public List<Actividad> obtenerActividades() throws Exception;
    public List<Pista> obtenerPistas() throws Exception;
<<<<<<< HEAD
    public List<Actividad> obtenerActividadesReservadas(String id) throws Exception;
=======
    public List<Reserva> obtenerReservas() throws Exception;
>>>>>>> 3f1511294de57d43e5f2942b2798691e6b4e0116
    
    public void registrarseEnActividad(String idUsuario, String idActividad) throws Exception;
    public void reservarPista(String idUsuario, String idPista, Date inicio, Date fin) throws Exception;
}
