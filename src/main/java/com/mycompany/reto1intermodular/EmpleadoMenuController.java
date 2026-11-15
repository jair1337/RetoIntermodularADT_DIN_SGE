package com.mycompany.reto1intermodular;

import static com.mycompany.reto1intermodular.LoggerController.logger;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class EmpleadoMenuController {

    @FXML
    private Button btnPerfil;
      @FXML
    private Button btnCerrarSesion;

    @FXML
    private void abrirPerfil() {
        abrirVista("Perfil.fxml");
    }

    @FXML
    private void abrirGestion() {
        abrirVista("EmpleadoGestion.fxml");
    }

    @FXML
    private void abrirHorario() {
        abrirVista("EmpleadoHorario.fxml");
    }

      @FXML
    private void cerrarSesion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres cerrar sesion?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioBienvenida.fxml"));
                Parent root = loader.load();
                
                Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
                Scene scene = new Scene(root);
                
                // Inyectamos la hoja de estilos por código para garantizar que cargue el diseño azul
                try {
                    String css = getClass().getResource("/styles/ApartadoLoginCSS.css").toExternalForm();
                    scene.getStylesheets().add(css);
                } catch (Exception e) {
                    try {
                        // Ruta alternativa por si estuviera empaquetada en el paquete
                        String cssAlt = getClass().getResource("/com/mycompany/reto1intermodular/styles/ApartadoLoginCSS.css").toExternalForm();
                        scene.getStylesheets().add(cssAlt);
                    } catch (Exception ex) {
                        System.out.println("No se pudo cargar el CSS automáticamente: " + ex.getMessage());
                    }
                }
                
                stage.setScene(scene);
                Sesion.cerrar();
                stage.setTitle("TxandaSport - Bienvenida");
                stage.show();
                
                logger.info("Sesion de empleado cerrada correctamente y vuelta a la bienvenida con estilos.");
            } catch (IOException e) {
                logger.severe("Error al cerrar sesion: " + e.getMessage());
                System.out.println("ERROR al cerrar sesion: " + e);
                e.printStackTrace();
            }
        }
    }
    private boolean abrirVista(String archivo) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(archivo));
            btnPerfil.getScene().setRoot(vista);
            return true;
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                    "No se pudo abrir " + archivo + ". " + e.getMessage())
                    .showAndWait();
            return false;
        }
    }
}