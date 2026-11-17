/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reto1intermodular;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author Albeiro.Mamani
 */
public class ClienteCancelarControllerTest extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioBienvenida.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
    }

    @BeforeEach
    public void navegarHastaCancelar() {
        clickOn("#btnComenzar"); 
        
        clickOn("#txtUsuario").write("ikerne");
        clickOn("#pwdContrasena").write("123");
        clickOn("#btnEntrar");

        verifyThat(".dialog-pane", isVisible());
        DialogPane dialogo = lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Login correcto", dialogo.getContentText());

        clickOn(dialogo.lookupButton(ButtonType.OK));

        clickOn("#btnCancelar");
    }
    
    @Test
    public void testCancelarActividadApuntada() {
        clickOn("Actividad");
        clickOn("#btnCancelar");
        clickOn("Sí");
    }
    
    @Test
    public void testCancelarPistaReservada() {
        clickOn("Pista");
        clickOn("#btnCancelar");
        clickOn("Sí");
    }
}
