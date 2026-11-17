package com.mycompany.reto1intermodular;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import modelo.Rol;
import modelo.Usuario;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class EmpleadoMenuControllerTest extends ApplicationTest {

    private Usuario empleado;

    @Override
    public void start(Stage stage) throws IOException {
        empleado = new Usuario("empleado-test", "00000000T", Rol.Empleado,
                "unai", "123", "Unai", "Prueba", "Bilbao",
                "unai@example.com", "600000000");
        Sesion.iniciar(empleado);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpleadoMenu.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void stop() {
        Sesion.cerrar();
    }

    @Test
    public void testConsultarPerfilMuestraElUsuarioConectado() {
        clickOn("#btnPerfil");
        verifyThat("#tfNombreUsuario", isVisible());

        TextField usuario = lookup("#tfNombreUsuario").queryAs(TextField.class);
        interact(new Runnable() {
    @Override
    public void run() {
        assertEquals("unai", usuario.getText());
    }
});

        clickOn("#btnAtras");
        verifyThat("#btnPerfil", isVisible());
    }

    @Test
    public void testCancelarCierreMantieneLaSesion() {
        clickOn("#btnCerrarSesion");
        verifyThat(".dialog-pane", isVisible());

        push(KeyCode.ESCAPE);

        verifyThat("#btnPerfil", isVisible());
interact(new Runnable() {
    @Override
    public void run() {
        assertSame(empleado, Sesion.getUsuario());
    }
});    }
}