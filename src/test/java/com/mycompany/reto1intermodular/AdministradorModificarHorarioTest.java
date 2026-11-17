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
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AdministradorModificarHorarioControllerTest
        extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(
                        "AdministradorModificarHorario.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testControlesVisibles() {

        verifyThat("#cbEmpleado", isVisible());
        verifyThat("#cbDiaEliminar", isVisible());
        verifyThat("#dpDiaAnadir", isVisible());
        verifyThat("#btnBuscar", isVisible());
        verifyThat("#btnDelete", isVisible());
        verifyThat("#btnAnadir", isVisible());
    }

    @Test
    public void testBuscarSinEmpleadoSeleccionado() {

        clickOn("#btnBuscar");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Por favor, selecciona un empleado primero.",
                dialogo.getContentText());
    }

    @Test
    public void testEliminarSinDatosSeleccionados() {

        clickOn("#btnDelete");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Selecciona un empleado y la actividad/horario a eliminar.",
                dialogo.getContentText());
    }

    @Test
    public void testAnadirSinEmpleadoNiFecha() {

        clickOn("#btnAnadir");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Selecciona un empleado y una fecha válida en el calendario.",
                dialogo.getContentText());
    }
}
