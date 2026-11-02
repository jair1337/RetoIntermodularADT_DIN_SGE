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

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClienteDisponibilidadController implements Initializable {

    @FXML
    private Button btnAtras;
    
    private final Controlador controlador = new Controlador();
  
    @FXML
    private Button btnRegistrarse;
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
    @FXML
    private TableColumn colInicio;
    @FXML
    private TableColumn colFin;
    @FXML
    private TableColumn colAforo;
    @FXML
    private TableColumn colPuntos;
    @FXML
    private TableColumn colPrecio;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    cargarTabla();
    }
    
    // Con observableList aaa
    private void cargarTabla() {
    //actividadID.setCellValueFactory(new PropertyValueFactory<>("id"));
    colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    colZona.setCellValueFactory(new PropertyValueFactory<>("pistaZona"));
    collLocalizacion.setCellValueFactory(new PropertyValueFactory<>("localizacion"));
    colInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
    colFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
    colAforo.setCellValueFactory(new PropertyValueFactory<>("aforoMaximo"));
    colPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));
    colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    
    try {
        clienteActividad = FXCollections.observableArrayList(controlador.obtenerActividades());
        tbActividad.setItems(clienteActividad);
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
            
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    /*
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));
            
            Parent root = loader.load();

            Stage stage = (Stage) btnAtras.getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar sesión");

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    */
}
