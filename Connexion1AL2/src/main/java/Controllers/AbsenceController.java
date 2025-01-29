package Controllers;

import Services.AbsenceService;
import Entites.Absence;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AbsenceController {

    @FXML
    private TableView<Absence> tableAbsences;
    @FXML
    private TableColumn<Absence, Integer> colId;
    @FXML
    private TableColumn<Absence, Integer> colEtudiantId;
    @FXML
    private TableColumn<Absence, Integer> colCoursId;
    @FXML
    private TableColumn<Absence, String> colMotif;
    @FXML
    private TableColumn<Absence, java.sql.Date> colDateAbsence;

    private AbsenceService absenceService;

    public AbsenceController() {
        absenceService = new AbsenceService();  // Initialisation du service
    }

    // Méthode pour charger les absences d'un étudiant
    public void loadAbsences(int etudiantId) {
        List<Absence> absences = absenceService.getAbsencesByStudent(etudiantId);
        if (absences.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Aucune absence", "Aucune absence trouvée pour cet étudiant.");
        } else {
            tableAbsences.getItems().setAll(absences);
        }
    }

    // Initialiser la TableView avec les colonnes appropriées
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEtudiantId.setCellValueFactory(new PropertyValueFactory<>("etudiantId"));
        colCoursId.setCellValueFactory(new PropertyValueFactory<>("coursId"));
        colMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        colDateAbsence.setCellValueFactory(new PropertyValueFactory<>("dateAbsence"));
    }

    // Afficher un message d'alerte
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
