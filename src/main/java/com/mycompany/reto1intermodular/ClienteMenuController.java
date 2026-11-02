package com.mycompany.reto1intermodular;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class ClienteMenuController {

    @FXML 
    private ToggleGroup grupoNav;
    
    @FXML
    private ToggleButton navInicio;
    
    @FXML
    private ToggleButton navPerfil;
    
    @FXML
    private ToggleButton navFavoritos;
    
    @FXML
    private ToggleButton navConfig;
    
    @FXML
    private Button btnDeportividad;
    
    @FXML
    private Button btnEquipos;
    
    @FXML
    private Button btnResultados;
    
    @FXML
    private Button btnConfiguracion;

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
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnDeportividad.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Disponibilidad");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirPuntosYLogros() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClientePuntosLogros.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnDeportividad.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Disponibilidad");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void cerrarSesion() {
    Stage stage = (Stage) navConfig.getScene().getWindow();
    stage.close();
    }

    // ===== Tarjetas del menú =====
    @FXML
    private void abrirDisponibilidad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteDisponibilidad.fxml"));

            Parent root = loader.load();
            Scene escena = btnDeportividad.getScene();
            escena.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    @FXML
    private void abrirDisponibilidad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteDisponibilidad.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnDeportividad.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Disponibilidad");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    
    @FXML
    private void abrirCancelar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteCancelar.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnEquipos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cancelar reserva");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirContacto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteContacto.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnResultados.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Contactanos");
            stage.show();
        } catch (IOException e) {    
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirAyuda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteAyuda.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnConfiguracion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Preguntas frecuentes");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        
    
}
