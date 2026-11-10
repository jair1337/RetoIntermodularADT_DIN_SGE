package main;

//import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Albeiro.Mamani
 */
public class Principal extends Application {
    
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/reto1intermodular/UsuarioBienvenida.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/ApartadoLoginCSS.css").toExternalForm());
        stage.setTitle("TxandaSport");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/TxandaSportLogo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
