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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnSiguiente;
    @FXML
    private ImageView imgvLogo;
    @FXML
    private Label lblRegistrarCuenta;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasena;
    
    private final Controlador controlador = new Controlador();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imagen = new Image(getClass().getResourceAsStream("Logo.png"));
        imgvLogo.setImage(imagen);
        // TODO
    }    
       
    @FXML
    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "ERROR", "Rellena todos los campos.");
            return;
        }

        try {
        //  Controlador controlador = new Controlador();
            Usuario usuarioEncontrado = controlador.logearse(usuario, contrasena);

            if (usuarioEncontrado != null) {
                abrirClienteMenu();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "ERROR", "Usuario inexistente o contraseña incorrecta.");
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "ERROR", "No se pudo cargar la ventana de datos.");
            System.out.println("ERROR:" + e);
        }
    }
    
    @FXML
    private void abrirClienteRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteRegistro.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) lblRegistrarCuenta.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de cliente");
            stage.show();
        } catch (IOException e) {
            System.out.println("ERROR:" + e);
        }
    }
    
    
    private void abrirEmpleadoMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/EmpleadoMenu.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnSiguiente.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
            stage.show();
        } catch (IOException e) {
            System.out.println("ERROR:" + e);
        }
    }
    
    
    private void abrirClienteMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnSiguiente.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
            stage.show();
        } catch (IOException e) {
            System.out.println("ERROR:" + e);
        }
    }
    
    private void abrirAdministradorMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorMenu.fxml"));
            Parent root = loader.load();
            
            // Obtener la ventana actual y cambiarle la escena
            Stage stage = (Stage) btnSiguiente.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
            stage.show();
        } catch (IOException e) {
            System.out.println("ERROR:" + e);
        }
    }
    
    // Plantilla comun para las alertas
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
   
}
