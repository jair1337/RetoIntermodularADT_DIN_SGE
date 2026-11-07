package com.mycompany.reto1intermodular;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClienteContactoController extends LoggerController implements Initializable {

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnEnviar;
    @FXML
    private TextArea taComentario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void enviarMensaje() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        
        alert.setTitle("Contactanos");
        alert.setHeaderText("¡Mensaje enviado!");
        //alert.setContentText("Los datos se han guardado correctamente en la base de datos.");

        taComentario.setText("");
        alert.showAndWait();    
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
