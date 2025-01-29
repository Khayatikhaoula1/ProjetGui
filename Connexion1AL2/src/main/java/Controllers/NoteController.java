package Controllers;

import Services.NoteService;
import Entites.Note;
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
