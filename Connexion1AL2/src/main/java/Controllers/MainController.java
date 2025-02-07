package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private void handleStudentManagement(ActionEvent event) throws IOException {
        loadScene("student.fxml");
    }

    @FXML
    private void handleCourseManagement(ActionEvent event) throws IOException {
        loadScene("course.fxml");
    }

    @FXML
    private void handleUserManagement(ActionEvent event) throws IOException {
        loadScene("user.fxml");
    }

    @FXML
    private void handleGradeManagement(ActionEvent event) throws IOException {
        loadScene("grade.fxml");
    }

    @FXML
    private void handleAuth(ActionEvent event) throws IOException {
        loadScene("auth.fxml");
    }

    @FXML
    private void handleInscription(ActionEvent event) throws IOException {
        loadScene("inscription.fxml");
    }

    /**
     * Loads a new scene from the specified FXML file and displays it in a new stage.
     *
     * @param fxmlFile The name of the FXML file to load (e.g., "student.fxml").
     * @throws IOException If the FXML file cannot be loaded.
     */
    private void loadScene(String fxmlFile) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
        Parent root = loader.load();

        // Create a new stage and set the scene
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        // Set the title of the stage based on the FXML file
        switch (fxmlFile) {
            case "student.fxml":
                stage.setTitle("Gestion des Étudiants");
                break;
            case "course.fxml":
                stage.setTitle("Gestion des Cours");
                break;
            case "user.fxml":
                stage.setTitle("Gestion des Utilisateurs");
                break;
            case "grade.fxml":
                stage.setTitle("Gestion des Notes");
                break;
            case "auth.fxml":
                stage.setTitle("Authentification");
                break;
            case "inscription.fxml":
                stage.setTitle("Inscription au Système");
                break;
            default:
                stage.setTitle("NextGenEdu");
                break;
        }

        // Show the stage
        stage.show();
    }
}