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

    // Récupérer une note par son ID
    public Note getNoteById(int id) {
        String query = "SELECT id, etudiant_id, cours_id, note, date_evaluation, note_controle, resultat, nomEtudiant " +
                "FROM notes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Note(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),
                        rs.getDouble("note"),
                        rs.getDate("date_evaluation"),
                        rs.getObject("note_controle") != null ? rs.getDouble("note_controle") : null,
                        rs.getString("resultat"),
                        rs.getString("nomEtudiant")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la note par ID : " + e.getMessage());
        }
        return null;
    }

    // Récupérer toutes les notes
    public List<Note> getAllNotes() {
        List<Note> notesList = new ArrayList<>();
        String query = "SELECT id, etudiant_id, cours_id, note, date_evaluation, note_controle, resultat, nomEtudiant FROM notes";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),
                        rs.getDouble("note"),
                        rs.getDate("date_evaluation"),
                        rs.getObject("note_controle") != null ? rs.getDouble("note_controle") : null,
                        rs.getString("resultat"),
                        rs.getString("nomEtudiant")
                );
                notesList.add(note);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des notes : " + e.getMessage());
        }
        return notesList;
    }

    // Récupérer les notes d'un étudiant
    public List<Note> getNotesByStudent(int etudiantId) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT id, etudiant_id, cours_id, note, date_evaluation, note_controle, resultat, nomEtudiant " +
                "FROM notes WHERE etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),
                        rs.getDouble("note"),
                        rs.getDate("date_evaluation"),
                        rs.getObject("note_controle") != null ? rs.getDouble("note_controle") : null,
                        rs.getString("resultat"),
                        rs.getString("nomEtudiant")
                );
                notes.add(note);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des notes de l'étudiant : " + e.getMessage());
        }
        return notes;
    }

    // Ajouter une note
    public void ajouterNote(Note note) {
        String query = "INSERT INTO notes (etudiant_id, cours_id, note, date_evaluation, note_controle, resultat, nomEtudiant) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, note.getEtudiantId());
            stmt.setInt(2, note.getCoursId());
            stmt.setDouble(3, note.getNote());
            stmt.setDate(4, new java.sql.Date(note.getDateEvaluation().getTime()));
            if (note.getNoteControle() == null) {
                stmt.setNull(5, Types.DOUBLE);
            } else {
                stmt.setDouble(5, note.getNoteControle());
            }
            stmt.setString(6, note.getResultat());
            stmt.setString(7, note.getNomEtudiant());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("L'ajout de la note a échoué, aucune ligne affectée.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    note.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("L'ajout de la note a échoué, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la note : " + e.getMessage());
        }
    }

    // Modifier une note
    public void modifierNote(Note note) {
        String query = "UPDATE notes SET etudiant_id = ?, cours_id = ?, note = ?, date_evaluation = ?, note_controle = ?, resultat = ?, nomEtudiant = ? " +
                "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, note.getEtudiantId());
            stmt.setInt(2, note.getCoursId());
            stmt.setDouble(3, note.getNote());
            stmt.setDate(4, new java.sql.Date(note.getDateEvaluation().getTime()));
            if (note.getNoteControle() == null) {
                stmt.setNull(5, Types.DOUBLE);
            } else {
                stmt.setDouble(5, note.getNoteControle());
            }
            stmt.setString(6, note.getResultat());
            stmt.setString(7, note.getNomEtudiant());
            stmt.setInt(8, note.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la note : " + e.getMessage());
        }
    }

    // Supprimer une note
    public void supprimerNote(int id) {
        String query = "DELETE FROM notes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la note : " + e.getMessage());
        }
    }
}
