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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AdministradorRegistroEmpleadoControllerTest
        extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(
                        "AdministradorRegistroEmpleado.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testCamposPrincipalesVisibles() {

        verifyThat("#txtNombreUsuario", isVisible());
        verifyThat("#txtPassword", isVisible());
        verifyThat("#txtDni", isVisible());
        verifyThat("#txtNombre", isVisible());
        verifyThat("#txtApellidos", isVisible());
        verifyThat("#txtDireccion", isVisible());
        verifyThat("#txtEmail", isVisible());
        verifyThat("#txtTelefono", isVisible());
        verifyThat("#txtCargo", isVisible());
        verifyThat("#btnAnadir", isVisible());
    }

    @Test
    public void testControlesCargados() {

        TextField usuario =
                lookup("#txtNombreUsuario").queryAs(TextField.class);

        PasswordField password =
                lookup("#txtPassword").queryAs(PasswordField.class);

        assertNotNull(usuario);
        assertNotNull(password);
    }

    @Test
    public void testAnadirEmpleadoSinDatos() {

        clickOn("#btnAnadir");

        verifyThat(".dialog-pane", isVisible());

        DialogPane dialogo =
                lookup(".dialog-pane").queryAs(DialogPane.class);

        assertEquals(
                "Por favor, rellena al menos el DNI, Nombre, Usuario y Contraseña.",
                dialogo.getContentText());
    }

    @Test
    public void testAnadirEmpleadoSinNombre() {

        clickOn("#txtNombreUsuario").write("empleado1");
        clickOn("#txtPassword").write("1234");
        clickOn("#txtDni").write("12345678A");

        clickOn("#btnAnadir");

        verifyThat(".dialog-pane", isVisible());
    }

    @Test
    public void testAnadirEmpleadoSinUsuario() {

        clickOn("#txtPassword").write("1234");
        clickOn("#txtDni").write("12345678A");
        clickOn("#txtNombre").write("Pepe");

        clickOn("#btnAnadir");

        verifyThat(".dialog-pane", isVisible());
    }
}