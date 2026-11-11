package com.mycompany.reto1intermodular;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import modelo.Usuario;

/**
 * FXML Controller class para el submenú de gestión de polideportivos
 * 
 * @author Inés
 */
public class AdministradorGestionPolideportivoController extends LoggerController implements Initializable {

    private Usuario usuarioActual;

    @FXML
    private Button btnAnadirPolideportivo;
    @FXML
    private Button btnAnadirPistas;
    @FXML
    private Button btnAnadirClases;
    @FXML
    private Button btnAnadirActividades;
    @FXML
    private Button btnAtras;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        logger.info("Submenú de gestion de polideportivos abierto por: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización básica
    }    

    @FXML
    private void abrirAnadirPolideportivo(ActionEvent event) {
        // Corregido: Apunta a la vista de añadir polideportivo que creamos
        cambiarEscena("/com/mycompany/reto1intermodular/AdministradorAnadirPolideportivo.fxml", btnAnadirPolideportivo, "Añadir Polideportivo");
    }

    @FXML
    private void abrirAnadirPistas(ActionEvent event) {
        cambiarEscena("/com/mycompany/reto1intermodular/AdministradorAnadirPistas.fxml", btnAnadirPistas, "Añadir Pistas");
    }

    @FXML
    private void abrirAnadirClases(ActionEvent event) {
        cambiarEscena("/com/mycompany/reto1intermodular/AdministradorAnadirClases.fxml", btnAnadirClases, "Añadir Clases");
    }

    @FXML
    private void abrirAnadirActividades(ActionEvent event) {
        cambiarEscena("/com/mycompany/reto1intermodular/AdministradorAnadirActividades.fxml", btnAnadirActividades, "Añadir Actividades");
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorMenu.fxml"));
            Parent root = loader.load();

            AdministradorMenuController adminController = loader.getController();
            adminController.setUsuario(usuarioActual);

            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            logger.info("Volviendo al menu principal de administrador");
        } catch (IOException e) {
            logger.severe("Error al volver al menu de administrador: " + e.getMessage());
            System.out.println("ERROR: " + e);
        }
    }

    // Método auxiliar privado adaptado para propagar el usuario si la vista destino lo soporta
    private void cambiarEscena(String fxmlRuta, Button botonReferencia, String nombreVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
            Parent root = loader.load();
            
            // Si el controlador de destino tiene método para recibir el usuario, se lo pasamos
            Object controller = loader.getController();
            if (controller != null) {
                try {
                    // Intenta invocar setUsuario por si el controlador destino lo implementa
                    controller.getClass().getMethod("setUsuario", Usuario.class).invoke(controller, usuarioActual);
                } catch (Exception e) {
                    // Si la vista no usa setUsuario, simplemente se ignora sin romper nada
                }
            }

            Scene escena = botonReferencia.getScene();
            escena.setRoot(root);
            
            logger.info("Navegando a la vista: " + nombreVentana);
        } catch (IOException e) {
            logger.severe("Error al cargar la vista " + fxmlRuta + ": " + e.getMessage());
            System.out.println("ERROR al cargar " + fxmlRuta + ": " + e);
        }
    }
}