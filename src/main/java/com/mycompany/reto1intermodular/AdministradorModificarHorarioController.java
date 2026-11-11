package com.mycompany.reto1intermodular;

import controlador.Controlador;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import modelo.Actividad;
import modelo.Usuario;
import org.bson.types.ObjectId;

/**
 * FXML Controller class para modificar el horario de los empleados
 * 
 * @author Inés
 */
public class AdministradorModificarHorarioController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();

    @FXML
    private ComboBox<Usuario> cbEmpleado;
    @FXML
    private ComboBox<Actividad> cbDiaEliminar; 
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnDelete;
    @FXML
    private DatePicker dpDiaAnadir;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnAtras;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        logger.info("Vista de modificar horario de empleado abierta por: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEmpleados();
    }    

    /**
     * Carga los empleados en el ComboBox superior
     */
    private void cargarEmpleados() {
        try {
            cbEmpleado.getItems().clear();
            List<Usuario> todosLosUsuarios = controlador.obtenerUsuarios();
            if (todosLosUsuarios != null) {
                for (Usuario u : todosLosUsuarios) {
                    if (u.getRol() != null && u.getRol().name().equalsIgnoreCase("Empleado")) {
                        cbEmpleado.getItems().add(u);
                    }
                }
            }
            
            // Muestra el nombre de usuario y nombre real de forma limpia en el desplegable
            cbEmpleado.setCellFactory(param -> new javafx.scene.control.ListCell<Usuario>() {
                @Override
                protected void updateItem(Usuario item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombreUsuario() + " (" + item.getNombre() + " " + item.getApellidos() + ")");
                    }
                }
            });
            cbEmpleado.setButtonCell(cbEmpleado.getCellFactory().call(null));

            logger.info("Combobox de empleados cargado con éxito.");
        } catch (Exception e) {
            logger.severe("Error al cargar empleados en el combo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuscar(ActionEvent event) {
        Usuario empleadoSeleccionado = cbEmpleado.getValue();
        if (empleadoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Por favor, selecciona un empleado primero.");
            return;
        }
        
        logger.info("Buscando actividades/horario para el empleado: " + empleadoSeleccionado.getNombreUsuario());
        try {
            cbDiaEliminar.getItems().clear();
            
            // Llamamos al DAO optimizado para que lea el array de actividadesAsignadas del usuario en MongoDB
            List<Actividad> actividadesDelEmpleado = controlador.obtenerActividadesPorEmpleado(empleadoSeleccionado.getId());
            
            if (actividadesDelEmpleado != null) {
                for (Actividad act : actividadesDelEmpleado) {
                    cbDiaEliminar.getItems().add(act);
                }
            }

            // Formato visual para el ComboBox de actividades asignadas
            cbDiaEliminar.setCellFactory(param -> new javafx.scene.control.ListCell<Actividad>() {
                @Override
                protected void updateItem(Actividad item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getTitulo() + " (" + item.getFechaInicio() + ")");
                    }
                }
            });
            cbDiaEliminar.setButtonCell(cbDiaEliminar.getCellFactory().call(null));
            
            if (cbDiaEliminar.getItems().isEmpty()) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sin horarios", "Este empleado no tiene actividades asignadas actualmente.");
            } else {
                logger.info("Horario cargado correctamente para: " + empleadoSeleccionado.getNombreUsuario());
            }
        } catch (Exception e) {
            logger.severe("Error al buscar horario del empleado: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar el horario: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Usuario empleadoSeleccionado = cbEmpleado.getValue();
        Actividad actividadAEliminar = cbDiaEliminar.getValue();

        if (empleadoSeleccionado == null || actividadAEliminar == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "Selecciona un empleado y la actividad/horario a eliminar.");
            return;
        }

        logger.info("Intentando eliminar asignacion de horario para el empleado: " + empleadoSeleccionado.getNombreUsuario());
        try {
            List<Actividad> actividadesActuales = cbDiaEliminar.getItems();
            List<ObjectId> actualizadas = new ArrayList<>();
            
            // Recopilamos los IDs de todas las actividades MENOS la que queremos borrar
            for (Actividad act : actividadesActuales) {
                if (!act.getId().equals(actividadAEliminar.getId())) {
                    actualizadas.add(new ObjectId(act.getId()));
                }
            }
            
            // Actualizamos en la base de datos el array de actividades asignadas del empleado
            controlador.modificarHorarioEmpleado(empleadoSeleccionado.getId(), actualizadas);
            
            logger.info("Horario modificado (eliminado) con exito.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Operación exitosa", "Se ha actualizado el horario del empleado correctamente.");
            
            // Refrescamos automáticamente la vista recargando el ComboBox
            handleBuscar(event);

        } catch (Exception e) {
            logger.severe("Error al eliminar horario: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el horario: " + e.getMessage());
        }
    }

    @FXML
    private void handleAnadirDia(ActionEvent event) {
        Usuario empleadoSeleccionado = cbEmpleado.getValue();
        LocalDate fechaSeleccionada = dpDiaAnadir.getValue();

        if (empleadoSeleccionado == null || fechaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "Selecciona un empleado y una fecha valida en el calendario.");
            return;
        }

        logger.info("Intentando añadir nueva fecha/horario laborable al empleado: " + empleadoSeleccionado.getNombreUsuario());
        try {
            List<ObjectId> actualizadas = new ArrayList<>();
            
            // Mantenemos las que ya tenía asignadas obteniéndolas del ComboBox actual
            List<Actividad> actividadesActuales = cbDiaEliminar.getItems();
            for (Actividad act : actividadesActuales) {
                actualizadas.add(new ObjectId(act.getId()));
            }
            
            // Buscamos en todas las actividades globales las que coincidan con la fecha seleccionada
            List<Actividad> todas = controlador.obtenerActividades();
            boolean anadido = false;
            
            if (todas != null) {
                for (Actividad act : todas) {
                    if (act.getFechaInicio() != null) {
                        LocalDate fechaAct = act.getFechaInicio().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                        if (fechaAct.equals(fechaSeleccionada)) {
                            ObjectId idActObj = new ObjectId(act.getId());
                            if (!actualizadas.contains(idActObj)) {
                                actualizadas.add(idActObj);
                                anadido = true;
                            }
                        }
                    }
                }
            }
            
            if (!anadido) {
                mostrarAlerta(Alert.AlertType.WARNING, "Sin actividades", "No hay actividades registradas en esa fecha para asignar (o ya las tiene todas).");
                return;
            }

            controlador.modificarHorarioEmpleado(empleadoSeleccionado.getId(), actualizadas);
            
            logger.info("Horario añadido con exito.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Se ha añadido la nueva fecha al horario del empleado.");
            
            dpDiaAnadir.setValue(null); 
            handleBuscar(event);

        } catch (Exception e) {
            logger.severe("Error al añadir horario: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo añadir el horario: " + e.getMessage());
        }
    }
    
    @FXML
    public void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorMenu.fxml"));
            Parent root = loader.load();

            AdministradorMenuController adminController = loader.getController();
            adminController.setUsuario(usuarioActual);

            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            logger.info("Volviendo al menu de administrador desde modificar horario");
        } catch (IOException e) {
            logger.severe("Error al volver al menu de administrador: " + e.getMessage());
            System.out.println("ERROR: " + e);
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}