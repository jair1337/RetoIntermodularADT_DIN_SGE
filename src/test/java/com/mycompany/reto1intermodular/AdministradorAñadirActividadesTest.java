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
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AdministradorAnadirActividadesControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(
                        "AdministradorAnadirActividades.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testControlesPrincipalesVisibles() {

        verifyThat("#txtPolyId", isVisible());
        verifyThat("#txtActTitulo", isVisible());
        verifyThat("#txtActFechaInicio", isVisible());
        verifyThat("#txtActFechaLimite", isVisible());
        verifyThat("#spinnerActPrecio", isVisible());
        verifyThat("#spinnerActPuntos", isVisible());
        verifyThat("#btnRegistrar", isVisible());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testValoresInicialesSpinners() {

        Spinner<Double> precio =
                lookup("#spinnerActPrecio").queryAs(Spinner.class);

        Spinner<Integer> puntos =
                lookup("#spinnerActPuntos").queryAs(Spinner.class);

        assertEquals(10.0, precio.getValue());
        assertEquals(50, puntos.getValue());
    }

    @Test
    public void testRegistrarSinCamposObligatoriosMuestraAviso() {

        clickOn("#btnRegistrar");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Por favor, rellene al menos el ID del polideportivo, el título y las fechas.",
                dialogo.getContentText());
    }

    @Test
    public void testAforoInvalidoMuestraError() {

        clickOn("#txtPolyId").write("1");
        clickOn("#txtActTitulo").write("Futbol");

        clickOn("#txtActFechaInicio")
                .write("2030-05-10 10:00");

        clickOn("#txtActFechaLimite")
                .write("2030-05-10 12:00");

        clickOn("#txtActAforo")
                .write("abc");

        clickOn("#btnRegistrar");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "El aforo debe ser un número entero válido.",
                dialogo.getContentText());
    }
}
