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
public class ClienteFAQControllerTest extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioBienvenida.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
    }

    @BeforeEach
    public void navegarHastaFAQ() {
        clickOn("#btnComenzar"); 
        
        clickOn("#txtUsuario").write("ikerne");
        clickOn("#pwdContrasena").write("123");
        clickOn("#btnEntrar");

        verifyThat(".dialog-pane", isVisible());
        DialogPane dialogo = lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Login correcto", dialogo.getContentText());

        clickOn(dialogo.lookupButton(ButtonType.OK));

        clickOn("#btnAyuda");
    }
    
    @Test
    public void testLabelQuienesSomosTexto(){
        verifyThat("#lblUno", hasText("TxandaSport es una startup especializada en servicios deportivos y ocio que dinamiza polideportivos e instalaciones públicas mediante una plataforma digital integral."));
    }
    
    @Test
    public void testLabelDondeNosUbicamosTexto() {
        verifyThat("#lblDos", hasText("Operamos de forma digital a través de nuestra app y colaboramos directamente en diversos polideportivos municipales y centros asociados de la zona."));
    }
    
    
    @Test
    public void testLabelQueOfrecemosTexto() {
        verifyThat("#lblTres", hasText("Alquiler rápido de pistas, clases dirigidas (baile, cross-training), campus deportivos trimestrales y un sistema de logros y puntos para fidelizar al usuario."));
    }
    
    
    @Test
    public void testLabelComoGestionamosPolisTexto() {
        verifyThat("#lblCuatro", hasText("Mediante acuerdos de cesión de franjas horarias en horas de menor uso, maximizamos la ocupación de las pistas y ofrecemos actividades privadas exclusivas."));
    }
    
    @Test
    public void testLabelDireccionPolisTexto() {
        verifyThat("#lblCinco", hasText("Las actividades y reservas se realizan en los distintos polideportivos municipales asociados. Puedes consultar la ubicación exacta de cada instalación dentro de la app."));
    }
    
    @Test
    public void testLabelApartadosTexto() {
        verifyThat("#lblSeis", hasText("Desde la app puedes gestionar tus reservas inmediatas de pistas, apuntarte a clases dirigidas y llevar un seguimiento de tus métricas y logros."));
    }
    
    @Test
    public void testLabelComoPagarPistaTexto() {
        verifyThat("#lblSiete", hasText("El pago del alquiler express de pistas se realiza en nuestra aplicacion de  forma segura directamente al seleccionar una pista y reservarla."));
    }
    
    @Test
    public void testLabelComoGestionamosPagosTexto() {
        verifyThat("#lblOcho", hasText("Todos los cobros de suscripciones, bonos de actividades y alquileres se procesan de manera automatizada e integrada en el ecosistema digital de la app."));
    }
    @Test
    public void testLabelMetodosDePagoTexto() {
        verifyThat("#lblNueve", hasText("Aceptamos pagos mediante Tarjeta o Bizum en nuestra plataforma de cobro seguro."));
    }
    
    @Test
    public void testBotonAtrasFunciona() {
        clickOn("#btnAtras");
        verifyThat("#lblTitulo", isVisible());  
    }
   
}
