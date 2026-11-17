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
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Albeiro.Mamani
 */
public class ClienteDisponibilidadControllerTest extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioBienvenida.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
    }

    @BeforeEach
    public void navegarHastaDisponibilidad() {
        clickOn("#btnComenzar"); 
        
        clickOn("#txtUsuario").write("ikerne");
        clickOn("#pwdContrasena").write("123");
        clickOn("#btnEntrar");

        verifyThat(".dialog-pane", isVisible());
        DialogPane dialogo = lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Login correcto", dialogo.getContentText());

        clickOn(dialogo.lookupButton(ButtonType.OK));

        clickOn("#btnDisponibilidad");
    }
    
    
    @Test
    public void testTabPistasFuncionaAlPulsar() {
        clickOn("#tabPistas");
        clickOn("#btnReservarPista");
    }
    
    @Test
    public void testTabClasesFuncionaAlPulsar() {
        clickOn("#tabClases");
        clickOn("#btnRegistrarseActividad");
    }
    
    @Test
    public void testBotonAtrasDesdeTabClases() {
        clickOn("#tabClases");
        clickOn("#btnAtras");
        verifyThat("#lblTitulo", isVisible());  
    }
    
    @Test
    public void testBotonAtrasDesdeTabPistas() {
        clickOn("#tabPistas");
        clickOn("#btnAtras");
        verifyThat("#lblTitulo", isVisible());  
    }
    
    @Test
    public void testRegistrarseEnClase() {
        clickOn("#tabClases");
        clickOn("Escuela de Tenis Adultos");
        
        clickOn("#btnRegistrarseActividad");
  
        verifyThat(".dialog-pane", isVisible());
        DialogPane dialogo = lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals(true, dialogo.getContentText().contains("Te has registrado en: Escuela de Tenis Adultos"));

        clickOn(dialogo.lookupButton(ButtonType.OK));
    }
     
    @Test
    public void testReservarUnaPista() {
        clickOn("#tabPistas");
        clickOn("Pista Pádel 1");
        
        clickOn("#btnReservarPista");
        
        clickOn("#dtrFecha").push(KeyCode.CONTROL, KeyCode.A).push(KeyCode.BACK_SPACE).write("22/11/2026");        
        
        //clickOn("#sprCantidad").push(KeyCode.CONTROL, KeyCode.A).push(KeyCode.BACK_SPACE).write("1");
        
        Spinner<Integer> spinner = lookup("#sprHora").queryAs(Spinner.class);

        javafx.application.Platform.runLater(() -> {
            spinner.getValueFactory().setValue(22); 
        });
    
        clickOn("#btnPagar");
        
        verifyThat(".dialog-pane", isVisible());
        DialogPane dialogo = lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals(true, dialogo.getContentText().contains("Reserva realizada. Total: 12,50 € (Tarjeta)"));

        clickOn(dialogo.lookupButton(ButtonType.OK));
    }
       
}
