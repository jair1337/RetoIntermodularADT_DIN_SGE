/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.reto1intermodular;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClientePagoController implements Initializable {


    @FXML
    private Button btnAtras;
    @FXML
    private Button btnPagar;
    @FXML
    private TextField lblPista;
    @FXML
    private DatePicker dtrFecha;
    @FXML
    private Spinner<?> sprCantidad;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
