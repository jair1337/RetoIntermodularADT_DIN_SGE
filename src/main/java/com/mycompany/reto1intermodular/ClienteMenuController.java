package com.mycompany.reto1intermodular;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import modelo.Usuario;


public class ClienteMenuController extends LoggerController {

    private Usuario usuarioActual;
   
    private ToggleGroup grupoNav;
    
    private ToggleButton navConfig;
    
    private Button btnDeportividad;
    
    private Button btnEquipos;
    
    private Button btnResultados;
    
    private Button btnConfiguracion;
    
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
    }
    
    private void initialize() {
        // Evita que la barra lateral se quede sin ningún botón seleccionado
        grupoNav.selectedToggleProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo == null && anterior != null) {
                anterior.setSelected(true);
            }
        });
    }

    // ===== Barra lateral =====    
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
    
    @FXML
    private void abrirPuntosYLogros() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClientePuntosLogros.fxml"));

            Parent root = loader.load();
            Scene escena = btnPerfil.getScene();
            escena.setRoot(root);
            
            Stage stage = (Stage) escena.getWindow();
            stage.setWidth(450);
            stage.setHeight(400);
            stage.centerOnScreen();
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
            Scene escena = btnPerfil.getScene();
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
            Scene escena = btnPerfil.getScene();
            escena.setRoot(root);

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    
    @FXML
    private void abrirAyuda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteAyuda.fxml"));

            Parent root = loader.load();
            Scene escena = btnPerfil.getScene();
            escena.setRoot(root);

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
}