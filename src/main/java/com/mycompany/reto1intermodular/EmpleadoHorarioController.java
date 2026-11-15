package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.Actividad;
import modelo.Usuario;

public class EmpleadoHorarioController {

    private final ZoneId zona = ZoneId.systemDefault();
    private final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(zona);
    private final List<Actividad> asignaciones = new ArrayList<>();

    @FXML private DatePicker dpFecha;
    @FXML private TableView<Actividad> tablaHorario;
    @FXML private TableColumn<Actividad, String> colActividad;
    @FXML private TableColumn<Actividad, String> colInicio;
    @FXML private TableColumn<Actividad, String> colFin;
    
        private final Controlador controlador;

    public EmpleadoHorarioController() {
        this(new Controlador());
    }

    EmpleadoHorarioController(Controlador controlador) {
        this.controlador = controlador;
    }

    @FXML
    private void initialize() {
        colActividad.setCellValueFactory(fila ->
                new ReadOnlyStringWrapper(fila.getValue().getTitulo()));
        colInicio.setCellValueFactory(fila ->
                new ReadOnlyStringWrapper(formatear(fila.getValue().getFechaInicio())));
        colFin.setCellValueFactory(fila ->
                new ReadOnlyStringWrapper(formatear(fila.getValue().getFechaFin())));
        tablaHorario.setPlaceholder(new Label("No hay asignaciones para mostrar."));

        Usuario empleado = Sesion.getUsuario();
        if (empleado == null) {
            return;
        }

        try {
            asignaciones.addAll(
                controlador.obtenerActividadesPorEmpleado(empleado.getId()));
            asignaciones.sort(Comparator.comparing(Actividad::getFechaInicio,
                    Comparator.nullsLast(Comparator.naturalOrder())));
            buscar();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "No se pudo cargar el horario. " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void buscar() {
        LocalDate dia = dpFecha.getValue();
        if (dia == null) {
            tablaHorario.getItems().setAll(asignaciones);
            return;
        }

        Date inicioDia = Date.from(dia.atStartOfDay(zona).toInstant());
        Date finDia = Date.from(dia.plusDays(1).atStartOfDay(zona).toInstant());
        tablaHorario.getItems().clear();

        for (Actividad actividad : asignaciones) {
            Date inicio = actividad.getFechaInicio();
            Date fin = actividad.getFechaFin();
            if (inicio != null && fin != null
                    && inicio.before(finDia) && fin.after(inicioDia)) {
                tablaHorario.getItems().add(actividad);
            }
        }
    }

    @FXML
    private void mostrarTodo() {
        dpFecha.setValue(null);
        dpFecha.getEditor().clear();
        buscar();
    }

    @FXML
    private void volver() {
        try {
            Parent menu = FXMLLoader.load(getClass().getResource("EmpleadoMenu.fxml"));
            tablaHorario.getScene().setRoot(menu);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                    "No se pudo volver al menu. " + e.getMessage()).showAndWait();
        }
    }

    private String formatear(Date fecha) {
        return fecha == null ? "" : formato.format(fecha.toInstant());
    }
}