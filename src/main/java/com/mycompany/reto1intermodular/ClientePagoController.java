package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import modelo.Pago;
import modelo.Pista;
import modelo.Usuario;
/**
 * FXML Controller class
 *
 * @author Albeiro.Mamani
 */
public class ClientePagoController extends LoggerController implements Initializable {
 
    // Horario de las pistas (la reserva debe terminar como máximo a las 23:00)
    private static final int HORA_APERTURA = 8;
    private static final int HORA_CIERRE = 23;
 
    private final Controlador controlador = new Controlador();
 
    private Pista pista;
 
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnPagar;
    @FXML
    private TextField lblPista;
    @FXML
    private DatePicker dtrFecha;
    @FXML
    private Spinner<Integer> sprHora;
    @FXML
    private Spinner<Integer> sprCantidad;   // número de horas
    @FXML
    private Label lblTotal;
    @FXML
    private RadioButton rbTarjeta;
    @FXML
    private RadioButton rbBizum;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblPista.setEditable(false);
 
        // Método de pago: solo uno a la vez
        ToggleGroup grupoPago = new ToggleGroup();
        rbTarjeta.setToggleGroup(grupoPago);
        rbBizum.setToggleGroup(grupoPago);
        rbTarjeta.setSelected(true);
 
        // Fecha: por defecto hoy y sin permitir días pasados
        dtrFecha.setValue(LocalDate.now());
        dtrFecha.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now()));
            }
        });
 
        sprHora.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(HORA_APERTURA, HORA_CIERRE - 1, 10));
        sprCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
 
        // Al cambiar las horas se recalcula el total
        sprCantidad.valueProperty().addListener((obs, anterior, nuevo) -> actualizarTotal());
    }
 
    // Lo llama ClienteDisponibilidadController justo después de cargar el FXML
    public void setPista(Pista pista) {
        this.pista = pista;
        lblPista.setText(pista.getNombre());
        actualizarTotal();
    }
 
    private double calcularImporte() {
        return pista.getPrecioHora() * sprCantidad.getValue();
    }
 
    private void actualizarTotal() {
        if (pista == null) {
            return;
        }
        lblTotal.setText(String.format("%.2f €", calcularImporte()));
    }
 
    @FXML
    private void pagar() {
        Usuario usuario = Sesion.getUsuario();
 
        if (usuario == null || pista == null) {
            new Alert(Alert.AlertType.WARNING, "No hay una sesión o una pista seleccionada").showAndWait();
            return;
        }
        if (dtrFecha.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Elige un día para la reserva").showAndWait();
            return;
        }
 
        int horaInicio = sprHora.getValue();
        int horas = sprCantidad.getValue();
 
        if (horaInicio + horas > HORA_CIERRE) {
            new Alert(Alert.AlertType.WARNING, "La reserva debe terminar como máximo a las " + HORA_CIERRE + ":00").showAndWait();
            return;
        }
 
        LocalDateTime inicio = dtrFecha.getValue().atTime(horaInicio, 0);
        LocalDateTime fin = inicio.plusHours(horas);
 
        if (inicio.isBefore(LocalDateTime.now())) {
            new Alert(Alert.AlertType.WARNING, "No puedes reservar en el pasado").showAndWait();
            return;
        }
 
        Pago metodoPago = rbBizum.isSelected() ? Pago.Bizum : Pago.Tarjeta;
        double importe = calcularImporte();
 
        try {
            controlador.reservarPista(
                    usuario.getId(),
                    pista.getId(),
                    aDate(inicio),
                    aDate(fin),
                    importe,
                    metodoPago);
 
            new Alert(Alert.AlertType.INFORMATION,
                    String.format("Reserva realizada. Total: %.2f € (%s)", importe, metodoPago)).showAndWait();
            logger.info("Usuario " + usuario.getNombreUsuario() + " reservó la pista " + pista.getNombre());
 
            volver();
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
            logger.severe("ERROR: " + e);
        }
    }
 
    private Date aDate(LocalDateTime fecha) {
        return Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
    }
 
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/ClienteDisponibilidad.fxml"));
 
            Parent root = loader.load();
            btnAtras.getScene().setRoot(root);
 
            logger.info("Volviendo al escenario Disponibilidad");
        } catch (IOException e) {
            logger.severe("ERROR: " + e);
        }
    }
}
