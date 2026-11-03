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
import javafx.stage.Stage;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class PerfilController extends LoggerController implements Initializable {
    
    private final Controlador controlador = new Controlador();
    
    private String dniUsuario;

    @FXML
    private Button btnAtras;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtContrasenaUsuario;
    @FXML
    private TextField txtNombreUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarPerfil();
        
    }
    
    private void cargarPerfil () {
    Usuario usuario = controlador.verPerfil(dniUsuario);
    
    if (usuario != null) {

            txtNombreUsuario.setText(usuario.getNombreUsuario());
            txtContrasenaUsuario.setText(usuario.getContrasenaUsuario());
            txtNombre.setText(usuario.getNombre());
            txtApellidos.setText(usuario.getApellidos());
            txtEmail.setText(usuario.getEmail());
            txtTelefono.setText(usuario.getTelefono());
            txtDireccion.setText(usuario.getDireccion());

        } else {
            logger.info("No se ha encontrado al usuario");
        }
    }
    
        
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));

            Parent root = loader.load();
            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            Stage stage = (Stage) escena.getWindow();
            stage.setWidth(900);
            stage.setHeight(560);
            stage.centerOnScreen();
            logger.info("Volviendo al escenario Menu");
        } catch (IOException e) {
            logger.info("Error en la salida/entrada");
            System.out.println("ERROR: " + e);
        }
    }  
}