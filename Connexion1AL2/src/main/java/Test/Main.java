package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Charger le fichier FXML cours.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));

        // Charger le fichier FXML et obtenir le root (parent)
        Parent root = loader.load();

        // Créer la scène et l'associer au stage
        Scene scene = new Scene(root);
        stage.setTitle("Cours");
        stage.setScene(scene);

        // Afficher la fenêtre
        stage.show();
    }
}
