package com.mycompany.reto1intermodular;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class EmpleadoMenuController {

    @FXML
    private Button btnPerfil;

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
        Node boton = (Node) event.getSource();

        Alert aviso = new Alert(Alert.AlertType.CONFIRMATION);
        aviso.setTitle("Cerrar sesión");
        aviso.setHeaderText(null);
        aviso.setContentText("¿Estás seguro de que quieres cerrar sesión?");
        aviso.initOwner(boton.getScene().getWindow());

        if (aviso.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            Parent bienvenida = FXMLLoader.load(
                    getClass().getResource("UsuarioBienvenida.fxml"));
            boton.getScene().setRoot(bienvenida);
            Sesion.cerrar();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                    "No se pudo volver a la pantalla de bienvenida. "
                    + e.getMessage()).showAndWait();
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