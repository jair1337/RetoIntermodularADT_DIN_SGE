/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.reto1intermodular;

import static com.mycompany.reto1intermodular.LoggerController.logger;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import modelo.Logro;

/**
 * FXML Controller class
 *
 * @author aldair
 */
public class ClienteLogrosPuntosController implements Initializable {

    private final Controlador controlador = new Controlador();
    
    @FXML
    private ImageView imgPuntos;
    @FXML
    private Label lblPuntos;
    @FXML
    private Button btnAtras;
    
    @FXML
    private TableView<Logro> tbLogros;
    @FXML
    private TableColumn<Logro, String> colNombre;
    @FXML
    private TableColumn<Logro, String> colDescripcion;
    @FXML
    private TableColumn<Logro, String> colIcono;
    
    private ObservableList<Logro> listaLogros;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarLogros();
    }
    
    private void cargarLogros() {
    colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    colIcono.setCellValueFactory(new PropertyValueFactory<>("iconoUrl"));
       
    try {
        listaLogros = FXCollections.observableArrayList(controlador.verLogros());
        tbLogros.setItems(listaLogros);
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
            logger.severe("ERROR: " + e);
        }
    }
    
}
