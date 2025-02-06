package Controllers;

import Entites.Absence;
import Services.AbsenceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.Date;
import java.util.List;

public class AbsenceController {

    @FXML
    private TextField studentIdField, dateField, reasonField;
    @FXML
    private TableView<Absence> absenceTable;
    @FXML
    private TableColumn<Absence, Integer> colId, colStudentId;
    @FXML
    private TableColumn<Absence, Date> colDate;
    @FXML
    private TableColumn<Absence, String> colReason;
    @FXML
    private TableColumn<Absence, Void> colActions;

    private AbsenceService absenceService;
    private ObservableList<Absence> absenceList;

    public void initialize() {
        absenceService = new AbsenceService();
        absenceList = FXCollections.observableArrayList(absenceService.getAllAbsences());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("etudiantId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("motif"));

        // Add delete buttons in the action column
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Absence absence = getTableView().getItems().get(getIndex());
                    deleteAbsence(absence.getId());
                });
                deleteButton.getStyleClass().add("action-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        absenceTable.setItems(absenceList);
    }

    @FXML
    private void addAbsence() {
        try {
            int studentId = Integer.parseInt(studentIdField.getText());
            Date date = Date.valueOf(dateField.getText());
            String reason = reasonField.getText();

            Absence absence = new Absence(0, studentId, date, reason);
            absenceService.addAbsence(absence);
            absenceList.setAll(absenceService.getAllAbsences()); // Refresh table
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez entrer des valeurs valides.");
        }
    }

    private void deleteAbsence(int id) {
        absenceService.deleteAbsence(id);
        absenceList.setAll(absenceService.getAllAbsences()); // Refresh table
    }

    private void clearFields() {
        studentIdField.clear();
        dateField.clear();
        reasonField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
