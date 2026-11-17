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

public class AdministradorGestionPolideportivoControllerTest
        extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(
                        "AdministradorGestionPolideportivo.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testBotonesVisibles() {

        verifyThat("#btnAnadirPolideportivo", isVisible());
        verifyThat("#btnAnadirPistas", isVisible());
        verifyThat("#btnAnadirClases", isVisible());
        verifyThat("#btnAnadirActividades", isVisible());
        verifyThat("#btnAtras", isVisible());
    }

    @Test
    public void testAbrirAnadirPolideportivo() {

        clickOn("#btnAnadirPolideportivo");

        verifyThat("#txtNombre", isVisible());
    }

    @Test
    public void testAbrirAnadirPistas() {

        clickOn("#btnAnadirPistas");

        verifyThat("#txtPistaId", isVisible());
    }

    @Test
    public void testAbrirAnadirClases() {

        clickOn("#btnAnadirClases");

        verifyThat("#spinnerClasePrecio", isVisible());
    }

    @Test
    public void testAbrirAnadirActividades() {

        clickOn("#btnAnadirActividades");

        verifyThat("#txtActTitulo", isVisible());
    }
}
