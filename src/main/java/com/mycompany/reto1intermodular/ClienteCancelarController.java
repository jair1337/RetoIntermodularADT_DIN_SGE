package com.mycompany.reto1intermodular;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author aldair
 */
public class ClienteCancelarController extends LoggerController implements Initializable {


    @FXML
    private Button btnAtras;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));

            Parent root = loader.load();
            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            Stage stage = (Stage) escena.getWindow();
            stage.setWidth(900);
            stage.setHeight(560);
            stage.centerOnScreen();
            logger.info("Volviendo al escenario Menu");
        } catch (IOException e) {
            logger.info("Error en la salida/entrada");
            System.out.println("ERROR: " + e);
        }
    }
}
