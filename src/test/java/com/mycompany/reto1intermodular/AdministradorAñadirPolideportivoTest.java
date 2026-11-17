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

public class AdministradorAnadirPolideportivoControllerTest
        extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(
                        "AdministradorAnadirPolideportivo.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testCamposPrincipalesVisibles() {

        verifyThat("#txtNombre", isVisible());
        verifyThat("#txtCalle", isVisible());
        verifyThat("#txtNumero", isVisible());
        verifyThat("#txtLocalidad", isVisible());
        verifyThat("#txtProvincia", isVisible());
        verifyThat("#btnRegistrar", isVisible());
    }

    @Test
    public void testRegistrarSinDatosMuestraAviso() {

        clickOn("#btnRegistrar");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Por favor, rellena al menos el nombre, calle, localidad y provincia.",
                dialogo.getContentText());
    }

    @Test
    public void testFaltaNombreMuestraAviso() {

        clickOn("#txtCalle").write("Gran Via");
        clickOn("#txtLocalidad").write("Bilbao");
        clickOn("#txtProvincia").write("Bizkaia");

        clickOn("#btnRegistrar");

        verifyThat(".dialog-pane", isVisible());
    }

    @Test
    public void testFaltaProvinciaMuestraAviso() {

        clickOn("#txtNombre").write("Polideportivo Norte");
        clickOn("#txtCalle").write("Gran Via");
        clickOn("#txtLocalidad").write("Bilbao");

        clickOn("#btnRegistrar");

        verifyThat(".dialog-pane", isVisible());
    }
}