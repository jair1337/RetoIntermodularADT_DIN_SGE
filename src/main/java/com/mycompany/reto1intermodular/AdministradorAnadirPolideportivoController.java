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
import javafx.scene.control.TextField;
import modelo.Usuario;

/**
 * FXML Controller class para añadir un nuevo polideportivo
 * 
 * @author Inés
 */
public class AdministradorAnadirPolideportivoController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCalle;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtLocalidad;
    @FXML
    private TextField txtProvincia;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnRegistrar;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        logger.info("Vista de añadir polideportivo abierta por: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización adicional si fuera necesaria
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        String nombre = txtNombre.getText() != null ? txtNombre.getText().trim() : "";
        String calle = txtCalle.getText() != null ? txtCalle.getText().trim() : "";
        String numero = txtNumero.getText() != null ? txtNumero.getText().trim() : "";
        String localidad = txtLocalidad.getText() != null ? txtLocalidad.getText().trim() : "";
        String provincia = txtProvincia.getText() != null ? txtProvincia.getText().trim() : "";

        // Validaciones básicas de campos obligatorios
        if (nombre.isEmpty() || calle.isEmpty() || localidad.isEmpty() || provincia.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, rellena al menos el nombre, calle, localidad y provincia.");
            return;
        }

        logger.info("Intentando registrar el polideportivo: " + nombre);

        try {
            // Llamamos al método del controlador que inserta en la base de datos (colección polideportivos)
            controlador.insertarPolideportivo(nombre, calle, numero, localidad, provincia);

            logger.info("Polideportivo registrado con éxito en MongoDB.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "El polideportivo ha sido registrado correctamente.");

            // Limpiamos los campos del formulario tras la inserción correcta
            txtNombre.clear();
            txtCalle.clear();
            txtNumero.clear();
            txtLocalidad.clear();
            txtProvincia.clear();

        } catch (Exception e) {
            logger.severe("Error al registrar el polideportivo: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", "No se pudo registrar el polideportivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorGestionPolideportivo.fxml"));
            Parent root = loader.load();

            AdministradorGestionPolideportivoController gestionController = loader.getController();
            gestionController.setUsuario(usuarioActual);

            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            logger.info("Volviendo al submenú de gestión de polideportivos desde añadir polideportivo");
        } catch (IOException e) {
            logger.severe("Error al volver al submenú de gestión de polideportivos: " + e.getMessage());
            System.out.println("ERROR: " + e);
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}