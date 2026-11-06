package com.mycompany.reto1intermodular;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author aldair
 */
public class UsuarioBienvenidaController extends LoggerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML 
    private StackPane root;
    @FXML 
    private Region overlay;

    
    
@FXML
private void abrirLogin() throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioInicioSesion.fxml"));

    Parent card = loader.load();

    // Obtenemos el controlador del Login
    UsuarioInicioSesionController login = loader.getController();

    Stage popup = new Stage();

    Window owner = root.getScene().getWindow();

    popup.initOwner(owner);
    popup.initModality(Modality.APPLICATION_MODAL);
    popup.initStyle(StageStyle.TRANSPARENT);

    Scene scene = new Scene(card);
    scene.setFill(Color.TRANSPARENT);

    scene.getStylesheets().add(getClass().getResource("/styles/ApartadoLoginCSS.css").toExternalForm());

    popup.setScene(scene);

    login.setStage(popup);

    // Centramos el popup
    popup.setOnShown(e -> {
        popup.setX(owner.getX() + (owner.getWidth() - popup.getWidth()) / 2);
        popup.setY(owner.getY() + (owner.getHeight() - popup.getHeight()) / 2);
    });

    overlay.setVisible(true);

    try {
        popup.showAndWait();
    } finally {
        overlay.setVisible(false);
    }
    
    if (login.isLoginCorrecto()) {

    // Guardamos el usuario en la sesión (una sola vez)
    Sesion.iniciar(login.getUsuarioAutenticado());

    // Cargamos el menú
    Parent menu = FXMLLoader.load(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));
    root.getScene().setRoot(menu);

    logger.info("UsuarioBienvenidaController: Inicio de sesión correcto");
    }
    }      
}
