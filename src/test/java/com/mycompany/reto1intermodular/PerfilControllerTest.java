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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Albeiro.Mamani
 */
public class PerfilControllerTest extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioBienvenida.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    stage.show();
    }

    @BeforeEach // Usa @BeforeEach si estás en JUnit 5
    public void navegarHastaPerfil() {
        clickOn("#btnComenzar"); 
        
        clickOn("#txtUsuario").write("ikerne");
        clickOn("#pwdContrasena").write("123");
        clickOn("#btnEntrar");

        verifyThat(".dialog-pane", isVisible());
        DialogPane dialogo = lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Login correcto", dialogo.getContentText());

        clickOn(dialogo.lookupButton(ButtonType.OK));

        clickOn("#btnPerfil");
    }

    @Test
    public void testNombreDeUsuarioCargadoAlNavegar() {
        TextField tf = lookup("#tfNombreUsuario").queryAs(TextField.class);
        assertEquals("ikerne", tf.getText());
    }
    
    @Test
    public void testNombreCargadoAlNavegar() {
        TextField tf = lookup("#tfNombre").queryAs(TextField.class);
        assertEquals("Ikerne", tf.getText());
    }
    
    @Test
    public void testCorreoElectronicoCargadoAlNavegar() {
        TextField tf = lookup("#tfEmail").queryAs(TextField.class);
        assertEquals("ikerne.sevilla@email.com", tf.getText());
    }
    
    @Test
    public void testDireccionCargadaAlNavegar() {
        TextField tf = lookup("#tfDireccion").queryAs(TextField.class);
        assertEquals("Lehendakari Aguirre, 22, Bilbao, 48014, Bizkaia", tf.getText());
    }
    
    @Test
    public void testContrasenaDeUsuarioCargadaAlNavegar() {
        TextField tf = lookup("#tfContrasenaUsuario").queryAs(TextField.class);
        assertEquals("123", tf.getText());
    }
    
    @Test
    public void testApellidosCargadosAlNavegar() {
        TextField tf = lookup("#tfApellidos").queryAs(TextField.class);
        assertEquals("Sevilla Gal", tf.getText());
    }
    
    @Test
    public void testTelefonoCargadoAlNavegar() {
        TextField tf = lookup("#tfTelefono").queryAs(TextField.class);
        assertEquals("600000006", tf.getText());
    }
    
    
    @Test
    public void testBotonAtrasFunciona() {
        clickOn("#btnAtras");
        verifyThat("#lblTitulo", isVisible());  
    }
    
    
    @Test
    public void testModificarYGuardarCampoFunciona() {
        clickOn("#btnModificar");
        clickOn("#tfNombreUsuario").write("2");
        clickOn("#btnGuardar");
        
        TextField tf = lookup("#tfNombreUsuario").queryAs(TextField.class);
        assertEquals("ikerne2", tf.getText());
    }
   
}
