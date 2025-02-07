package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    private static final Map<String, String> SCENE_TITLES = new HashMap<>();
    static {
        SCENE_TITLES.put("student.fxml", "Gestion des Étudiants");
        SCENE_TITLES.put("course.fxml", "Gestion des Cours");
        SCENE_TITLES.put("user.fxml", "Gestion des Utilisateurs");
        SCENE_TITLES.put("grade.fxml", "Gestion des Notes");
        SCENE_TITLES.put("auth.fxml", "Authentification");
        SCENE_TITLES.put("inscription.fxml", "Inscription au Système");
        SCENE_TITLES.put("examen.fxml", "Gestion des Examens");
    }

    @FXML
    private void handleStudentManagement(ActionEvent event) throws IOException {
        loadScene(event, "student.fxml");
    }

    @FXML
    private void handleCourseManagement(ActionEvent event) throws IOException {
        loadScene(event, "course.fxml");
    }

    @FXML
    private void handleUserManagement(ActionEvent event) throws IOException {
        loadScene(event, "user.fxml");
    }

    @FXML
    private void handleGradeManagement(ActionEvent event) throws IOException {
        loadScene(event, "grade.fxml");
    }

    @FXML
    private void handleAuth(ActionEvent event) throws IOException {
        loadScene(event, "auth.fxml");
    }

    @FXML
    private void handleInscription(ActionEvent event) throws IOException {
        loadScene(event, "inscription.fxml");
    }

    @FXML
    private void handleExamenManagement(ActionEvent event) throws IOException {
        loadScene(event, "examen.fxml");  // Méthode pour charger la scène des examens
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // Handle logout logic here (e.g., close the stage, clear session data, etc.)
        System.out.println("Logging out...");
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void loadScene(ActionEvent event, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get current stage
        stage.setScene(new Scene(root));
        stage.setTitle(SCENE_TITLES.getOrDefault(fxmlFile, "NextGenEdu"));
        stage.show();
    }
}
