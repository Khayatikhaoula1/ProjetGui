/*package Controllers;

import Entites.Inscription;
import Services.InscriptionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class InscriptionController {

    @FXML
    private TextField etudiantIdField;

    @FXML
    private TextField coursIdField;

    @FXML
    private DatePicker dateInscriptionPicker;

    @FXML
    private TableView<Inscription> inscriptionTable;

    @FXML
    private TableColumn<Inscription, Integer> idColumn;

    @FXML
    private TableColumn<Inscription, Integer> etudiantIdColumn;

    @FXML
    private TableColumn<Inscription, Integer> coursIdColumn;

    @FXML
    private TableColumn<Inscription, Date> dateColumn;

    private InscriptionService inscriptionService = new InscriptionService();
    private ObservableList<Inscription> inscriptionList;

    @FXML
    public void initialize() {
        // Configuration des colonnes du TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        etudiantIdColumn.setCellValueFactory(new PropertyValueFactory<>("etudiantId"));
        coursIdColumn.setCellValueFactory(new PropertyValueFactory<>("coursId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));

        loadInscriptions();
    }

    // Charge l'ensemble des inscriptions depuis la base de données
    private void loadInscriptions() {
        List<Inscription> inscriptions = inscriptionService.getAllInscriptions();
        inscriptionList = FXCollections.observableArrayList(inscriptions);
        inscriptionTable.setItems(inscriptionList);
    }

    // Ajoute une nouvelle inscription
    @FXML
    private void handleAddInscription(ActionEvent event) {
        String etudiantIdText = etudiantIdField.getText();
        String coursIdText = coursIdField.getText();
        LocalDate localDate = dateInscriptionPicker.getValue();

        if (etudiantIdText.isEmpty() || coursIdText.isEmpty() || localDate == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.WARNING);
            return;
        }

        try {
            int etudiantId = Integer.parseInt(etudiantIdText);
            int coursId = Integer.parseInt(coursIdText);
            Date dateInscription = Date.valueOf(localDate);

            // On passe 0 pour l'id puisque la base de données s'en charge (AUTO_INCREMENT)
            Inscription inscription = new Inscription(0, etudiantId, coursId, dateInscription);
            inscriptionService.addInscription(inscription);
            showAlert("Succès", "Inscription ajoutée avec succès.", Alert.AlertType.INFORMATION);
            loadInscriptions();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les IDs doivent être des nombres.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Supprime l'inscription sélectionnée dans le TableView
    @FXML
    private void handleDeleteInscription(ActionEvent event) {
        Inscription selected = inscriptionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner une inscription à supprimer.", Alert.AlertType.WARNING);
            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Voulez-vous vraiment supprimer cette inscription ?");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            inscriptionService.deleteInscription(selected.getId());
            showAlert("Succès", "Inscription supprimée avec succès.", Alert.AlertType.INFORMATION);
            loadInscriptions();
        }
    }

    // Réinitialise les champs du formulaire
    private void clearFields() {
        etudiantIdField.clear();
        coursIdField.clear();
        dateInscriptionPicker.setValue(null);
    }

    // Affiche une alerte
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/
package Controllers;

import Entites.Role;
import Entites.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class InscriptionController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    private final UserService userService = new UserService();

    @FXML
    private void handleInscription(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        String email = emailField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        try {
            // Vérifier si l'utilisateur existe déjà
            User existingUser = userService.getUserByUsername(username);
            if (existingUser != null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Ce nom d'utilisateur est déjà pris.");
                return;
            }

            // Récupérer le rôle par défaut (Étudiant)
            Role defaultRole = userService.getRoleByName("Étudiant");
            if (defaultRole == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le rôle 'Étudiant' n'existe pas.");
                return;
            }

            // Créer l'utilisateur
            boolean success = userService.createUser(username, password, defaultRole, email);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie !");
                goToLogin(event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'inscription.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Impossible d'ajouter l'utilisateur : " + e.getMessage());
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir la page de connexion.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
