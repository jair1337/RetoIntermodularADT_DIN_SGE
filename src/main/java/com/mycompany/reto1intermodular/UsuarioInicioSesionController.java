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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class UsuarioInicioSesionController extends LoggerController implements Initializable {

    private Usuario usuarioEncontrado;
    
    @FXML
    private Button btnCerrar;
    @FXML
    private Button btnEntrar;
    @FXML
    private Hyperlink lblRegistrarse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtContrasenaVisible.textProperty().bindBidirectional(pwdContrasena.textProperty());
    }

    @FXML 
    private TextField txtUsuario;
    @FXML 
    private PasswordField pwdContrasena;
    @FXML 
    private TextField txtContrasenaVisible;
    @FXML 
    private Button btnOjo;
    @FXML 
    private Label lblError;
   
    private final Controlador controlador = new Controlador();
    
    private Stage stage;
    private boolean loginCorrecto = false;

    public void setStage(Stage stage) { 
        this.stage = stage; 
    }
    public boolean isLoginCorrecto() { 
        return loginCorrecto; 
    }
    public String getUsuario() { 
        return txtUsuario.getText(); 
    }
    
    public Usuario getUsuarioAutenticado() {
        return usuarioEncontrado;
    }
    
    @FXML
    private void alternarVisibilidad() {
        boolean mostrar = !txtContrasenaVisible.isVisible();
        txtContrasenaVisible.setVisible(mostrar);
        pwdContrasena.setVisible(!mostrar);
    }

    @FXML
    private void entrar() {
        String user = txtUsuario.getText().trim();
        String pass = pwdContrasena.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de validación", "Introduce usuario y contraseña");
            return;
        }

        try {
            Usuario encontrado = controlador.logearse(user, pass);

            if (encontrado == null) {
                logger.warning("Intento de login fallido para el usuario: " + user);
                mostrarAlerta(Alert.AlertType.ERROR, "Error de acceso", "Usuario o contraseña incorrectos");
            } else if (encontrado.getRol() == null) {
                logger.warning("El usuario " + user + " no tiene un rol válido en MongoDB.");
                mostrarAlerta(Alert.AlertType.ERROR, "Error de rol", "Tu cuenta no tiene un rol asignado");
            } else {
                usuarioEncontrado = encontrado;
                loginCorrecto = true;
                logger.info("Login exitoso para el usuario: " + user);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Login correcto");
                
                //stage.close();
                if (stage != null) {
                    stage.close();
                }
                
            }
        } catch (Exception e) {
            logger.severe("Error crítico al iniciar sesión: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error del sistema", "Se ha producido un error al iniciar sesión");
        }
    }
    
    @FXML
    private void abrirRegistrarse() throws Exception {
    try {    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteRegistro.fxml"));
        Parent root = loader.load();
        
        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.setTitle("Registro de cliente");
        
        } catch (IOException e) {
            System.out.println("ERROR:" + e);
        }
    }

    @FXML
    private void cerrar() {
        stage.close();
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
}