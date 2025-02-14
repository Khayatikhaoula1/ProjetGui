package Controllers;

import Entites.User;
import Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {

    private MainController mainController;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        // Permet de se connecter en appuyant sur Entrée
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Vérifie les informations d'identification de l'utilisateur
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showErrorAlert("Erreur", "Nom d'utilisateur ou mot de passe manquant.");
            return;
        }

        try {
            User user = userService.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                // Authentification réussie, retour à la page principale
                MainController mainController = MainController.getInstance();
                mainController.setUser(user);
                mainController.loadDashboardView(null);  // Redirection vers le tableau de bord
            } else {
                showErrorAlert("Erreur", "Nom d'utilisateur ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            showErrorAlert("Erreur de connexion", "Une erreur est survenue lors de la tentative de connexion.");
            e.printStackTrace();  // Affiche l'exception dans la console pour le débogage
        }
    }

    // Méthode pour afficher une alerte d'erreur
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
