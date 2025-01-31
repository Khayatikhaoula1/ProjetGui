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
        String query = "INSERT INTO Notes(etudiant_id, cours_id, note, date_evaluation) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, note.getEtudiantId());
            ps.setInt(2, note.getCoursId());
            ps.setDouble(3, note.getNote());
            ps.setDate(4, note.getDateEvaluation());

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
                        rs.getDate("date_evaluation")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des notes: " + e.getMessage());
        }
        return notes;
    }

    // Récupérer une note par ID
    public Note getNoteById(int id) {
        String query = "SELECT * FROM notes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Note(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),
                        rs.getDouble("note"),
                        rs.getDate("date_evaluation")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la note: " + e.getMessage());
        }
        return null;
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

    // Modifier une note
    public boolean updateNote(Note note) {
        String query = "UPDATE notes SET etudiant_id = ?, cours_id = ?, note = ?, date_evaluation = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, note.getEtudiantId());
            stmt.setInt(2, note.getCoursId());
            stmt.setDouble(3, note.getNote());
            stmt.setDate(4, note.getDateEvaluation());
            stmt.setInt(5, note.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la note: " + e.getMessage());
            return false;
        }
    }
}
