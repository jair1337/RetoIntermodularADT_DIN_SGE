package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import modelo.Usuario;

/**
 * FXML Controller class para añadir actividades
 * 
 * @author Inés
 */
public class AdministradorAnadirActividadesController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @FXML
    private TextField txtPolyId;
    @FXML
    private TextField txtPolyNombre;
    @FXML
    private TextField txtPolyDireccion;
    @FXML
    private TextField txtPolyActividad;
    @FXML
    private TextField txtActPistaId;
    @FXML
    private TextField txtActTitulo;
    @FXML
    private TextField txtActPistaZona;
    @FXML
    private TextField txtActLocalizacion;
    @FXML
    private TextField txtActFechaInicio;
    @FXML
    private TextField txtActFechaLimite;
    @FXML
    private Spinner<Double> spinnerActPrecio;
    @FXML
    private TextField txtActAforo;
    @FXML
    private Spinner<Integer> spinnerActPuntos;
    @FXML
    private TextField txtActEmpleadoId;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnRegistrar;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        logger.info("Vista de añadir actividades abierta por: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuramos el Spinner de precio (0.0 a 500.0, valor inicial 10.0)
        SpinnerValueFactory<Double> precioFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 500.0, 10.0, 1.0);
        spinnerActPrecio.setValueFactory(precioFactory);

        // Configuramos el Spinner de puntos (0 a 1000, valor inicial 50)
        SpinnerValueFactory<Integer> puntosFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 50, 5);
        spinnerActPuntos.setValueFactory(puntosFactory);
    }    

    @FXML
    private void handleRegistrar(ActionEvent event) {
        try {
            String idPolideportivo = txtPolyId.getText() != null ? txtPolyId.getText().trim() : "";
            String titulo = txtActTitulo.getText() != null ? txtActTitulo.getText().trim() : "";
            String tipo = txtActPistaId.getText() != null ? txtActPistaId.getText().trim() : "";
            String pistaZona = txtActPistaZona.getText() != null ? txtActPistaZona.getText().trim() : "";
            String localizacion = txtActLocalizacion.getText() != null ? txtActLocalizacion.getText().trim() : "";
            String strFechaInicio = txtActFechaInicio.getText() != null ? txtActFechaInicio.getText().trim() : "";
            String strFechaFin = txtActFechaLimite.getText() != null ? txtActFechaLimite.getText().trim() : "";
            double precio = spinnerActPrecio.getValue();
            int aforo = txtActAforo.getText() != null && !txtActAforo.getText().isEmpty() ? Integer.parseInt(txtActAforo.getText().trim()) : 0;
            int puntos = spinnerActPuntos.getValue();
            String idEmpleado = txtActEmpleadoId.getText() != null ? txtActEmpleadoId.getText().trim() : "";

            if (idPolideportivo.isEmpty() || titulo.isEmpty() || strFechaInicio.isEmpty() || strFechaFin.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, rellene al menos el ID del polideportivo, el título y las fechas.");
                return;
            }

            Date fechaInicio = dateFormat.parse(strFechaInicio);
            Date fechaFin = dateFormat.parse(strFechaFin);

            logger.info("Intentando registrar nueva actividad: " + titulo);

            controlador.insertarActividad(idPolideportivo, titulo, tipo, pistaZona, fechaInicio, fechaFin, precio, aforo, puntos, idEmpleado);

            logger.info("Actividad registrada con éxito.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La actividad ha sido registrada correctamente.");

            // Limpieza de campos
            txtPolyId.clear();
            txtPolyNombre.clear();
            txtPolyDireccion.clear();
            txtPolyActividad.clear();
            txtActPistaId.clear();
            txtActTitulo.clear();
            txtActPistaZona.clear();
            txtActLocalizacion.clear();
            txtActFechaInicio.clear();
            txtActFechaLimite.clear();
            txtActAforo.clear();
            txtActEmpleadoId.clear();
            spinnerActPrecio.getValueFactory().setValue(10.0);
            spinnerActPuntos.getValueFactory().setValue(50);

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "El aforo debe ser un número entero válido.");
        } catch (Exception e) {
            logger.severe("Error al registrar la actividad: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar la actividad: " + e.getMessage());
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
            
            logger.info("Volviendo al submenú de polideportivos desde añadir actividades");
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