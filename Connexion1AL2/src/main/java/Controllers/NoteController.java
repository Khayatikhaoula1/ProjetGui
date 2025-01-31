package Controllers;

import Services.NoteService;
import Entites.Note;
import java.util.List;

public class NoteController {

    private NoteService noteService;

    public NoteController() {
        this.noteService = new NoteService();
    }

    // Ajouter une note
    public boolean addNote(Note note) {
        return noteService.addNote(note);
    }

    // Récupérer toutes les notes d'un étudiant
    public List<Note> getNotesByStudent(int etudiantId) {
        return noteService.getNotesByStudent(etudiantId);
    }

    // Récupérer une note par ID
    public Note getNoteById(int noteId) {
        return noteService.getNoteById(noteId);
    }

    // Supprimer une note
    public boolean deleteNote(int noteId) {
        return noteService.deleteNote(noteId);
    }

    // Modifier une note
    public boolean updateNote(Note note) {
        return noteService.updateNote(note);
    }
}
