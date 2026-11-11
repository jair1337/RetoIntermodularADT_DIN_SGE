package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.Usuario;

/**
 * FXML Controller class para el registro de empleados
 * 
 * @author Inés
 */
public class AdministradorRegistroEmpleadoController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();

    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCargo; // <-- Vinculado al TextField libre del FXML
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnAnadir;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        logger.info("Vista de registro de empleado abierta por: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Al ser un campo de texto libre, no requiere inicializacion de elementos desplegables.
    }    

    @FXML
    private void anadirEmpleado(ActionEvent event) {
        logger.info("Intentando registrar un nuevo empleado...");

        try {
            String nombreUsuario = txtNombreUsuario.getText() != null ? txtNombreUsuario.getText().trim() : "";
            String password = txtPassword.getText() != null ? txtPassword.getText().trim() : "";
            String dni = txtDni.getText() != null ? txtDni.getText().trim() : "";
            String nombre = txtNombre.getText() != null ? txtNombre.getText().trim() : "";
            String apellidos = txtApellidos.getText() != null ? txtApellidos.getText().trim() : "";
            String direccion = txtDireccion.getText() != null ? txtDireccion.getText().trim() : "";
            String email = txtEmail.getText() != null ? txtEmail.getText().trim() : "";
            String telefono = txtTelefono.getText() != null ? txtTelefono.getText().trim() : "";
            String puesto = txtCargo.getText() != null ? txtCargo.getText().trim() : "";

            if (nombreUsuario.isEmpty() || password.isEmpty() || dni.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, rellena al menos el DNI, Nombre, Usuario y Contraseña.");
                return;
            }

            controlador.registrarEmpleado(dni, nombreUsuario, password, nombre, apellidos, email, telefono, direccion, puesto);

            logger.info("¡Empleado registrado con éxito en la base de datos: " + nombreUsuario + "!");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "El empleado ha sido guardado correctamente en el sistema.");

            limpiarCampos();

        } catch (Exception e) {
            logger.severe("Error al registrar empleado: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error de registro", "No se pudo registrar el empleado:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorMenu.fxml"));
            Parent root = loader.load();

            AdministradorMenuController adminController = loader.getController();
            if (adminController != null && usuarioActual != null) {
                adminController.setUsuario(usuarioActual);
            }

            Scene escena = btnAtras.getScene();
            if (escena != null) {
                escena.setRoot(root);
                logger.info("Volviendo al menu de administrador desde registro de empleado");
            }
        } catch (IOException e) {
            logger.severe("Error al volver al menu de administrador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtNombreUsuario.clear();
        txtPassword.clear();
        txtDni.clear();
        txtNombre.clear();
        txtApellidos.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtCargo.clear();
    }
}