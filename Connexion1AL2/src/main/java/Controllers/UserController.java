package Controllers;

import Entites.Role;
import Entites.User;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    private final UserService userService = new UserService();
    private ObservableList<User> userList;

    @FXML
    public void initialize() {
        // Configurer les colonnes du tableau
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(cellData -> {
            Role role = cellData.getValue().getRole();
            return role != null ? new javafx.beans.property.SimpleStringProperty(role.getNom()) : new javafx.beans.property.SimpleStringProperty("Inconnu");
        });

        // Charger les rôles dans le ComboBox
        roleComboBox.setItems(FXCollections.observableArrayList("Administrateur", "Professeur", "Étudiant"));

        // Charger les utilisateurs
        loadUsers();
    }

    private void loadUsers() {
        try {
            List<User> users = userService.getAllUsers();
            userList = FXCollections.observableArrayList(users);
            userTable.setItems(userList);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les utilisateurs : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void handleUpdateUser(ActionEvent event) {
        // Récupérer l'utilisateur sélectionné dans le tableau
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner un utilisateur à mettre à jour.", Alert.AlertType.WARNING);
            return;
        }

        // Récupérer les valeurs des champs de saisie
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String selectedRole = roleComboBox.getValue();

        // Vérifier que tous les champs sont remplis
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || selectedRole == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis pour la mise à jour.", Alert.AlertType.WARNING);
            return;
        }

        // Récupérer l'ID du rôle correspondant
        int roleId = getRoleIdByName(selectedRole);
        if (roleId == -1) {
            showAlert("Erreur", "Rôle invalide.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Récupérer l'objet Role depuis le service
            Role role = userService.getRoleById(roleId);
            // Appeler la méthode d'update dans le service
            boolean success = userService.updateUser(selectedUser.getId(), username, password, role, email);
            if (success) {
                showAlert("Succès", "Utilisateur mis à jour avec succès.", Alert.AlertType.INFORMATION);
                loadUsers();
                clearFields();
            } else {
                showAlert("Erreur", "Échec de la mise à jour de l'utilisateur.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur SQL : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String selectedRole = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || selectedRole == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.WARNING);
            return;
        }

        int roleId = getRoleIdByName(selectedRole);
        if (roleId == -1) {
            showAlert("Erreur", "Rôle invalide.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Role role = userService.getRoleById(roleId);
            boolean success = userService.createUser(username, password, role, email);
            if (success) {
                showAlert("Succès", "Utilisateur ajouté avec succès.", Alert.AlertType.INFORMATION);
                loadUsers();
                clearFields();
            } else {
                showAlert("Erreur", "Échec de l'ajout de l'utilisateur.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur SQL : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner un utilisateur à supprimer.", Alert.AlertType.WARNING);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cet utilisateur ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean success = userService.deleteUser(selectedUser.getId());
                if (success) {
                    showAlert("Succès", "Utilisateur supprimé avec succès.", Alert.AlertType.INFORMATION);
                    loadUsers();
                } else {
                    showAlert("Erreur", "Échec de la suppression de l'utilisateur.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur SQL : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private int getRoleIdByName(String roleName) {
        return switch (roleName) {
            case "Administrateur" -> 1;
            case "Professeur" -> 2;
            case "Étudiant" -> 3;
            default -> -1;
        };
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
        roleComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}