package com.mycompany.reto1intermodular;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import modelo.Rol;
import static modelo.Rol.Administrador;
import static modelo.Rol.Empleado;

/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class UsuarioBienvenidaController extends LoggerController implements Initializable {

    @FXML
    private Label lblSencillez;
    @FXML
    private Label lblSeguridad;
    @FXML
    private Label lblResultados;
    @FXML
    private Label lblTxandaSport;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblSubtitulo;
    @FXML
    private Button btnComenzar;
    @FXML
    private Label IconoIzquierdo;
    @FXML
    private Label IconoCentral;
    @FXML
    private Label IconoDerecho;

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

        // Centrar el popup
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

            // Guardar el usuario en la sesión (una sola vez)
            Sesion.iniciar(login.getUsuarioAutenticado());

            // Menú según el rol del usuario que ha iniciado sesión
            Rol rol = login.getUsuarioAutenticado().getRol();
            Parent menu = FXMLLoader.load(getClass().getResource(rutaMenu(rol)));
            root.getScene().setRoot(menu);

            logger.info("UsuarioBienvenidaController: Inicio de sesión correcto");
            }
        }

    private String rutaMenu(Rol rol) {
        String base = "/com/mycompany/reto1intermodular/";

        switch (rol) {
            case Empleado:
                return base + "EmpleadoMenu.fxml";
            case Administrador:
                return base + "AdministradorMenu.fxml";
            default:
                return base + "ClienteMenu.fxml";
        }
    }
}
