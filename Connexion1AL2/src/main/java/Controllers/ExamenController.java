package Controllers;

import Entites.Examen;
import Services.ExamenService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ExamenController {

    @FXML
    private TableView<Examen> tableExamen;
    @FXML
    private TableColumn<Examen, Integer> colIdExamen;
    @FXML
    private TableColumn<Examen, String> colNom; // Mise à jour pour afficher le nom
    @FXML
    private TableColumn<Examen, java.sql.Date> colDateExamen;

    private ExamenService examenService;

    public ExamenController() {
        examenService = new ExamenService();  // Initialisation du service
    }

    // Méthode pour charger tous les Examen
    public void loadExamen() {
        List<Examen> examenList = examenService.getAllExamen();
        if (examenList.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Aucun Examen", "Aucun Examen trouvé.");
        } else {
            tableExamen.getItems().setAll(examenList);
        }
    }

    // Initialiser la TableView avec les colonnes appropriées
    public void initialize() {
        colIdExamen.setCellValueFactory(new PropertyValueFactory<>("idExamen"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDateExamen.setCellValueFactory(new PropertyValueFactory<>("dateExamen"));
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
