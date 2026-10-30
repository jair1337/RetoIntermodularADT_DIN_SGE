package com.mycompany.reto1intermodular;

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
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AdministradorMenuController implements Initializable {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void abrirAñadirPolideportivo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorGestionPoli.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnAñadir.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Disponibilidad");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirRegistrarEmpleado() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorRegistrarEmpleado.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Disponibilidad");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirModificarHorario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorModificarHorario.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnModificar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Disponibilidad");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirEliminarUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorEliminarUsuario.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnEliminar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Disponibilidad");
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
    
    @FXML
    private void mostrarOpciones() {
        ButtonType opcion1 = new ButtonType("Actividad");
        ButtonType opcion2 = new ButtonType("Pista");

        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle("Seleccionar opción");
        alert.setHeaderText("¿Que deseas añadir?");
        alert.setContentText("Selecciona una de las siguientes opciones:");

        alert.getButtonTypes().setAll(
            opcion1,
            opcion2,
            ButtonType.CANCEL
        );

        alert.showAndWait().ifPresent(opcion -> {
            if (opcion == opcion1) {
                System.out.println("Has elegido Actividad");

            } else if (opcion == opcion2) {
                System.out.println("Has elegido Pista");

            } 
        });
    }  
}
