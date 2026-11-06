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
import modelo.Actividad;
import modelo.Reserva;
/**
 * FXML Controller class
 *
 * @author aldair
 */
public class ClienteCancelarController extends LoggerController implements Initializable {

    private final Controlador controlador = new Controlador();

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnCancelar;
    
    @FXML
    private TableView tbActividades;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TableColumn colImporte;
    @FXML
    private TableColumn colEstado;
    
    private ObservableList<Actividad> listaClases;
    private ObservableList<Reserva> listaReservas;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTabla();
    } 
    
    private void cargarTabla() {
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    //colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
    colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
       
    try {
        listaReservas = FXCollections.observableArrayList(controlador.obtenerReservas());
        tbActividades.setItems(listaReservas);
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
