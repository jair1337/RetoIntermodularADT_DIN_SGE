package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import modelo.Polideportivo;
import modelo.Rol;
import modelo.Usuario;

/**
 * FXML Controller class para añadir actividades
 * 
 * @author Inés
 */
public class AdministradorAnadirActividadesController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();

    @FXML
    private ComboBox<Polideportivo> cbPolideportivo;
    @FXML
    private ComboBox<Usuario> cbEmpleado;
    @FXML
    private TextField txtActTitulo;
    @FXML
    private TextField txtActPistaZona;
    @FXML
    private TextField txtActLocalizacion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private Spinner<Integer> spinnerHoraInicio;
    @FXML
    private Spinner<Integer> spinnerMinutoInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Spinner<Integer> spinnerHoraFin;
    @FXML
    private Spinner<Integer> spinnerMinutoFin;
    @FXML
    private Spinner<Double> spinnerActPrecio;
    @FXML
    private TextField txtActAforo;
    @FXML
    private Spinner<Integer> spinnerActPuntos;
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
        // Spinner de Precio (0.0 a 500.0)
        spinnerActPrecio.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 500.0, 10.0, 1.0));
        // Spinner de Puntos (0 a 1000)
        spinnerActPuntos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 50, 5));

        // Spinners de Hora (0 a 23) y Minutos (0 a 59) para Inicio
        spinnerHoraInicio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 9));
        spinnerMinutoInicio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 5));

        // Spinners de Hora (0 a 23) y Minutos (0 a 59) para Fin
        spinnerHoraFin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 10));
        spinnerMinutoFin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 5));

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
                    // Filtramos únicamente los usuarios que tengan rol de Empleado
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
        try {
            Polideportivo poliSeleccionado = cbPolideportivo.getValue();
            Usuario empleadoSeleccionado = cbEmpleado.getValue();
            
            String titulo = txtActTitulo.getText() != null ? txtActTitulo.getText().trim() : "";
            String pistaZona = txtActPistaZona.getText() != null ? txtActPistaZona.getText().trim() : "";
            String localizacion = txtActLocalizacion.getText() != null ? txtActLocalizacion.getText().trim() : "";
            
            LocalDate localDateInicio = dpFechaInicio.getValue();
            LocalDate localDateFin = dpFechaFin.getValue();
            
            double precio = spinnerActPrecio.getValue();
            int aforo = txtActAforo.getText() != null && !txtActAforo.getText().isEmpty() ? Integer.parseInt(txtActAforo.getText().trim()) : 0;
            int puntos = spinnerActPuntos.getValue();

            if (poliSeleccionado == null || titulo.isEmpty() || localDateInicio == null || localDateFin == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, selecciona un polideportivo, el título y ambas fechas.");
                return;
            }

            // Ya no necesitamos pedir el ID de pista, asignamos un valor por defecto o vacío según prefieras ("General" o similar)
            String tipo = ""; 
            String idEmpleado = (empleadoSeleccionado != null) ? empleadoSeleccionado.getId() : "";

            // Combinamos fecha y hora seleccionadas
            LocalDateTime ldtInicio = localDateInicio.atTime(spinnerHoraInicio.getValue(), spinnerMinutoInicio.getValue());
            LocalDateTime ldtFin = localDateFin.atTime(spinnerHoraFin.getValue(), spinnerMinutoFin.getValue());

            Date fechaInicio = Date.from(ldtInicio.atZone(ZoneId.systemDefault()).toInstant());
            Date fechaFin = Date.from(ldtFin.atZone(ZoneId.systemDefault()).toInstant());

            logger.info("Intentando registrar nueva actividad: " + titulo);

            controlador.insertarActividad(poliSeleccionado.getId(), titulo, tipo, pistaZona, fechaInicio, fechaFin, precio, aforo, puntos, idEmpleado);

            logger.info("Actividad registrada con exito.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La actividad ha sido registrada correctamente.");

            // Limpieza de campos
            cbPolideportivo.setValue(null);
            cbEmpleado.setValue(null);
            txtActTitulo.clear();
            txtActPistaZona.clear();
            txtActLocalizacion.clear();
            dpFechaInicio.setValue(null);
            dpFechaFin.setValue(null);
            txtActAforo.clear();
            spinnerActPrecio.getValueFactory().setValue(10.0);
            spinnerActPuntos.getValueFactory().setValue(50);
            spinnerHoraInicio.getValueFactory().setValue(9);
            spinnerMinutoInicio.getValueFactory().setValue(0);
            spinnerHoraFin.getValueFactory().setValue(10);
            spinnerMinutoFin.getValueFactory().setValue(0);

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "El aforo debe ser un número entero valido.");
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
            
            logger.info("Volviendo al submenu de polideportivos desde añadir actividades");
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