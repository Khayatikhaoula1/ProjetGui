package Controllers;

import Entites.Note;
import Services.NoteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class NoteController {
/*
    @FXML private TableView<Note> tableNotes;
    @FXML private TableColumn<Note, Integer> colId;
    @FXML private TableColumn<Note, Integer> colEtudiantId;
    @FXML private TableColumn<Note, Integer> colCoursId;
    @FXML private TableColumn<Note, String> colNomEtudiant;
    @FXML private TableColumn<Note, Double> colNote;
    @FXML private TableColumn<Note, Double> colNoteControle;
    @FXML private TableColumn<Note, String> colResultat;
    @FXML private TableColumn<Note, Object> colDateEvaluation; // Utilisation d'Object pour afficher le java.util.Date

    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;

    private final NoteService noteService = new NoteService();
    private ObservableList<Note> noteList;

    @FXML
    public void initialize() {
        // Initialiser les colonnes de la table
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEtudiantId.setCellValueFactory(new PropertyValueFactory<>("etudiantId"));
        colCoursId.setCellValueFactory(new PropertyValueFactory<>("coursId"));
        colNomEtudiant.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colNoteControle.setCellValueFactory(new PropertyValueFactory<>("noteControle"));
        colResultat.setCellValueFactory(new PropertyValueFactory<>("resultat"));
        colDateEvaluation.setCellValueFactory(new PropertyValueFactory<>("dateEvaluation"));

        // Charger les données
        loadNotes();
    }

    private void loadNotes() {
        List<Note> notes = noteService.getAllNotes();
        noteList = FXCollections.observableArrayList(notes);
        tableNotes.setItems(noteList);
    }

    @FXML
    private void ajouterNote(ActionEvent event) {
        Note newNote = showNoteDialog(null);
        if (newNote != null) {
            noteService.ajouterNote(newNote);
            loadNotes();
        }
    }

    @FXML
    private void modifierNote(ActionEvent event) {
        Note selectedNote = tableNotes.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            Note updatedNote = showNoteDialog(selectedNote);
            if (updatedNote != null) {
                noteService.modifierNote(updatedNote);
                loadNotes();
            }
        } else {
            showAlert("Aucune note sélectionnée", "Veuillez sélectionner une note à modifier.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void supprimerNote(ActionEvent event) {
        Note selectedNote = tableNotes.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cette note ?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                noteService.supprimerNote(selectedNote.getId());
                loadNotes();
            }
        } else {
            showAlert("Aucune note sélectionnée", "Veuillez sélectionner une note à supprimer.", Alert.AlertType.WARNING);
        }
    }

    private Note showNoteDialog(Note note) {
        Dialog<Note> dialog = new Dialog<>();
        dialog.setTitle(note == null ? "Ajouter une note" : "Modifier la note");

        // Champs de saisie
        TextField etudiantIdField = new TextField(note != null ? String.valueOf(note.getEtudiantId()) : "");
        TextField coursIdField = new TextField(note != null ? String.valueOf(note.getCoursId()) : "");
        TextField nomEtudiantField = new TextField(note != null ? note.getNomEtudiant() : "");
        TextField noteField = new TextField(note != null ? String.valueOf(note.getNote()) : "");
        TextField noteControleField = new TextField(note != null && note.getNoteControle() != null ? String.valueOf(note.getNoteControle()) : "");
        TextField resultatField = new TextField(note != null ? note.getResultat() : "");
        DatePicker dateEvaluationField = new DatePicker(note != null ?
                note.getDateEvaluation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null);

        VBox vbox = new VBox(10,
                new Label("Etudiant ID:"), etudiantIdField,
                new Label("Cours ID:"), coursIdField,
                new Label("Nom de l'étudiant:"), nomEtudiantField,
                new Label("Note:"), noteField,
                new Label("Note de contrôle:"), noteControleField,
                new Label("Résultat:"), resultatField,
                new Label("Date d'évaluation:"), dateEvaluationField
        );
        dialog.getDialogPane().setContent(vbox);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    int etudiantId = Integer.parseInt(etudiantIdField.getText().trim());
                    int coursId = Integer.parseInt(coursIdField.getText().trim());
                    String nomEtudiant = nomEtudiantField.getText().trim();
                    double noteValue = Double.parseDouble(noteField.getText().trim());
                    Double noteControle = null;
                    if (!noteControleField.getText().trim().isEmpty()) {
                        noteControle = Double.parseDouble(noteControleField.getText().trim());
                    }
                    String resultat = resultatField.getText().trim();
                    java.util.Date dateEvaluation = java.sql.Date.valueOf(dateEvaluationField.getValue());

                    return new Note(
                            note != null ? note.getId() : 0,
                            etudiantId,
                            coursId,
                            noteValue,
                            dateEvaluation,
                            noteControle,
                            resultat,
                            nomEtudiant
                    );
                } catch (NumberFormatException e) {
                    showAlert("Erreur de saisie", "Veuillez vérifier que les champs numériques contiennent des valeurs valides.", Alert.AlertType.ERROR);
                } catch (Exception e) {
                    showAlert("Erreur", "Une erreur est survenue : " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        Optional<Note> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    } */
}
