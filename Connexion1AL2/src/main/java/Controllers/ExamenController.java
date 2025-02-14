package Controllers;

import Entites.Examen;
import Services.ExamenService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.Date;
import java.util.List;

public class ExamenController {

    @FXML
    private TableView<Examen> tableExamen;
    @FXML
    private TableColumn<Examen, Integer> colIdExamen;
    @FXML
    private TableColumn<Examen, String> colNom;
    @FXML
    private TableColumn<Examen, Date> colDateExamen;
    @FXML
    private TextField txtNomExamen;  // Champ de texte pour le nom de l'examen
    @FXML
    private DatePicker dateExamen;   // Sélecteur de date pour la date de l'examen

    private ExamenService examenService;

    private Examen examenToUpdate;  // Examen à modifier (si sélectionné)

    public ExamenController() {
        examenService = new ExamenService();
    }

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la table
        colIdExamen.setCellValueFactory(new PropertyValueFactory<>("idExamen"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDateExamen.setCellValueFactory(new PropertyValueFactory<>("dateExamen"));
        loadExamen();  // Charger les examens dès l'initialisation
    }

    // Charger tous les examens dans la table
    public void loadExamen() {
        List<Examen> examenList = examenService.getAllExamen();
        tableExamen.getItems().setAll(examenList);
    }

    // Ajouter un examen
    public void addExamen() {
        String nom = txtNomExamen.getText();
        Date date = Date.valueOf(dateExamen.getValue());  // Convertir la date du DatePicker en java.sql.Date
        if (nom != null && !nom.isEmpty() && date != null) {
            Examen examen = new Examen(0, nom, date, 1);  // 1 est l'ID du cours, à adapter selon ta logique
            examenService.addExamen(examen);
            loadExamen();  // Recharger les examens après ajout
            clearForm();  // Effacer le formulaire après l'ajout
        } else {
            showAlert(Alert.AlertType.WARNING, "Champs incomplets", "Veuillez remplir tous les champs.");
        }
    }

    // Modifier un examen sélectionné
    public void updateExamen() {
        if (examenToUpdate != null) {
            // Vérifier que les champs ne sont pas vides avant de mettre à jour
            String nom = txtNomExamen.getText();
            Date date = Date.valueOf(dateExamen.getValue());

            if (nom != null && !nom.isEmpty() && date != null) {
                // Mise à jour des données de l'examen
                examenToUpdate.setNom(nom);
                examenToUpdate.setDateExamen(date);

                // Appel à la méthode de mise à jour
                examenService.updateExamen(examenToUpdate);

                loadExamen();  // Recharger les examens après modification
                clearForm();  // Effacer le formulaire après la modification
                examenToUpdate = null;  // Réinitialiser l'examen à modifier
            } else {
                showAlert(Alert.AlertType.WARNING, "Champs incomplets", "Veuillez remplir tous les champs.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélectionnez un Examen", "Veuillez sélectionner un examen à modifier.");
        }
    }

    // Supprimer un examen sélectionné
    public void deleteExamen() {
        Examen selectedExamen = tableExamen.getSelectionModel().getSelectedItem();
        if (selectedExamen != null) {
            examenService.deleteExamen(selectedExamen.getIdExamen());
            loadExamen();  // Recharger les examens après suppression
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélectionnez un Examen", "Veuillez sélectionner un examen à supprimer.");
        }
    }

    // Méthode pour vider les champs du formulaire
    private void clearForm() {
        txtNomExamen.clear();
        dateExamen.setValue(null);
    }

    // Afficher une alerte
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Gérer la sélection d'un examen dans la table pour modification
    @FXML
    public void onTableExamenClick(MouseEvent event) {
        Examen selectedExamen = tableExamen.getSelectionModel().getSelectedItem();
        if (selectedExamen != null) {
            examenToUpdate = selectedExamen;  // Enregistrer l'examen sélectionné pour modification
            txtNomExamen.setText(selectedExamen.getNom());
            dateExamen.setValue(selectedExamen.getDateExamen().toLocalDate());  // Convertir la date en LocalDate
        }
    }
}
