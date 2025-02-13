package Controllers;

import Entites.Inscription;
import Services.InscriptionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class InscriptionController {

    @FXML
    private TextField etudiantIdField;
    @FXML
    private TextField coursIdField;
    @FXML
    private DatePicker dateInscriptionPicker;
    @FXML
    private Button btnInscrire;
    @FXML
    private Button btnAnnuler;

    private final InscriptionService inscriptionService;

    public InscriptionController() {
        this.inscriptionService = new InscriptionService();
    }

    @FXML
    private void handleInscription() {
        try {
            int etudiantId = Integer.parseInt(etudiantIdField.getText());
            int coursId = Integer.parseInt(coursIdField.getText());
            LocalDate localDate = dateInscriptionPicker.getValue();

            if (localDate == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une date d'inscription.");
                return;
            }

            Date dateInscription = Date.valueOf(localDate);
            Inscription inscription = new Inscription(0, etudiantId, coursId, dateInscription);

            inscriptionService.addInscription(inscription);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie !");
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des identifiants valides.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        etudiantIdField.clear();
        coursIdField.clear();
        dateInscriptionPicker.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
