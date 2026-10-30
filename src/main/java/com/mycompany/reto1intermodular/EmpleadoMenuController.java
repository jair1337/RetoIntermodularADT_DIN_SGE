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
public class EmpleadoMenuController implements Initializable {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnGestionarZonas;
    @FXML
    private Button btnHorarioEmpleado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    // TODO
    }
    
    @FXML
    private void abrirGestion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/EmpleadoGestion.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnGestionarZonas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirHorario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/EmpleadoHorario.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnHorarioEmpleado.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion Zonas");
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
