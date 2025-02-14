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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
        private static MainController instance; // ✅ Ajout de l'instance Singleton

        public static MainController getInstance() {
            return instance;
        }

        @FXML
        public void initialize() {
            instance = this; // Sauvegarde l'instance actuelle

    }
    @FXML
    private Button loginLogoutButton; // Bouton Login/Logout

    @FXML
    private Label loggedInUserLabel; // Affichage du nom de l'utilisateur connecté

    @FXML
    private StackPane mainContent; // Conteneur où charger les vues dynamiquement

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
     * Charge une interface dans le `mainContent` sans ouvrir une nouvelle fenêtre.
     *
     * @param fxmlFile Chemin du fichier FXML à charger.
     */
    private void loadView(String fxmlFile) {
        try {
            String path = "/fxml/" + fxmlFile;
            System.out.println("🔍 Chargement du fichier : " + path);

            if (getClass().getResource(path) == null) {
                System.err.println("❌ ERREUR : Le fichier " + path + " est introuvable !");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent newView = loader.load();
            mainContent.getChildren().setAll(newView);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la vue : " + fxmlFile);
            e.printStackTrace();
        }
    }

    /**
     * Gestion des boutons de la barre latérale
     */
    @FXML
    void loadDashboardView(ActionEvent event) {
        loadView("Dashboard.fxml");
    }

    @FXML
    private void loadUserView(ActionEvent event) {
        loadView("UserView.fxml");
    }
    @FXML
    public void loadAbsencesView(ActionEvent event) {
        loadView("Absence.fxml");
    }


    @FXML
    private void loadCoursView(ActionEvent event) {
        loadView("Cours.fxml");
    }


    @FXML
    private void loadExamensView(ActionEvent event) {
        loadView("examen.fxml");
    }

    @FXML
    private void loadNotesView(ActionEvent event) {
        loadView("note .fxml");
    }

    @FXML
    private void loadInscriptionsView(ActionEvent event) {
        loadView("InscriptionView.fxml");
    }

    @FXML
    private void loadAuthView(ActionEvent event) {
        loadView("Login.fxml");
    }
    @FXML
    private void loadAbsencesView() {
        loadView("Absence.fxml");
    }

    /**
     * Gère l'action du bouton "Login / Logout".
     */


    /**
     * Charge et affiche la page de connexion.
     */


    /**
     * Affiche une boîte de dialogue d'alerte.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleLoginLogout(ActionEvent event) throws IOException {
        if (currentUser != null) {
            handleLogout(event);  // Déconnexion
        } else {
            loadLoginPage();  // Chargement de la page de connexion
        }
    }

    private void loadLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) mainContent.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page de connexion.");
        }
    }
    @FXML
    private void handleLogout(ActionEvent event) {
        setUser(null);  // Effacer l'utilisateur connecté
        loadLoginPage();  // Retourner à la page de connexion
    }

}