package Controllers;

import Entites.Cours;
import Services.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class CoursController {

    @FXML private TableView<Cours> tableCours;
    @FXML private TableColumn<Cours, Integer> colId;
    @FXML private TableColumn<Cours, String> colNom;
    @FXML private TableColumn<Cours, String> colDescription;
    @FXML private TableColumn<Cours, Integer> colProfesseurId;
    @FXML private TableColumn<Cours, String> colProfesseurNom;

    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;

    private final CoursService coursService = new CoursService();
    private ObservableList<Cours> coursList;

    @FXML
    public void initialize() {
        // Initialiser les colonnes de la table
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProfesseurId.setCellValueFactory(new PropertyValueFactory<>("professeurId"));
        colProfesseurNom.setCellValueFactory(new PropertyValueFactory<>("professeurNom"));

        // Charger les données
        loadCourses();
    }

    private void loadCourses() {
        List<Cours> courses = coursService.getAllCours();
        coursList = FXCollections.observableArrayList(courses);
        tableCours.setItems(coursList);
    }

    @FXML
    private void ajouterCours(ActionEvent event) {
        Cours newCours = showCoursDialog(null);
        if (newCours != null) {
            coursService.ajouterCours(newCours);
            loadCourses();
        }
    }

    @FXML
    private void modifierCours(ActionEvent event) {
        Cours selectedCours = tableCours.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            Cours updatedCours = showCoursDialog(selectedCours);
            if (updatedCours != null) {
                coursService.modifierCours(updatedCours);
                loadCourses();
            }
        } else {
            showAlert("Aucun cours sélectionné", "Veuillez sélectionner un cours à modifier.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void supprimerCours(ActionEvent event) {
        Cours selectedCours = tableCours.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce cours ?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                coursService.supprimerCours(selectedCours.getId());
                loadCourses();
            }
        } else {
            showAlert("Aucun cours sélectionné", "Veuillez sélectionner un cours à supprimer.", Alert.AlertType.WARNING);
        }
    }

    private Cours showCoursDialog(Cours cours) {
        Dialog<Cours> dialog = new Dialog<>();
        dialog.setTitle(cours == null ? "Ajouter un cours" : "Modifier le cours");

        // Champs de saisie
        TextField nomField = new TextField(cours != null ? cours.getNom() : "");
        TextField descriptionField = new TextField(cours != null ? cours.getDescription() : "");
        TextField professeurIdField = new TextField(cours != null ? String.valueOf(cours.getProfesseurId()) : "");
        TextField professeurNomField = new TextField(cours != null ? cours.getProfesseurNom() : "");
        professeurNomField.setEditable(false); // On empêche la modification directe du nom du professeur

        VBox vbox = new VBox(10,
                new Label("Nom:"), nomField,
                new Label("Description:"), descriptionField,
                new Label("Professeur ID:"), professeurIdField,
                new Label("Professeur Nom (généré automatiquement):"), professeurNomField
        );
        dialog.getDialogPane().setContent(vbox);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    int professeurId = Integer.parseInt(professeurIdField.getText().trim());
                    return new Cours(
                            cours != null ? cours.getId() : 0,
                            nomField.getText(),
                            descriptionField.getText(),
                            professeurId,
                            "" // Nom du professeur sera rempli via la base de données
                    );
                } catch (NumberFormatException e) {
                    showAlert("Erreur de saisie", "L'ID du professeur doit être un nombre valide.", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        Optional<Cours> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}