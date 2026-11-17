package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import modelo.Polideportivo;
import modelo.RecursoMantenimiento;
import modelo.Rol;
import modelo.Usuario;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.util.Callback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class EmpleadoGestionControllerTest extends ApplicationTest {

    private final Polideportivo erandio =
            new Polideportivo("erandio-test", "Erandio", "Calle A", "1", "Erandio", "Bizkaia");
    private final Polideportivo getxo =
            new Polideportivo("getxo-test", "Getxo", "Calle B", "2", "Getxo", "Bizkaia");

    private final RecursoMantenimiento pista =
            new RecursoMantenimiento("pista-test", "Pista de pádel", false);
    private final RecursoMantenimiento actividad =
            new RecursoMantenimiento("yoga-test", "Yoga", true);

    private int guardados;

    @Override
    public void start(Stage stage) throws IOException {
        guardados = 0;
        Usuario empleado = new Usuario("empleado-test", "00000000T", Rol.Empleado,
                "unai", "123", "Unai", "Prueba", "Bilbao",
                "unai@example.com", "600000000");
        Sesion.iniciar(empleado);

        Controlador datosPrueba = new Controlador() {
            @Override
            public List<Polideportivo> obtenerPolideportivos() {
                return List.of(erandio, getxo);
            }

            @Override
            public List<RecursoMantenimiento> obtenerRecursos(String idPolideportivo) {
                if (erandio.getId().equals(idPolideportivo)) {
                    return List.of(pista);
                }
                return List.of(actividad);
            }

            @Override
            public long programarMantenimiento(String idEmpleado, String idPolideportivo,
                    RecursoMantenimiento recurso, Date inicio, Date fin) {
                guardados++;
                return 0;
            }
        };

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpleadoGestion.fxml"));
        loader.setControllerFactory(
    new Callback<Class<?>, Object>() {
        @Override
        public Object call(Class<?> tipo) {
            return new EmpleadoGestionController(datosPrueba);
        }
    }
);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void stop() {
        Sesion.cerrar();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCambiarPolideportivoActualizaLosRecursos() {
        ComboBox<Polideportivo> centros = lookup("#polideportivo").queryAs(ComboBox.class);
        ComboBox<RecursoMantenimiento> recursos = lookup("#recurso").queryAs(ComboBox.class);

       interact(new Runnable() {
    @Override
    public void run() {
        centros.getSelectionModel().select(erandio);
        assertEquals(List.of(pista), recursos.getItems());

        recursos.getSelectionModel().select(pista);
        centros.getSelectionModel().select(getxo);

        assertEquals(List.of(actividad), recursos.getItems());
        assertNull(recursos.getValue());
    }
});
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAplicarSinRecursoMuestraAviso() {
        ComboBox<Polideportivo> centros = lookup("#polideportivo").queryAs(ComboBox.class);
       interact(new Runnable() {
    @Override
    public void run() {
        centros.getSelectionModel().select(erandio);
    }
});

        clickOn("Aplicar mantenimiento");
        verifyThat(".dialog-pane", isVisible());

        DialogPane aviso = lookup(".dialog-pane").queryAs(DialogPane.class);
        interact(new Runnable() {
    @Override
    public void run() {
        assertEquals(
                "Selecciona el polideportivo, el recurso y las dos fechas.",
                aviso.getContentText());
    }
});

        push(KeyCode.ENTER);
        interact(new Runnable() {
    @Override
    public void run() {
        assertEquals(0, guardados);
    }
});
    }
}
