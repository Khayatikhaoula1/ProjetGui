package Services;

import Entites.Note;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteService {

    private Connection connection;

    public NoteService() {
        this.connection = DataSource.getInstance().getConn();
    }

    // Ajouter une note
    public boolean addNote(Note note) {
        String query = "INSERT INTO Notes(etudiant_id, cours_id, note, note_controle, date_evaluation, resultat) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, note.getEtudiantId());
            ps.setInt(2, note.getCoursId());
            ps.setDouble(3, note.getNote());
            ps.setDouble(4, note.getNoteControle());
            ps.setDate(5, note.getDateEvaluation());
            ps.setString(6, note.getResultat());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la note: " + e.getMessage());
            return false;
        }
    }

    // Récupérer les notes d'un étudiant
    public List<Note> getNotesByStudent(int etudiantId) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes WHERE etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notes.add(new Note(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),
                        rs.getDouble("note"),
                        rs.getDouble("note_controle"),
                        rs.getDate("date_evaluation")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des notes: " + e.getMessage());
        }
        return notes;
    }

    // Modifier une note
    public boolean updateNote(Note note) {
        String query = "UPDATE notes SET etudiant_id = ?, cours_id = ?, note = ?, note_controle = ?, date_evaluation = ?, resultat = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, note.getEtudiantId());
            stmt.setInt(2, note.getCoursId());
            stmt.setDouble(3, note.getNote());
            stmt.setDouble(4, note.getNoteControle());
            stmt.setDate(5, note.getDateEvaluation());
            stmt.setString(6, note.getResultat());
            stmt.setInt(7, note.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la note: " + e.getMessage());
            return false;
        }
    }
    // Supprimer une note
    public boolean deleteNote(int id) {
        String query = "DELETE FROM notes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la note: " + e.getMessage());
            return false;
        }
    }

}