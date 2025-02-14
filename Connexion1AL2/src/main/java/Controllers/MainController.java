package Controllers;

import Entites.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button loginLogoutButton; // Bouton qui affichera soit "Login" soit "Logout"

    @FXML
    private Label loggedInUserLabel; // Label affichant le nom d'utilisateur

    @FXML
    private javafx.scene.layout.BorderPane mainPane;

    private User currentUser = null; // Stocke l'utilisateur connecté

    /**
     * Définit l'utilisateur actuel et met à jour l'interface.
     */
    public void setUser(User user) {
        this.currentUser = user;
        if (user != null) {
            loggedInUserLabel.setText("Bienvenue, " + user.getUsername() + " !");
            loginLogoutButton.setText("Logout");
        } else {
            loggedInUserLabel.setText("Non connecté");
            loginLogoutButton.setText("Login");
        }
    }



    /**
     * Charge et affiche la page de connexion.
     */
    private void loadLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Connexion");
        stage.show();
    }

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
        loadScene("UserView.fxml", "Gestion des Utilisateurs");
    }

    @FXML
    private void handleGradeManagement(ActionEvent event) throws IOException {
        loadScene("note.fxml", "Gestion des Notes");
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
    @FXML
    private void handleAuth(ActionEvent event) throws IOException {
        loadScene("auth.fxml", "Authentification");
    }
    /**
     * Gère l'action du bouton "Login / Logout".
     */

    @FXML
    private void handleLoginLogout(ActionEvent event) throws IOException {
        boolean isLoggedIn = checkUserLoginStatus(); // Vérifie si l'utilisateur est connecté

        if (isLoggedIn) {
            // Déconnexion et retour à la page de connexion
            loadLoginPage(event);
        } else {
            // Connexion et chargement de la page principale
            loadScene("main.fxml", "Tableau de Bord");
        }
    }

    private void loadLoginPage(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml")); // Assure-toi que le chemin est correct
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page de connexion.");
        }
    }

    // Simule un état de connexion (à remplacer par ta propre logique)
    private boolean checkUserLoginStatus() {
        return loggedInUserLabel.getText() != null && !loggedInUserLabel.getText().equals("Bienvenue, Invité!");
    }


}
