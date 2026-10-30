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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClienteMenuController implements Initializable {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnConsultarDisponibilidad;
    @FXML
    private Button btnConsultarActividades;
    @FXML
    private Button btnContacto;
    @FXML
    private Button btnAyuda;
    @FXML
    private Button btnLogrosPuntos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void abrirDisponibilidad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteDisponibilidad.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnConsultarDisponibilidad.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Disponibilidad");
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
            Stage stage = (Stage) btnContacto.getScene().getWindow();
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
            Stage stage = (Stage) btnAyuda.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Preguntas frecuentes");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/Perfil.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnPerfil.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Mi perfil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirPuntosLogros() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClientePuntosLogros.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnLogrosPuntos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Mis puntos y logros");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirCancelar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteCancelar.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnConsultarActividades.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cancelar reserva");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/Login.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
