package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import modelo.Actividad;
import modelo.Estado;
import modelo.Pista;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClienteDisponibilidadController extends LoggerController implements Initializable {
    
    private final Controlador controlador = new Controlador();
    
    @FXML
    private Button btnAtras;
    
    @FXML
    private Button btnRegistrarseActividad;
    
    private ObservableList<Actividad> clienteActividad;
    
    @FXML
    private Button btnReservarPista;
    
    private ObservableList<Pista> clientePista;
    
    // Actividades
    @FXML private TableView<Actividad> tbActividad;
    @FXML private TableColumn<Actividad, String> colTitulo;
    @FXML private TableColumn<Actividad, String> colTipo;
    @FXML private TableColumn<Actividad, String> colZona;
    @FXML private TableColumn<Actividad, String> collLocalizacion;
    @FXML private TableColumn<Actividad, Double> colPrecio;

    // Pistas
    @FXML private TableView<Pista> tbPista;
    @FXML private TableColumn<Pista, String> colNombrePista;
    @FXML private TableColumn<Pista, String> colTipoPista;
    @FXML private TableColumn<Pista, String> colLocalizacionPista;
    @FXML private TableColumn<Pista, Double> colPrecioPista;
    @FXML private TableColumn<Pista, Estado> colEstadoPista;
    
    /*
    @FXML
    private TableView tbActividad;
    
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TableColumn colZona;
    @FXML
    private TableColumn collLocalizacion;
    @FXML
    private TableColumn colPrecio;
    
    @FXML
    private TableView tbPista;
    @FXML
    private TableColumn colNombrePista;
    @FXML
    private TableColumn colTipoPista;
    @FXML
    private TableColumn colLocalizacionPista;
    @FXML
    private TableColumn colPrecioPista;
    @FXML
    private TableColumn colEstadoPista;
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    cargarTabla();
    
    // Deshabilitado mientras no haya fila seleccionada
    btnRegistrarseActividad.disableProperty().bind(tbActividad.getSelectionModel().selectedItemProperty().isNull());
    btnReservarPista.disableProperty().bind(tbPista.getSelectionModel().selectedItemProperty().isNull());
    }
    
    @FXML
    private void registroEnActividad() {
        Actividad seleccionada = tbActividad.getSelectionModel().getSelectedItem();
        Usuario usuario = Sesion.getUsuario();

        if (seleccionada == null || usuario == null) {
            return;
        }

        try {
            controlador.registrarseEnActividad(usuario.getId(), seleccionada.getId());
            new Alert(Alert.AlertType.INFORMATION, "Te has registrado en: " + seleccionada.getTitulo()).showAndWait();
            logger.info("Usuario " + usuario.getNombreUsuario() + " registrado en actividad " + seleccionada.getId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        }
    }
    
    @FXML
    private void reservarPista() {
        Pista seleccionada = tbPista.getSelectionModel().getSelectedItem();
        Usuario usuario = Sesion.getUsuario();

        if (seleccionada == null || usuario == null) {
            return;
        }

        // Diálogo sencillo para elegir día y hora
        Dialog<ButtonType> dialogo = new Dialog<>();
        dialogo.setTitle("Reservar " + seleccionada.getNombre());

        DatePicker fecha = new DatePicker(LocalDate.now());
        Spinner<Integer> hora = new Spinner<>(8, 22, 10);

        VBox contenido = new VBox(10, new Label("Día:"), fecha, new Label("Hora de inicio (1 hora):"), hora);
        dialogo.getDialogPane().setContent(contenido);
        dialogo.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> resultado = dialogo.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.OK || fecha.getValue() == null) {
            return;
        }

        LocalDateTime inicio = fecha.getValue().atTime(hora.getValue(), 0);
        LocalDateTime fin = inicio.plusHours(1);

        if (inicio.isBefore(LocalDateTime.now())) {
            new Alert(Alert.AlertType.WARNING, "No puedes reservar en el pasado").showAndWait();
            return;
        }

        try {
            controlador.reservarPista(
                    usuario.getId(),
                    seleccionada.getId(),
                    Date.from(inicio.atZone(ZoneId.systemDefault()).toInstant()),
                    Date.from(fin.atZone(ZoneId.systemDefault()).toInstant()));
            new Alert(Alert.AlertType.INFORMATION, "Reserva realizada").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        }
    }
    
    
    
    private void cargarTabla() {
    // De la tabla actividades
    colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    colZona.setCellValueFactory(new PropertyValueFactory<>("pistaZona"));
    collLocalizacion.setCellValueFactory(new PropertyValueFactory<>("localizacion"));
    colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));    
        
    // De la tabla pistas    
    colNombrePista.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colTipoPista.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    colLocalizacionPista.setCellValueFactory(new PropertyValueFactory<>("localizacion"));
    colPrecioPista.setCellValueFactory(new PropertyValueFactory<>("precioHora"));
    colEstadoPista.setCellValueFactory(new PropertyValueFactory<>("estado"));    
    
    try {
        clienteActividad = FXCollections.observableArrayList(controlador.obtenerActividades());
        tbActividad.setItems(clienteActividad);
    } catch (Exception e) {       
        System.out.println("ERROR:" + e);
    }  
    
    try {       
        clientePista = FXCollections.observableArrayList(controlador.obtenerPistas());
        tbPista.setItems(clientePista);
    } catch (Exception e) {       
        System.out.println("ERROR:" + e);
    }
    
    }
    
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));

            Parent root = loader.load();
            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            /*
            Stage stage = (Stage) escena.getWindow();
            stage.setWidth(900);
            stage.setHeight(560);
            stage.centerOnScreen();
            */
            logger.info("Volviendo al escenario Menu");
        } catch (IOException e) {
            logger.severe("ERROR:" + e);
        }
    }
}