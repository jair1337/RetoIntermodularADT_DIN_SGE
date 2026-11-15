package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Actividad;
import modelo.EstadoPista;
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
    @FXML private TableColumn<Pista, EstadoPista> colEstadoPista;
    
    @FXML
    private Tab tabClases;
    @FXML
    private Tab tabPistas;
     
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

        if (seleccionada == null || Sesion.getUsuario() == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClientePago.fxml"));
            Parent root = loader.load();

            // Le pasamos la pista elegida a la pantalla de pago
            ClientePagoController pago = loader.getController();
            pago.setPista(seleccionada);

            btnReservarPista.getScene().setRoot(root);

            logger.info("Abriendo pantalla de pago para la pista " + seleccionada.getNombre());
        } catch (IOException e) {
            logger.severe("ERROR: " + e);
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
        
            logger.info("Volviendo al escenario Menu");
        } catch (IOException e) {
            logger.severe("ERROR:" + e);
        }
    }

    
}