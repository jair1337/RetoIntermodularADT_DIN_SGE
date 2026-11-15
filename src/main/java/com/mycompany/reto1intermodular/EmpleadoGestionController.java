package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modelo.Polideportivo;
import modelo.RecursoMantenimiento;
import modelo.Usuario;

public class EmpleadoGestionController {

       private final Controlador controlador;

    public EmpleadoGestionController() {
        this(new Controlador());
    }

    EmpleadoGestionController(Controlador controlador) {
        this.controlador = controlador;
    }
    private final DateTimeFormatter formatoHora =
            DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

    @FXML private ComboBox<Polideportivo> polideportivo;
    @FXML private ComboBox<RecursoMantenimiento> recurso;
    @FXML private DatePicker fechaInicio;
    @FXML private DatePicker fechaFin;
    @FXML private TextField horaInicio;
    @FXML private TextField horaFin;

    @FXML
    private void initialize() {
        fechaInicio.setValue(LocalDate.now());
        fechaFin.setValue(LocalDate.now());

        try {
            polideportivo.getItems().setAll(controlador.obtenerPolideportivos());
        } catch (Exception e) {
            mensaje(Alert.AlertType.ERROR,
                    "No se pudieron cargar los polideportivos. " + e.getMessage());
        }
    }

    @FXML
    private void cargarRecursos() {
        recurso.getSelectionModel().clearSelection();
        recurso.setValue(null);
        recurso.getItems().clear();

        Polideportivo centro = polideportivo.getValue();
        if (centro == null) {
            return;
        }

        try {
            recurso.getItems().setAll(controlador.obtenerRecursos(centro.getId()));
        } catch (Exception e) {
            mensaje(Alert.AlertType.ERROR,
                    "No se pudieron cargar los recursos. " + e.getMessage());
        }
    }

    @FXML
    private void aplicar() {
        Usuario empleado = Sesion.getUsuario();
        Polideportivo centro = polideportivo.getValue();
        RecursoMantenimiento seleccionado = recurso.getValue();

        if (empleado == null || centro == null || seleccionado == null
                || fechaInicio.getValue() == null || fechaFin.getValue() == null) {
            mensaje(Alert.AlertType.WARNING,
                    "Selecciona el polideportivo, el recurso y las dos fechas.");
            return;
        }

        try {
            Date inicio = convertir(fechaInicio, horaInicio);
            Date fin = convertir(fechaFin, horaFin);

            if (!inicio.before(fin) || !fin.after(new Date())) {
                mensaje(Alert.AlertType.WARNING,
                        "El fin debe ser posterior al inicio y el intervalo no puede haber terminado.");
                return;
            }

            Alert aviso = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Quieres programar el mantenimiento? Se cancelarán las reservas "
                    + "e inscripciones que coincidan con ese horario.");
            aviso.setTitle("Programar mantenimiento");
            aviso.setHeaderText(null);
            aviso.initOwner(polideportivo.getScene().getWindow());

            if (aviso.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            long canceladas = controlador.programarMantenimiento(
                    empleado.getId(), centro.getId(), seleccionado, inicio, fin);
            mensaje(Alert.AlertType.INFORMATION,
                    "Mantenimiento guardado. Reservas e inscripciones canceladas: " + canceladas);
        } catch (DateTimeParseException e) {
            mensaje(Alert.AlertType.WARNING,
                    "Escribe las horas en formato HH:mm, por ejemplo 09:30.");
        } catch (Exception e) {
            mensaje(Alert.AlertType.ERROR,
                    "No se pudo completar el mantenimiento. " + e.getMessage());
        }
    }

    private Date convertir(DatePicker fecha, TextField hora) {
        LocalTime tiempo = LocalTime.parse(hora.getText().trim(), formatoHora);
        return Date.from(fecha.getValue().atTime(tiempo)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    private void volver() {
        try {
            Parent menu = FXMLLoader.load(getClass().getResource("EmpleadoMenu.fxml"));
            polideportivo.getScene().setRoot(menu);
        } catch (IOException e) {
            mensaje(Alert.AlertType.ERROR, "No se pudo volver al menú. " + e.getMessage());
        }
    }

    private void mensaje(Alert.AlertType tipo, String texto) {
        Alert alerta = new Alert(tipo, texto);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}