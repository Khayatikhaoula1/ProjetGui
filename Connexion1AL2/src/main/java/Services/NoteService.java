package Services;

import Entites.Inscription;
import Entites.Note;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteService {

    private Connection connection;

    // Constructeur modifié sans paramètre
    public NoteService() {
        this.connection = DataSource.getInstance().getConn();
    }

    public boolean addNote(Note note) {
        String query = "INSERT INTO Notes(etudiant_id, cours_id, note, date_evaluation) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = DataSource.getInstance().getConn().prepareStatement(query)) {  // Utilisation de DataSource.getInstance()
            ps.setInt(1, note.getEtudiantId());
            ps.setInt(2, note.getCoursId());  // Utilisation du coursId
            ps.setDouble(3, note.getNote());
            ps.setDate(4, note.getDateEvaluation());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer les notes d'un étudiant par son ID
    public List<Note> getNotesByStudent(int etudiantId) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes WHERE etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Passer coursId dans le constructeur
                notes.add(new Note(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),  // Récupérer cours_id ici
                        rs.getDouble("note"),
                        rs.getDate("date_evaluation")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des notes de l'étudiant : " + e.getMessage());
        }
        return notes;
    }

    // Méthode pour récupérer une note par ID
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
            System.err.println("Erreur lors de la récupération de la note : " + e.getMessage());
        }
        return null; // Si aucune note n'est trouvée
    }

    // Autres méthodes de NoteService...
}