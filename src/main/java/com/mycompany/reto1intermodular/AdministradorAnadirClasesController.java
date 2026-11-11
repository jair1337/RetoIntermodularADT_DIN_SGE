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
import modelo.Rol;
import modelo.Usuario;

/**
 * FXML Controller class para añadir clases / pistas / zonas
 * 
 * @author Inés
 */
public class AdministradorAnadirClasesController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();

    @FXML
    private ComboBox<Polideportivo> cbPolideportivo;
    @FXML
    private ComboBox<Usuario> cbEmpleado;
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
        // 1. Configuramos el Spinner de precio
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 200.0, 15.0, 1.0);
        spinnerClasePrecio.setValueFactory(valueFactory);

        // 2. Rellenamos el ComboBox de estados
        cbClaseEstado.getItems().addAll("Libre", "Ocupado", "Mantenimiento");
        cbClaseEstado.setValue("Libre"); // Valor por defecto común para pistas/zonas

        // 3. Cargamos los desplegables de la izquierda
        cargarPolideportivos();
        cargarEmpleados();
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

    private void cargarEmpleados() {
        try {
            cbEmpleado.getItems().clear();
            List<Usuario> usuarios = controlador.obtenerUsuarios();
            if (usuarios != null) {
                for (Usuario u : usuarios) {
                    if (u.getRol() == Rol.Empleado) {
                        cbEmpleado.getItems().add(u);
                    }
                }
            }

            cbEmpleado.setCellFactory(param -> new javafx.scene.control.ListCell<Usuario>() {
                @Override
                protected void updateItem(Usuario item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " " + item.getApellidos() + " (" + item.getNombreUsuario() + ")");
                    }
                }
            });
            cbEmpleado.setButtonCell(cbEmpleado.getCellFactory().call(null));

        } catch (Exception e) {
            logger.severe("Error al cargar empleados: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        Polideportivo poliSeleccionado = cbPolideportivo.getValue();
        String tipoClase = txtPistaTipo.getText() != null ? txtPistaTipo.getText().trim() : "";
        String localizacion = txtPistaLocalizacion.getText() != null ? txtPistaLocalizacion.getText().trim() : "";
        double precioClase = spinnerClasePrecio.getValue();
        String estadoClase = cbClaseEstado.getValue();

        // Validaciones básicas
        if (poliSeleccionado == null || tipoClase.isEmpty() || localizacion.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, selecciona un polideportivo, introduce el tipo y la localizacion.");
            return;
        }

        logger.info("Intentando registrar nueva zona/pista: " + tipoClase + " en polideportivo: " + poliSeleccionado.getNombre());

        try {
            // Invocamos el método del controlador para insertar la zona/pista en la BD
            // (nombre, tipo, idPolideportivo, precioHora, estado)
            controlador.insertarZona(tipoClase, tipoClase, poliSeleccionado.getId(), precioClase, estadoClase);

            logger.info("Clase/Zona registrada con exito.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La clase/pista ha sido registrada correctamente.");

            // Limpiamos los campos del formulario tras el éxito
            cbPolideportivo.setValue(null);
            cbEmpleado.setValue(null);
            txtPistaTipo.clear();
            txtPistaLocalizacion.clear();
            spinnerClasePrecio.getValueFactory().setValue(15.0);
            cbClaseEstado.setValue("Libre");

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
            
            logger.info("Volviendo al submenu de polideportivos desde añadir clases");
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