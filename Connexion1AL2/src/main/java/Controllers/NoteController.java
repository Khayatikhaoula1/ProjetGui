package Controllers;

import Entites.Note;
import Services.NoteService;

import java.util.List;

public class NoteController {

    private NoteService noteService;

    public NoteController() {
        this.noteService = new NoteService();
    }

    // Récupérer toutes les notes d'un étudiant
    public List<Note> getNotesByStudent(int etudiantId) {
        return noteService.getNotesByStudent(etudiantId);
    }
}
