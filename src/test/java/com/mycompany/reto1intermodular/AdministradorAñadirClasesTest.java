/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reto1intermodular;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AdministradorAnadirClasesControllerTest
        extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(
                        "AdministradorAnadirClases.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testControlesPrincipalesVisibles() {

        verifyThat("#txtPolyId", isVisible());
        verifyThat("#txtPistaId", isVisible());
        verifyThat("#txtPistaTipo", isVisible());
        verifyThat("#spinnerClasePrecio", isVisible());
        verifyThat("#cbClaseEstado", isVisible());
        verifyThat("#btnRegistrar", isVisible());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testValoresIniciales() {

        Spinner<Double> precio =
                lookup("#spinnerClasePrecio").queryAs(Spinner.class);

        ComboBox<String> estado =
                lookup("#cbClaseEstado").queryAs(ComboBox.class);

        assertEquals(5.0, precio.getValue());
        assertEquals("Activa", estado.getValue());
        assertEquals(3, estado.getItems().size());
    }

    @Test
    public void testRegistrarSinDatosMuestraAviso() {

        clickOn("#btnRegistrar");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Por favor, introduce el ID del polideportivo, el identificador de la clase y su tipo.",
                dialogo.getContentText());
    }

    @Test
    public void testFaltaTipoClaseMuestraAviso() {

        clickOn("#txtPolyId").write("1");
        clickOn("#txtPistaId").write("YogaBasico");

        clickOn("#btnRegistrar");

        verifyThat(".dialog-pane", isVisible());
    }
}