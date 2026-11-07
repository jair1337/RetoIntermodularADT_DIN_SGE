package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ItemReserva;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClienteCancelarController extends LoggerController implements Initializable {
 
    private final Controlador controlador = new Controlador();
 
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnCancelar;
 
    @FXML
    private TableView<ItemReserva> tbActividades;
    @FXML
    private TableColumn<ItemReserva, String> colId;
    @FXML
    private TableColumn<ItemReserva, String> colTipo;
    @FXML
    private TableColumn<ItemReserva, Double> colImporte;
    @FXML
    private TableColumn<ItemReserva, String> colEstado;
 
    // OPCIONALES: si añades estas dos columnas en SceneBuilder (fx:id colNombre y colFecha)
    // se rellenan solas; si no existen en el FXML se quedan a null y se ignoran.
    @FXML
    private TableColumn<ItemReserva, String> colNombre;
    @FXML
    private TableColumn<ItemReserva, String> colFecha;
 
    private ObservableList<ItemReserva> listaItems;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDatos();
 
        // El botón hace lo mismo aunque no tenga onAction en el FXML
        btnCancelar.setOnAction(e -> cancelar());
 
        // Deshabilitado si no hay fila seleccionada o si la reserva ya está cancelada
        btnCancelar.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            ItemReserva seleccionado = tbActividades.getSelectionModel().getSelectedItem();
            return seleccionado == null || "Cancelada".equals(seleccionado.getEstado());
        }, tbActividades.getSelectionModel().selectedItemProperty()));
    }
 
    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
 
        if (colNombre != null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        }
        if (colFecha != null) {
            colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaTexto"));
        }
    }
 
    // Carga (o recarga) las actividades y pistas del usuario en sesión
    private void cargarDatos() {
        Usuario usuario = Sesion.getUsuario();
        if (usuario == null) {
            logger.warning("No hay ningún usuario en sesión");
            return;
        }
 
        try {
            listaItems = FXCollections.observableArrayList(controlador.obtenerReservasUsuario(usuario.getId()));
            tbActividades.setItems(listaItems);
        } catch (Exception e) {
            logger.severe("ERROR: " + e);
        }
    }
 
    @FXML
    private void cancelar() {
        ItemReserva seleccionado = tbActividades.getSelectionModel().getSelectedItem();
        Usuario usuario = Sesion.getUsuario();
 
        if (seleccionado == null || usuario == null) {
            return;
        }
 
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Seguro que quieres cancelar \"" + seleccionado.getNombre() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirmacion.setHeaderText(null);
        confirmacion.setTitle("Cancelar");
 
        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isEmpty() || respuesta.get() != ButtonType.YES) {
            return;
        }
 
        try {
            if ("Actividad".equals(seleccionado.getTipo())) {
                // Te quita de los participantes: desaparece de la lista
                controlador.cancelarActividad(usuario.getId(), seleccionado.getId());
            } else {
                // La reserva pasa a estado "Cancelada"
                controlador.cancelarReserva(usuario.getId(), seleccionado.getId());
            }
 
            logger.info("Cancelado: " + seleccionado.getTipo() + " " + seleccionado.getNombre());
            cargarDatos();
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
            logger.severe("ERROR: " + e);
        }
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
            logger.severe("ERROR:" + e);
        }
    }
}