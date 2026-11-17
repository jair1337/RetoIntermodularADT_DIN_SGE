/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reto1intermodular;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AdministradorMenuControllerTest
        extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "AdministradorMenu.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testBotonesMenuVisibles() {

        verifyThat("#btnPerfil", isVisible());
        verifyThat("#btnCerrarSesion", isVisible());
        verifyThat("#btnGestionarPolideportivo", isVisible());
        verifyThat("#btnRegistrarEmpleado", isVisible());
        verifyThat("#btnModificarHorario", isVisible());
        verifyThat("#btnEliminarUsuario", isVisible());
    }

    @Test
    public void testAbrirGestionPolideportivo() {

        clickOn("#btnGestionarPolideportivo");

        verifyThat("#btnAnadirPolideportivo", isVisible());
    }

    @Test
    public void testAbrirRegistroEmpleado() {

        clickOn("#btnRegistrarEmpleado");

        verifyThat("#txtNombreUsuario", isVisible());
    }

    @Test
    public void testAbrirModificarHorario() {

        clickOn("#btnModificarHorario");

        verifyThat("#cbEmpleado", isVisible());
    }

    @Test
    public void testAbrirEliminarUsuario() {

        clickOn("#btnEliminarUsuario");

        verifyThat("#txtDni", isVisible());
    }
}