/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reto1intermodular;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AdministradorEliminarUsuarioControllerTest
        extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(
                        "AdministradorEliminarUsuario.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testCamposResultadoSoloLectura() {

        TextField usuario =
                lookup("#txtUsuario").queryAs(TextField.class);

        TextField nombre =
                lookup("#txtNombre").queryAs(TextField.class);

        assertFalse(usuario.isEditable());
        assertFalse(nombre.isEditable());
    }

    @Test
    public void testControlesPrincipalesVisibles() {

        verifyThat("#txtDni", isVisible());
        verifyThat("#btnBuscar", isVisible());
        verifyThat("#txtUsuario", isVisible());
        verifyThat("#txtNombre", isVisible());
        verifyThat("#btnDelete", isVisible());
    }

    @Test
    public void testBuscarConDniVacioMuestraAviso() {

        clickOn("#btnBuscar");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Por favor, introduce un DNI para buscar al usuario.",
                dialogo.getContentText());
    }

    @Test
    public void testDeleteSinUsuarioSeleccionadoMuestraAviso() {

        clickOn("#btnDelete");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Primero debes buscar y cargar un usuario mediante su DNI.",
                dialogo.getContentText());
    }
}
