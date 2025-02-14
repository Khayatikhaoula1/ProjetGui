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

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class NoteController {

    @FXML private TableView<Note> tableNotes;
    @FXML private TableColumn<Note, Integer> colId;
    @FXML private TableColumn<Note, Integer> colEtudiantId;
    @FXML private TableColumn<Note, Integer> colCoursId;
    @FXML private TableColumn<Note, Double> colNote;
    @FXML private TableColumn<Note, Double> colNoteControle;
    @FXML private TableColumn<Note, Date> colDateEvaluation;
    @FXML private TableColumn<Note, String> colResultat;

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
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colNoteControle.setCellValueFactory(new PropertyValueFactory<>("noteControle"));
        colDateEvaluation.setCellValueFactory(new PropertyValueFactory<>("dateEvaluation"));
        colResultat.setCellValueFactory(new PropertyValueFactory<>("resultat"));

        // Charger les données
        loadNotes();
    }

    private void loadNotes() {
        List<Note> notes = noteService.getNotesByStudent(22); // Exemple d'ID étudiant
        noteList = FXCollections.observableArrayList(notes);
        tableNotes.setItems(noteList);
    }

    @FXML
    private void ajouterNote(ActionEvent event) {
        Note newNote = showNoteDialog(null);
        if (newNote != null) {
            noteService.addNote(newNote);
            loadNotes();
        }
    }

    @FXML
    private void modifierNote(ActionEvent event) {
        Note selectedNote = tableNotes.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            Note updatedNote = showNoteDialog(selectedNote);
            if (updatedNote != null) {
                noteService.updateNote(updatedNote);
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
                noteService.deleteNote(selectedNote.getId());
                loadNotes();
            }
        } else {
            showAlert("Aucune note sélectionnée", "Veuillez sélectionner une note à supprimer.", Alert.AlertType.WARNING);
        }
    }

    private Note showNoteDialog(Note note) {
        // Boîte de dialogue personnalisée pour ajouter/modifier une note
        Dialog<Note> dialog = new Dialog<>();
        dialog.setTitle(note == null ? "Ajouter une note" : "Modifier la note");

        // Champs de saisie
        TextField etudiantIdField = new TextField(note != null ? String.valueOf(note.getEtudiantId()) : "");
        TextField coursIdField = new TextField(note != null ? String.valueOf(note.getCoursId()) : "");
        TextField noteField = new TextField(note != null ? String.valueOf(note.getNote()) : "");
        TextField noteControleField = new TextField(note != null ? String.valueOf(note.getNoteControle()) : "");
        DatePicker dateEvaluationPicker = new DatePicker(note != null ? note.getDateEvaluation().toLocalDate() : null);

        VBox vbox = new VBox(10,
                new Label("Étudiant ID:"), etudiantIdField,
                new Label("Cours ID:"), coursIdField,
                new Label("Note:"), noteField,
                new Label("Note de contrôle:"), noteControleField,
                new Label("Date d'évaluation:"), dateEvaluationPicker
        );
        dialog.getDialogPane().setContent(vbox);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Note(
                        note != null ? note.getId() : 0,
                        Integer.parseInt(etudiantIdField.getText()),
                        Integer.parseInt(coursIdField.getText()),
                        Double.parseDouble(noteField.getText()),
                        Double.parseDouble(noteControleField.getText()),
                        Date.valueOf(dateEvaluationPicker.getValue())
                );
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
    }
}