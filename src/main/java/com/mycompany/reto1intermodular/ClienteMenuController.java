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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClienteMenuController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
   
    @FXML
    private ToggleButton btnCasa;
    @FXML
    private ToggleButton btnPerfil;
    @FXML
    private ToggleButton btnPuntosLogros;
    @FXML
    private ToggleButton btnCerrarSesion;
    @FXML
    private Button btnDisponibilidad;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnContactanos;
    @FXML
    private Button btnAyuda;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblSubtitulo;
    
    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("Usuario recibido: " + usuarioActual);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    // ===== Barra lateral =====   
    @FXML
    private void abrirPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/Perfil.fxml"));

            Parent root = loader.load();

            // Recuperamos el controlador de Perfil
            PerfilController perfilController = loader.getController();

            // Le pasamos el usuario que ya tenemos
            //perfilController.setUsuario(usuarioActual);

            // Cambiamos la interfaz
            Scene escena = btnPerfil.getScene();
            escena.setRoot(root);
            
            logger.info("Inicializando escenario Perfil");
        } catch (IOException e) {
            logger.severe("Error al abrir Perfil: " + e.getMessage());
        }
    }
    
    @FXML
    private void abrirPuntosYLogros() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteLogrosPuntos.fxml"));

            Parent root = loader.load();
                       
            Scene escena = btnPuntosLogros.getScene();
            escena.setRoot(root);
            
            
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    

    /*
    @FXML
    private void cerrarSesion() {
    Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
    stage.close();
    logger.info("Sesion cerrada");
    }
    */
    

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
                Sesion.cerrar();
                stage.setTitle("TxandaSport - Bienvenida");
                stage.show();
                
                logger.info("Sesion de cliente cerrada correctamente y vuelta a la bienvenida con estilos.");
            } catch (IOException e) {
                logger.severe("Error al cerrar sesion: " + e.getMessage());
                System.out.println("ERROR al cerrar sesion: " + e);
                e.printStackTrace();
            }
        }
    }
    // ===== Tarjetas del menú =====
    @FXML
    private void abrirDisponibilidad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteDisponibilidad.fxml"));

            Parent root = loader.load();
            Scene escena = btnDisponibilidad.getScene();
            escena.setRoot(root);

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    
    @FXML
    private void abrirCancelar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteCancelar.fxml"));

            Parent root = loader.load();
            Scene escena = btnCancelar.getScene();
            escena.setRoot(root);

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    
    @FXML
    private void abrirContacto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteContacto.fxml"));

            Parent root = loader.load();
            Scene escena = btnContactanos.getScene();
            escena.setRoot(root);

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    
    
    @FXML
    private void abrirAyuda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteFAQ.fxml"));

            Parent root = loader.load();
            Scene escena = btnAyuda.getScene();
            escena.setRoot(root);

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
}