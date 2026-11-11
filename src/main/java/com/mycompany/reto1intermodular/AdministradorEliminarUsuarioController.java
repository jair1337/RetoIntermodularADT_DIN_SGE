package com.mycompany.reto1intermodular;

import controlador.Controlador;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Usuario;
import org.bson.Document;

/**
 * FXML Controller class para eliminar usuarios
 * 
 * @author Inés
 */
public class AdministradorEliminarUsuarioController extends LoggerController implements Initializable {

    private Usuario usuarioActual;
    private final Controlador controlador = new Controlador();
    
    // Variable para almacenar el ID (ObjectId en String) del usuario encontrado
    private String idUsuarioEncontrado = null;

    @FXML
    private TextField txtDni;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnDelete;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        logger.info("Vista de eliminar usuario abierta por: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "Desconocido"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Bloqueamos los campos de texto de los resultados para que sean de sólo lectura (como indica el FXML)
        if (txtUsuario != null) txtUsuario.setEditable(false);
        if (txtNombre != null) txtNombre.setEditable(false);
    }    

    @FXML
    private void handleBuscar(ActionEvent event) {
        String dniBuscado = txtDni.getText() != null ? txtDni.getText().trim() : "";

        if (dniBuscado.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "DNI vacio", "Por favor, introduce un DNI para buscar al usuario.");
            return;
        }

        logger.info("Buscando usuario por DNI: " + dniBuscado);

        try {
            // Buscamos directamente en la colección 'usuarios' de MongoDB por el campo 'dni'
            // (Usamos la misma URI que el DaoImplementacion)
            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
                MongoDatabase database = mongoClient.getDatabase("base_datos");
                MongoCollection<Document> collection = database.getCollection("usuarios");

                Document documento = collection.find(eq("dni", dniBuscado)).first();

                if (documento != null) {
                    // Guardamos el ID en texto para usarlo al eliminar
                    idUsuarioEncontrado = documento.getObjectId("_id").toString();
                    
                    // Rellenamos los campos de la interfaz
                    txtUsuario.setText(documento.getString("nombreUsuario"));
                    txtNombre.setText(documento.getString("nombre") + " " + documento.getString("apellidos"));

                    logger.info("Usuario encontrado con éxito: " + documento.getString("nombreUsuario"));
                } else {
                    idUsuarioEncontrado = null;
                    txtUsuario.clear();
                    txtNombre.clear();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sin resultados", "No se ha encontrado ningún usuario con ese DNI.");
                }
            }

        } catch (Exception e) {
            logger.severe("Error al buscar el usuario por DNI: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrio un error al realizar la búsqueda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        if (idUsuarioEncontrado == null || idUsuarioEncontrado.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Ningún usuario seleccionado", "Primero debes buscar y cargar un usuario mediante su DNI.");
            return;
        }

        logger.info("Intentando eliminar usuario con ID: " + idUsuarioEncontrado);

        try {
            // Llamamos al método del Controlador que ejecuta la eliminación en el DAO
            controlador.eliminarUsuario(idUsuarioEncontrado);

            logger.info("Usuario eliminado correctamente.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "El usuario ha sido eliminado de la base de datos.");

            // Limpiamos la pantalla tras la eliminación
            txtDni.clear();
            txtUsuario.clear();
            txtNombre.clear();
            idUsuarioEncontrado = null;

        } catch (Exception e) {
            logger.severe("Error al eliminar el usuario: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Error de eliminación", "No se pudo eliminar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/reto1intermodular/AdministradorMenu.fxml"));
            Parent root = loader.load();

            AdministradorMenuController adminController = loader.getController();
            adminController.setUsuario(usuarioActual);

            Scene escena = btnAtras.getScene();
            escena.setRoot(root);
            
            logger.info("Volviendo al menu de administrador desde eliminar usuario");
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