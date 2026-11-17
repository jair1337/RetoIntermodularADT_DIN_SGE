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
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Albeiro.Mamani
 */
public class UsuarioBienvenidaControllerTest extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioBienvenida.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
    }
   
    @Test
    public void testBotonLoginHabilitadoVisible() {
    verifyThat("#btnComenzar", isEnabled());
    }
    
    
    @Test
    public void testLabelTxandaSportTextoVisible() {
    verifyThat("#lblTxandaSport", hasText("TxandaSport"));
    }
    
    @Test
    public void testLabelTituloTextoVisible() {
    verifyThat("#lblTitulo", hasText("Gestiona tu deporte\n" + "como nunca antes"));
    }
    
    @Test
    public void testLabelSubtituloTextoVisible() {
    verifyThat("#lblSubtitulo", hasText("Organizar polideportivos nunca fue tan facil!"));
    }
    
    @Test
    public void testLabelInferiorIzquierdoTextoVisible() {
    verifyThat("#lblSencillez", hasText("Sencillez"));
    }
    
    @Test
    public void testLabelInferiorCentralTextoVisible() {
    verifyThat("#lblSeguridad", hasText("Seguridad"));
    }
    
    @Test
    public void testLabelInferiorDerechoTextoVisible() {
    verifyThat("#lblResultados", hasText("Resultados"));
    }
    
    @Test
    public void testIconoIzquierdoVisible() {
    verifyThat("#IconoIzquierdo", isVisible());
    }
    
    @Test
    public void testIconoCentralVisible() {
    verifyThat("#IconoCentral", isVisible());
    }
    
    @Test
    public void testIconoDerechoVisible() {
    verifyThat("#IconoDerecho", isVisible());
    }
    
}
