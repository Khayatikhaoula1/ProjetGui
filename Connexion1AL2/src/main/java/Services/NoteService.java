package Services;

import Entites.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteService {
    private Connection connection;

    public NoteService(Connection connection) {
        this.connection = connection;
    }

    public void ajouterNote(int etudiantId, int coursId, double note) throws SQLException {
        String query = "INSERT INTO notes (etudiant_id, cours_id, note, date_note) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, coursId);
            stmt.setDouble(3, note);
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
    }

    public List<Note> getNotesByEtudiant(int etudiantId) throws SQLException {
        List<Note> notesList = new ArrayList<>();
        String query = "SELECT * FROM notes WHERE etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notesList.add(new Note(
                            rs.getInt("id"),
                            rs.getInt("etudiant_id"),
                            rs.getInt("cours_id"),
                            rs.getDouble("note"),
                            rs.getDate("date_note")
                    ));
                }
            }
        }
        return notesList;
    }

    public void updateNote(Note note) throws SQLException {
        String query = "UPDATE notes SET note = ?, date_note = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, note.getNote());
            stmt.setDate(2, new java.sql.Date(note.getDateNote().getTime()));
            stmt.setInt(3, note.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteNote(int id) throws SQLException {
        String query = "DELETE FROM notes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
