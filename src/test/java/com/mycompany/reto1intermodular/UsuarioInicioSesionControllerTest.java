/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reto1intermodular;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.junit.jupiter.api.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.testfx.matcher.base.NodeMatchers.*;

/**
 *
 * @author Albeiro.Mamani
 */
public class UsuarioInicioSesionControllerTest extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioInicioSesion.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
    }
    
    @Test
    public void testLoginCredencialesIncorrectas() {
    clickOn("#txtUsuario").write("minipekka");
    clickOn("#pwdContrasena").write("123");
    clickOn("#btnEntrar");
    
    verifyThat(".dialog-pane", isVisible());
    
    DialogPane dialogo = lookup(".dialog-pane").queryAs(DialogPane.class);
    assertEquals("Usuario o contraseña incorrectos", dialogo.getContentText());
    
    clickOn(dialogo.lookupButton(ButtonType.OK));
    }
    
    
    @Test
    public void testLoginCredencialesCorrectas() {
        clickOn("#txtUsuario").write("ikerne");
        clickOn("#pwdContrasena").write("123");
        clickOn("#btnEntrar");

        verifyThat(".dialog-pane", isVisible());
        DialogPane dialogo = lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Login correcto", dialogo.getContentText());

        clickOn(dialogo.lookupButton(ButtonType.OK));
    }
    
   
}
