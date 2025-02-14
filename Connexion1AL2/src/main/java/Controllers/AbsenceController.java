package Controllers;

import Entites.Absence;
import Services.AbsenceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class AbsenceController {

    @FXML private TableView<Absence> tableAbsences;
    @FXML private TableColumn<Absence, Integer> colId;
    @FXML private TableColumn<Absence, Integer> colEtudiantId;
    @FXML private TableColumn<Absence, Date> colDate;
    @FXML private TableColumn<Absence, String> colMotif;
    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    private final AbsenceService absenceService = new AbsenceService();
    private ObservableList<Absence> absenceList;
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEtudiantId.setCellValueFactory(new PropertyValueFactory<>("etudiantId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        loadAbsences();
    }
    private void loadAbsences() {
        List<Absence> absences = absenceService.getAllAbsences();
        absenceList = FXCollections.observableArrayList(absences);
        tableAbsences.setItems(absenceList);
    }

    @FXML
    private void ajouterAbsence(ActionEvent event) {
        Absence newAbsence = showAbsenceDialog(null);
        if (newAbsence != null) {
            absenceService.addAbsence(newAbsence);
            loadAbsences();
        }
    }

    @FXML
    private void modifierAbsence(ActionEvent event) {
        Absence selectedAbsence = tableAbsences.getSelectionModel().getSelectedItem();
        if (selectedAbsence != null) {
            Absence updatedAbsence = showAbsenceDialog(selectedAbsence);
            if (updatedAbsence != null) {
                absenceService.deleteAbsence(selectedAbsence.getId());
                absenceService.addAbsence(updatedAbsence);
                loadAbsences();
            }
        } else {
            showAlert("Aucune absence sélectionnée", "Veuillez sélectionner une absence à modifier.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void supprimerAbsence(ActionEvent event) {
        Absence selectedAbsence = tableAbsences.getSelectionModel().getSelectedItem();
        if (selectedAbsence != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cette absence ?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                absenceService.deleteAbsence(selectedAbsence.getId());
                loadAbsences();
            }
        } else {
            showAlert("Aucune absence sélectionnée", "Veuillez sélectionner une absence à supprimer.", Alert.AlertType.WARNING);
        }
    }

    private Absence showAbsenceDialog(Absence absence) {
        Dialog<Absence> dialog = new Dialog<>();
        dialog.setTitle(absence == null ? "Ajouter une absence" : "Modifier l'absence");
        TextField etudiantIdField = new TextField(absence != null ? String.valueOf(absence.getEtudiantId()) : "");
        DatePicker datePicker = new DatePicker();
        if (absence != null) {
            datePicker.setValue(absence.getDate().toLocalDate());
        }
        TextField motifField = new TextField(absence != null ? absence.getMotif() : "");
        VBox vbox = new VBox(10,
                new Label("ID Étudiant:"), etudiantIdField,
                new Label("Date:"), datePicker,
                new Label("Motif:"), motifField
        );
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    int etudiantId = Integer.parseInt(etudiantIdField.getText().trim());
                    return new Absence(
                            absence != null ? absence.getId() : 0,
                            etudiantId,
                            Date.valueOf(datePicker.getValue()),
                            motifField.getText()
                    );
                } catch (NumberFormatException e) {
                    showAlert("Erreur de saisie", "L'ID de l'étudiant doit être un nombre valide.", Alert.AlertType.ERROR);
                }
            }
            return null;
        });
        Optional<Absence> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}