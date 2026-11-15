package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modelo.Actividad;
import modelo.Rol;
import modelo.Usuario;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EmpleadoHorarioControllerTest extends ApplicationTest {

    private final LocalDate diaPadel = LocalDate.of(2030, 5, 10);
    private final LocalDate diaYoga = LocalDate.of(2030, 5, 11);

    @Override
    public void start(Stage stage) throws IOException {
        Usuario empleado = new Usuario("empleado-test", "00000000T", Rol.Empleado,
                "unai", "123", "Unai", "Prueba", "Bilbao",
                "unai@example.com", "600000000");
        Sesion.iniciar(empleado);

        Actividad padel = crearActividad("Pádel", diaPadel, 10);
        Actividad yoga = crearActividad("Yoga", diaYoga, 11);

        Controlador datosPrueba = new Controlador() {
            @Override
            public List<Actividad> obtenerActividadesPorEmpleado(String idEmpleado) {
                assertEquals("empleado-test", idEmpleado);
                return List.of(yoga, padel);
            }
        };

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpleadoHorario.fxml"));
        loader.setControllerFactory(tipo -> new EmpleadoHorarioController(datosPrueba));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void stop() {
        Sesion.cerrar();
    }

    @Test
    public void testHorarioInicialOrdenadoPorFecha() {
        TableView<?> tabla = lookup("#tablaHorario").queryAs(TableView.class);

        interact(() -> {
            assertEquals(2, tabla.getItems().size());
            assertEquals("Pádel", ((Actividad) tabla.getItems().get(0)).getTitulo());
            assertEquals("Yoga", ((Actividad) tabla.getItems().get(1)).getTitulo());
        });
    }

    @Test
    public void testBuscarPorFechaYMostrarTodo() {
        DatePicker fecha = lookup("#dpFecha").queryAs(DatePicker.class);
        TableView<?> tabla = lookup("#tablaHorario").queryAs(TableView.class);

        interact(() -> fecha.setValue(diaYoga));
        clickOn("Buscar");

        interact(() -> {
            assertEquals(1, tabla.getItems().size());
            assertEquals("Yoga", ((Actividad) tabla.getItems().get(0)).getTitulo());
        });

        clickOn("Mostrar todo");

        interact(() -> {
            assertNull(fecha.getValue());
            assertEquals(2, tabla.getItems().size());
        });
    }

    private Actividad crearActividad(String titulo, LocalDate dia, int hora) {
        ZoneId zona = ZoneId.systemDefault();
        Actividad actividad = new Actividad();
        actividad.setTitulo(titulo);
        actividad.setFechaInicio(Date.from(dia.atTime(hora, 0).atZone(zona).toInstant()));
        actividad.setFechaFin(Date.from(dia.atTime(hora + 1, 0).atZone(zona).toInstant()));
        return actividad;
    }
}