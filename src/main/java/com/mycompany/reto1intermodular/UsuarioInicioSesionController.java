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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
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
        // TODO
        // Las dos cajas de contraseña comparten el mismo texto
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
    
    
    private void initialize() {
        // Las dos cajas de contraseña comparten el mismo texto
        txtContrasenaVisible.textProperty().bindBidirectional(pwdContrasena.textProperty());
    }
    

    @FXML
    private void alternarVisibilidad() {
        boolean mostrar = !txtContrasenaVisible.isVisible();
        txtContrasenaVisible.setVisible(mostrar);
        pwdContrasena.setVisible(!mostrar);
    }

    /*
    @FXML
    private void entrar() {
        String user = txtUsuario.getText().trim();
        String pass = pwdContrasena.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            mostrarError("Introduce usuario y contraseña");
            return;
        }

        try {
            Usuario usuarioEncontrado = controlador.logearse(user, pass);

            if (usuarioEncontrado != null) {
                loginCorrecto = true;
                stage.close();
            } else {
                mostrarError("Usuario o contraseña incorrectos");
            }

        } catch (Exception e) {       
            logger.severe("Error durante el inicio de sesión: " + e.getMessage());
        }
        
    }
    */
    
    @FXML
    private void entrar() {
        String user = txtUsuario.getText().trim();
        String pass = pwdContrasena.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            mostrarError("Introduce usuario y contraseña");
            return;
        }

        try {

            // pasando el usario lgo cambiar si no funca
            usuarioEncontrado = controlador.logearse(user, pass);

            if (usuarioEncontrado != null) {

                loginCorrecto = true;

                // Cerramos el popup
                stage.close();

            } else {

                mostrarError("Usuario o contraseña incorrectos");
            }

        } catch (Exception e) {

            logger.severe("Error al iniciar sesión: " + e.getMessage());
            mostrarError("Se ha producido un error al iniciar sesión");
        }
    }
    
    @FXML
    private void abrirRegistrarse() throws Exception {
    try {    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteRegistro.fxml"));
        Parent root = loader.load();
        
        Scene escena = new Scene(root);
        //escena.getStylesheets().add(getClass().getResource("/styles/RegistroCSS.css").toExternalForm());
            
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

    private void mostrarError(String msg) {
        lblError.setText(msg);
        lblError.setVisible(true);
        lblError.setManaged(true);
    }
    
}