package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterUser.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setTitle("Gestion des Absences");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'interface FXML.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
