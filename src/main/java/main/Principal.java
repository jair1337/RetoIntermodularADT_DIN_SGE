package main;

//import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Albeiro.Mamani
 */
public class Principal extends Application {
    
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/reto1intermodular/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Login - TxandaSport");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
