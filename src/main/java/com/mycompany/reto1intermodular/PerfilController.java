package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Usuario;
import com.mycompany.reto1intermodular.AdministradorMenuController;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class PerfilController extends LoggerController implements Initializable {
    
    private final Controlador controlador = new Controlador();
    
    private Usuario usuarioActual;

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfApellidos;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfContrasenaUsuario;
    @FXML
    private TextField tfNombreUsuario;
    
    @FXML
    private ImageView imgPerfil;
    
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        bloquearCampos(true);
        
        Image image = new Image(getClass().getResourceAsStream("/images/usuarioimg.jpg"));
        imgPerfil.setImage(image);
        
        // Si ya nos pasaron un usuario antes por setUsuario, lo respetamos; si no, tiramos de Sesion
        if (usuarioActual == null) {
            usuarioActual = Sesion.getUsuario();
        }
        cargarPerfil();
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        cargarPerfil(); // Recargamos al recibirlo para asegurar que tiene los datos del admin
    }
    
    
    private void cargarPerfil() {
    if (usuarioActual == null) {
        logger.warning("No se ha recibido ningún usuario");
        return;
    }

    tfNombreUsuario.setText(usuarioActual.getNombreUsuario());
    tfContrasenaUsuario.setText(usuarioActual.getContrasenaUsuario());
    tfNombre.setText(usuarioActual.getNombre());
    tfApellidos.setText(usuarioActual.getApellidos());
    tfEmail.setText(usuarioActual.getEmail());
    tfTelefono.setText(usuarioActual.getTelefono());
    tfDireccion.setText(usuarioActual.getDireccion());

    }
    
    @FXML
    private void desbloquearCampos() {
        bloquearCampos(false);
        btnModificar.setDisable(true);
        btnGuardar.setDisable(false);
    }
    
    private void bloquearCampos(boolean bloqueado) {
    tfNombreUsuario.setDisable(bloqueado);
    tfContrasenaUsuario.setDisable(bloqueado);
    tfNombre.setDisable(bloqueado);
    tfApellidos.setDisable(bloqueado);
    tfEmail.setDisable(bloqueado);
    tfTelefono.setDisable(bloqueado);
    tfDireccion.setDisable(bloqueado);
    }
    
    /*
    @FXML
    private void actualizarPerfil() {
    System.out.println("salio bien");
        btnModificar.setDisable(false);
        btnGuardar.setDisable(true);
    }
    */
    @FXML
    private void actualizarPerfil() {
        if (usuarioActual == null) {
            return;
        }

        String nombreUsuario = tfNombreUsuario.getText().trim();
        String contrasena = tfContrasenaUsuario.getText().trim();
        String nombre = tfNombre.getText().trim();
        String apellidos = tfApellidos.getText().trim();
        String email = tfEmail.getText().trim();
        String telefono = tfTelefono.getText().trim();
        String direccion = tfDireccion.getText().trim();

        if (nombreUsuario.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()
                || email.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Rellena todos los campos").showAndWait();
            return;
        }

        try {
            controlador.actualizarPerfil(usuarioActual.getId(), nombreUsuario, contrasena,
                    nombre, apellidos, email, telefono, direccion);

            // Actualizamos también el usuario guardado en Sesion para que siga coherente
            usuarioActual.setNombreUsuario(nombreUsuario);
            usuarioActual.setContrasenaUsuario(contrasena);
            usuarioActual.setNombre(nombre);
            usuarioActual.setApellidos(apellidos);
            usuarioActual.setEmail(email);
            usuarioActual.setTelefono(telefono);
            usuarioActual.setDireccion(direccion);

            bloquearCampos(true);
            btnModificar.setDisable(false);
            btnGuardar.setDisable(true);

            new Alert(Alert.AlertType.INFORMATION, "Perfil actualizado correctamente").showAndWait();
            logger.info("Perfil actualizado: " + nombreUsuario);
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
            logger.severe("Error al actualizar el perfil: " + e);
        }
    }
    
    
       
  @FXML
    private void volver() {
        try {
            String rutaDestino = "/com/mycompany/reto1intermodular/ClienteMenu.fxml";
            String tituloVentana = "Menú Cliente";

            // Si por lo que sea usuarioActual está a null, intentamos rescatarlo de Sesion
            if (usuarioActual == null && Sesion.getUsuario() != null) {
                usuarioActual = Sesion.getUsuario();
            }

            // Verificamos el rol del usuario actual para decidir la ruta exacta
            if (usuarioActual != null && usuarioActual.getRol() != null) {
                switch (usuarioActual.getRol()) {
                    case Administrador:
                        rutaDestino = "/com/mycompany/reto1intermodular/AdministradorMenu.fxml";
                        tituloVentana = "Menú Administrador";
                        break;
                    case Empleado:
                        rutaDestino = "/com/mycompany/reto1intermodular/EmpleadoMenu.fxml";
                        tituloVentana = "Menú Empleado";
                        break;
                    case Cliente:
                    default:
                        rutaDestino = "/com/mycompany/reto1intermodular/ClienteMenu.fxml";
                        tituloVentana = "Menú Cliente";
                        break;
                }
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaDestino));
            Parent root = loader.load();

            // Pasamos el usuario al controlador de destino según a qué menú estemos volviendo
            Object controller = loader.getController();
            if (controller != null && usuarioActual != null) {
                try {
                    controller.getClass().getMethod("setUsuario", Usuario.class).invoke(controller, usuarioActual);
                } catch (Exception e) {
                    // Si el controlador destino no tiene setUsuario, se ignora de forma segura
                }
            }

            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            logger.info("Volviendo al escenario: " + tituloVentana);
        } catch (IOException e) {
            logger.severe("ERROR al volver: " + e);
            System.out.println("ERROR: " + e);
        }
    }
}