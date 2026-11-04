package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Actividad;
import modelo.Pista;

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
    private TableView tbActividad;
    
    private ObservableList<Actividad> clienteActividad;
    
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TableColumn colZona;
    @FXML
    private TableColumn collLocalizacion;
    /*
    @FXML
    private TableColumn colInicio;
    @FXML
    private TableColumn colFin;
    @FXML
    private TableColumn colAforo;
    @FXML
    private TableColumn colPuntos;
    */
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
    
    private ObservableList<Pista> clientePista;
    
    @FXML
    private Button btnRegistrarseActividad;
    @FXML
    private Button btnReservarPista;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    cargarTabla();
    }
    
    // Con observableList aaa
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
            System.out.println("ERROR: " + e);
        }
    }
}
