package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import modelo.Polideportivo;
import modelo.Usuario;

/**
 * FXML Controller class para añadir pistas
 * 
 * @author Inés
 */
public class AdministradorAnadirPistasController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();

    @FXML
    private ComboBox<Polideportivo> cbPolideportivo;
    @FXML
    private TextField txtPistaTipo;
    @FXML
    private TextField txtPistaLocalizacion;
    @FXML
    private Spinner<Double> spinnerPistaPrecio;
    @FXML
    private ComboBox<String> cbPistaEstado;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnRegistrar;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        logger.info("Vista de añadir pistas abierta por: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Configuramos el Spinner de precio (valor inicial 10.0, rango de 0 a 500, saltos de 1.0)
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 500.0, 10.0, 1.0);
        spinnerPistaPrecio.setValueFactory(valueFactory);

        // 2. Rellenamos el ComboBox de estados
        cbPistaEstado.getItems().addAll("Libre", "Ocupado", "Mantenimiento");
        cbPistaEstado.setValue("Libre"); // Valor por defecto

        // 3. Cargamos los polideportivos en el desplegable
        cargarPolideportivos();
    }    

    private void cargarPolideportivos() {
        try {
            cbPolideportivo.getItems().clear();
            List<Polideportivo> lista = controlador.obtenerPolideportivos();
            if (lista != null) {
                cbPolideportivo.getItems().addAll(lista);
            }

            cbPolideportivo.setCellFactory(param -> new javafx.scene.control.ListCell<Polideportivo>() {
                @Override
                protected void updateItem(Polideportivo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre());
                    }
                }
            });
            cbPolideportivo.setButtonCell(cbPolideportivo.getCellFactory().call(null));

        } catch (Exception e) {
            logger.severe("Error al cargar polideportivos: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        Polideportivo poliSeleccionado = cbPolideportivo.getValue();
        String tipoPista = txtPistaTipo.getText() != null ? txtPistaTipo.getText().trim() : "";
        String localizacionPista = txtPistaLocalizacion.getText() != null ? txtPistaLocalizacion.getText().trim() : "";
        double precioHora = spinnerPistaPrecio.getValue();
        String estadoPista = cbPistaEstado.getValue();

        // Validaciones básicas de campos obligatorios
        if (poliSeleccionado == null || tipoPista.isEmpty() || localizacionPista.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, selecciona un polideportivo, introduce el tipo y la localizacion de la pista.");
            return;
        }

        logger.info("Intentando registrar nueva pista/zona: " + tipoPista + " para el polideportivo: " + poliSeleccionado.getNombre());

        try {
            // Llamamos al método del controlador que mapea con el DAO e inserta en la colección 'pistas'
            controlador.insertarZona(tipoPista, tipoPista, poliSeleccionado.getId(), precioHora, estadoPista);

            logger.info("Pista registrada con exito en MongoDB.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La pista ha sido registrada correctamente.");

            // Limpiamos los campos del formulario tras el éxito
            cbPolideportivo.setValue(null);
            txtPistaTipo.clear();
            txtPistaLocalizacion.clear();
            spinnerPistaPrecio.getValueFactory().setValue(10.0);
            cbPistaEstado.setValue("Libre");

        } catch (Exception e) {
            logger.severe("Error al registrar la pista: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", "No se pudo registrar la pista: " + e.getMessage());
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
            
            logger.info("Volviendo al submenu de polideportivos desde añadir pistas");
        } catch (IOException e) {
            logger.severe("Error al volver al submenu de polideportivos: " + e.getMessage());
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