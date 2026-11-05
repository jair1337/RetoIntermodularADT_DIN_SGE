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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class PerfilController extends LoggerController implements Initializable {
    
    private final Controlador controlador = new Controlador();
    
    private Usuario usuarioActual;

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfApellidos;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfContrasenaUsuario;
    @FXML
    private TextField tfNombreUsuario;
    
    @FXML
    private ImageView imgPerfil;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bloquearCampos(true);
        
        Image image = new Image(getClass().getResourceAsStream("/images/usuarioimg.jpg"));
        imgPerfil.setImage(image);
        
        usuarioActual = Sesion.getUsuario();
        cargarPerfil();
       
    }
    
    /*
    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        //cargarPerfil();
    }
    */
    
    private void cargarPerfil() {
    if (usuarioActual == null) {
        logger.warning("No se ha recibido ningún usuario");
        return;
    }

    tfNombreUsuario.setText(usuarioActual.getNombreUsuario());
    tfContrasenaUsuario.setText(usuarioActual.getContrasenaUsuario());
    tfNombre.setText(usuarioActual.getNombre());
    tfApellidos.setText(usuarioActual.getApellidos());
    tfEmail.setText(usuarioActual.getEmail());
    tfTelefono.setText(usuarioActual.getTelefono());
    tfDireccion.setText(usuarioActual.getDireccion());

    }
    
    @FXML
    private void desbloquearCampos() {
        bloquearCampos(false);
        btnModificar.setDisable(true);
        btnGuardar.setDisable(false);
    }
    
    private void bloquearCampos(boolean bloqueado) {
    tfNombreUsuario.setDisable(bloqueado);
    tfContrasenaUsuario.setDisable(bloqueado);
    tfNombre.setDisable(bloqueado);
    tfApellidos.setDisable(bloqueado);
    tfEmail.setDisable(bloqueado);
    tfTelefono.setDisable(bloqueado);
    tfDireccion.setDisable(bloqueado);
    }
    
    @FXML
    private void actualizarPerfil() {
    System.out.println("salio bien");
        btnModificar.setDisable(false);
        btnGuardar.setDisable(true);
    }
       
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));

            Parent root = loader.load();
            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            logger.info("Volviendo al escenario Menu");
        } catch (IOException e) {
            logger.severe("ERROR: " + e);
        }
    }  
}