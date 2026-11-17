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

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.matcher.base.NodeMatchers.*;


/**
 *
 * @author Albeiro.Mamani
 */
public class ClienteMenuControllerTest extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteMenu.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @Test
    public void testLabelTituloVisible() {
        verifyThat("#lblTitulo", isVisible());
    }
    
    @Test
    public void testLabelSubtituloVisible() {
        verifyThat("#lblSubtitulo", isVisible());
    }
    
    @Test
    public void testBotonDisponibilidadAbreVentana() {
        clickOn("#btnDisponibilidad");
        verifyThat("#tbActividad", isVisible());
    }
    
    @Test
    public void testBotonCancelarAbreVentana() {
        clickOn("#btnCancelar");
        verifyThat("#tbActividades", isVisible());
    }
    
    @Test
    public void testBotonContactanosAbreVentana() {
        clickOn("#btnContactanos");
        verifyThat("#taComentario", isVisible());
    }
   
    @Test
    public void testBotonAyudaAbreVentana() {
        clickOn("#btnAyuda");
        verifyThat("#btnAtras", isVisible());
    }
    
}
