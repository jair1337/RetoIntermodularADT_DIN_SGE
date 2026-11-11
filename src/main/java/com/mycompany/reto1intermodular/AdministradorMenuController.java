package com.mycompany.reto1intermodular;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani / Inés
 */
public class AdministradorMenuController extends LoggerController implements Initializable {

    private Usuario usuarioActual;

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnGestionarPolideportivo; // <-- Botón enlazado al submenú de polideportivos
    @FXML
    private Button btnRegistrarEmpleado;
    @FXML
    private Button btnModificarHorario;
    @FXML
    private Button btnEliminarUsuario;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("Administrador recibido: " + usuarioActual);
        logger.info("Menu de administrador inicializado para el usuario: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // ===== Barra lateral / Acciones de cuenta =====
    
    @FXML
    private void abrirPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/Perfil.fxml"));
            Parent root = loader.load();

            // Recuperamos el controlador de Perfil y le pasamos el usuario actual
            PerfilController perfilController = loader.getController();
            perfilController.setUsuario(usuarioActual);

            Scene escena = btnPerfil.getScene();
            escena.setRoot(root);
            
            logger.info("Inicializando escenario Perfil desde Administrador");
        } catch (IOException e) {
            logger.severe("Error al abrir Perfil: " + e.getMessage());
            System.out.println("ERROR: " + e);
        }
    }

 @FXML
    private void cerrarSesion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres cerrar sesion?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioBienvenida.fxml"));
                Parent root = loader.load();
                
                Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
                Scene scene = new Scene(root);
                
                // Inyectamos la hoja de estilos por código para garantizar que cargue el diseño azul
                try {
                    String css = getClass().getResource("/styles/ApartadoLoginCSS.css").toExternalForm();
                    scene.getStylesheets().add(css);
                } catch (Exception e) {
                    try {
                        // Ruta alternativa por si estuviera empaquetada en el paquete
                        String cssAlt = getClass().getResource("/com/mycompany/reto1intermodular/styles/ApartadoLoginCSS.css").toExternalForm();
                        scene.getStylesheets().add(cssAlt);
                    } catch (Exception ex) {
                        System.out.println("No se pudo cargar el CSS automáticamente: " + ex.getMessage());
                    }
                }
                
                stage.setScene(scene);
                stage.setTitle("TxandaSport - Bienvenida");
                stage.show();
                
                logger.info("Sesion de administrador cerrada correctamente y vuelta a la bienvenida con estilos.");
            } catch (IOException e) {
                logger.severe("Error al cerrar sesion: " + e.getMessage());
                System.out.println("ERROR al cerrar sesion: " + e);
                e.printStackTrace();
            }
        }
    }
    // ===== Botones centrales de gestión de Administrador =====

    @FXML
    private void abrirGestionPolideportivo(ActionEvent event) {
        cambiarEscena("/com/mycompany/reto1intermodular/AdministradorGestionPolideportivo.fxml", btnGestionarPolideportivo, "Gestión de Polideportivos");
    }

    @FXML
    private void abrirRegistrarEmpleado(ActionEvent event) {
        cambiarEscena("/com/mycompany/reto1intermodular/AdministradorRegistroEmpleado.fxml", btnRegistrarEmpleado, "Registrar Empleado");
    }

    @FXML
    private void abrirModificarHorario(ActionEvent event) {
        cambiarEscena("/com/mycompany/reto1intermodular/AdministradorModificarHorario.fxml", btnModificarHorario, "Modificar Horario de Empleado");
    }

    @FXML
    private void abrirEliminarUsuario(ActionEvent event) {
        cambiarEscena("/com/mycompany/reto1intermodular/AdministradorEliminarUsuario.fxml", btnEliminarUsuario, "Eliminar Usuario");
    }

    // Método auxiliar privado para centralizar los cambios de escena propagando la sesión de usuario
    private void cambiarEscena(String fxmlRuta, Button botonReferencia, String nombreVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
            Parent root = loader.load();
            
            // Propagamos automáticamente el usuario al controlador de destino si lo soporta
            Object controller = loader.getController();
            if (controller != null) {
                try {
                    controller.getClass().getMethod("setUsuario", Usuario.class).invoke(controller, usuarioActual);
                } catch (Exception e) {
                    // Si la vista destino no requiere setUsuario, se ignora de forma segura
                }
            }
            
            Scene escena = botonReferencia.getScene();
            escena.setRoot(root);
            
            logger.info("Navegando a la vista: " + nombreVentana);
        } catch (IOException e) {
            logger.severe("Error al cargar la vista " + fxmlRuta + ": " + e.getMessage());
            System.out.println("ERROR al cargar " + fxmlRuta + ": " + e);
        }
    }
}