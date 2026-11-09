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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import modelo.Usuario;

/**
 * FXML Controller class para añadir clases / actividades
 * 
 * @author Inés
 */
public class AdministradorAnadirClasesController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();

    @FXML
    private TextField txtPolyId;
    @FXML
    private TextField txtPolyNombre;
    @FXML
    private TextField txtPolyDireccion;
    @FXML
    private TextField txtPolyActividad;
    @FXML
    private TextField txtPistaId;
    @FXML
    private TextField txtPistaTipo;
    @FXML
    private TextField txtPistaLocalizacion;
    @FXML
    private Spinner<Double> spinnerClasePrecio;
    @FXML
    private ComboBox<String> cbClaseEstado;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnRegistrar;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        logger.info("Vista de añadir clases abierta por: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Configuramos el Spinner de precio de la clase (valor inicial 5.0, rango de 0 a 200, saltos de 1.0)
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 200.0, 5.0, 1.0);
        spinnerClasePrecio.setValueFactory(valueFactory);

        // 2. Rellenamos el ComboBox de estados de la clase
        cbClaseEstado.getItems().addAll("Activa", "Cancelada", "Completa");
        cbClaseEstado.setValue("Activa"); // Valor por defecto
    }    

    @FXML
    private void handleRegistrar(ActionEvent event) {
        String idPolideportivo = txtPolyId.getText() != null ? txtPolyId.getText().trim() : "";
        String nombreClase = txtPistaId.getText() != null ? txtPistaId.getText().trim() : "";
        String tipoClase = txtPistaTipo.getText() != null ? txtPistaTipo.getText().trim() : "";
        double precioClase = spinnerClasePrecio.getValue();
        String estadoClase = cbClaseEstado.getValue();

        // Validaciones básicas de campos obligatorios
        if (idPolideportivo.isEmpty() || nombreClase.isEmpty() || tipoClase.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, introduce el ID del polideportivo, el identificador de la clase y su tipo.");
            return;
        }

        logger.info("Intentando registrar nueva clase/actividad: " + nombreClase + " para el polideportivo ID: " + idPolideportivo);

        try {
            // Aquí puedes invocar el método correspondiente en tu controlador/DAO para guardar la clase
            // Por ejemplo, si tienes un método de inserción de actividades:
            // controlador.insertarActividad(...);

            logger.info("Clase registrada con éxito.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La clase ha sido registrada correctamente.");

            // Limpiamos los campos del formulario tras el éxito
            txtPolyId.clear();
            txtPolyNombre.clear();
            txtPolyDireccion.clear();
            txtPolyActividad.clear();
            txtPistaId.clear();
            txtPistaTipo.clear();
            txtPistaLocalizacion.clear();
            spinnerClasePrecio.getValueFactory().setValue(5.0);
            cbClaseEstado.setValue("Activa");

        } catch (Exception e) {
            logger.severe("Error al registrar la clase: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", "No se pudo registrar la clase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorGestionPolideportivo.fxml"));
            Parent root = loader.load();

            AdministradorGestionPolideportivoController subMenuController = loader.getController();
            subMenuController.setUsuario(usuarioActual);

            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            logger.info("Volviendo al submenú de polideportivos desde añadir clases");
        } catch (IOException e) {
            logger.severe("Error al volver al submenú de polideportivos: " + e.getMessage());
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