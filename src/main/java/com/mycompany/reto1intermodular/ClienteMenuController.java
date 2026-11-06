package com.mycompany.reto1intermodular;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import modelo.Usuario;


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
    /*
    @FXML
    private void abrirPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/Perfil.fxml"));

            Parent root = loader.load();
            Scene escena = btnPerfil.getScene();
            escena.setRoot(root);
            
            Stage stage = (Stage) escena.getWindow();
            stage.setWidth(450);
            stage.setHeight(550);
            stage.centerOnScreen();
            logger.info("Inicializando escenario Perfil");
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    */
    
    @FXML
    private void abrirPuntosYLogros() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClientePuntosLogros.fxml"));

            Parent root = loader.load();
            
            /*
            ClientePuntosLogrosController plController = loader.getController();

            
            plController.setUsuario(usuarioActual);
            */
            
            Scene escena = btnPuntosLogros.getScene();
            escena.setRoot(root);
            
            
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    
    @FXML
    private void cerrarSesion() {
    Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
    stage.close();
    logger.info("Sesion cerrada");
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