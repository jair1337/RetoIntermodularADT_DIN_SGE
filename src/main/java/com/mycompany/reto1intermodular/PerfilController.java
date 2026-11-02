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
 * @author Albeiro.Mamani
 */
public class PerfilController implements Initializable {

    @FXML
    private Button btnAtras;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) btnAtras.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    
}
