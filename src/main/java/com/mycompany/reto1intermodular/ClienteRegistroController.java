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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClienteRegistroController implements Initializable {

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnRegistrarCuenta;
    
    private Controlador controlador = new Controlador();
    @FXML
    private TextField txtDNI;
    @FXML
    private TextField txtNombreCuenta;
    @FXML
    private TextField txtContrasenaCuenta;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    // TODO
    }
    
    @FXML
    private void registroDeCliente() {
    String dni = txtDNI.getText();
    String nombreC = txtNombreCuenta.getText();
    String contrasenaC = txtContrasenaCuenta.getText();
    String nombre = txtNombre.getText();
    String apellidos = txtApellidos.getText();
    String email = txtEmail.getText();
    String telefono = txtTelefono.getText();
    String direccion = txtDireccion.getText();
    
    try {
        if (dni.isBlank() | nombreC.isBlank() | contrasenaC.isBlank() | nombre.isBlank() | apellidos.isBlank() | email.isBlank() | telefono.isBlank() | direccion.isBlank() | telefono.isBlank()) {
            System.out.println("mal");
        } else {
            controlador.registrarse(dni, nombreC, contrasenaC, nombre, apellidos, email, telefono, direccion);
            volver();    
        }
        
    } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }
    
    
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioInicioSesion.fxml"));

            Parent root = loader.load();

            UsuarioInicioSesionController login = loader.getController();
  
            Stage stage = (Stage) btnAtras.getScene().getWindow();
            login.setStage(stage);
            
            Scene escena = new Scene(root);
            escena.setFill(Color.TRANSPARENT);
            escena.getStylesheets().add(getClass().getResource("/styles/ApartadoLoginCSS.css").toExternalForm());
            
            stage.setScene(escena);
            stage.setTitle("Iniciar sesión");
            
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
}
