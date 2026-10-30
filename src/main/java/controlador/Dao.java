package controlador;

import modelo.Usuario;

public interface Dao {
    public Usuario logearse(String nombreUsuario, String contrasenaUsuario) throws Exception;  
}
