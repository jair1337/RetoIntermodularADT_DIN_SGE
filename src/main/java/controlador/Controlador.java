package controlador;
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
}
