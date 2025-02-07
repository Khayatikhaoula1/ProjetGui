package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private void handleStudentManagement(ActionEvent event) throws IOException {
        loadScene("student.fxml", "Gestion des Étudiants");
    }

    @FXML
    private void handleCoursManagement(ActionEvent event) throws IOException {
        loadScene("cours.fxml", "Gestion des Cours");
    }

    @FXML
    private void handleUserManagement(ActionEvent event) throws IOException {
        loadScene("user.fxml", "Gestion des Utilisateurs");
    }

    @FXML
    private void handleGradeManagement(ActionEvent event) throws IOException {
        loadScene("grade.fxml", "Gestion des Notes");
    }

    @FXML
    private void handleAuth(ActionEvent event) throws IOException {
        loadScene("auth.fxml", "Authentification");
    }
    @FXML
    private javafx.scene.layout.BorderPane mainPane;

    @FXML
    private void handleLogout(ActionEvent event) {
        // Fermer la fenêtre
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleInscriptionManagement(ActionEvent event) throws IOException {
        loadScene("inscription.fxml", "Inscription au Système");
    }

    /**
     * Charge une nouvelle scène et l'affiche dans une nouvelle fenêtre.
     *
     * @param fxmlFile Nom du fichier FXML à charger.
     * @param title    Titre de la nouvelle fenêtre.
     * @throws IOException Si le fichier FXML ne peut pas être chargé.
     */
    private void loadScene(String fxmlFile, String title) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la vue : " + fxmlFile);
        }
    }

    /**
     * Affiche une boîte de dialogue d'alerte.
     *
     * @param type    Type d'alerte (INFORMATION, WARNING, ERROR).
     * @param title   Titre de l'alerte.
     * @param message Message à afficher.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
