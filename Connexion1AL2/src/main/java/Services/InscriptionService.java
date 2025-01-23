package Services;

import Entites.Inscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscriptionService {
    private Connection connection;

    public InscriptionService(Connection connection) {
        this.connection = connection;
    }

    public void inscrireEtudiant(int etudiantId, int coursId) throws SQLException {
        String query = "INSERT INTO inscriptions (etudiant_id, cours_id, date_inscription) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, coursId);
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
    }

    public List<Inscription> getInscriptionsByEtudiant(int etudiantId) throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM inscriptions WHERE etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    inscriptions.add(new Inscription(
                            rs.getInt("id"),
                            rs.getInt("etudiant_id"),
                            rs.getInt("cours_id"),
                            rs.getDate("date_inscription")
                    ));
                }
            }
        }
        return inscriptions;
    }

    public void updateInscription(Inscription inscription) throws SQLException {
        String query = "UPDATE inscriptions SET etudiant_id = ?, cours_id = ?, date_inscription = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, inscription.getEtudiantId());
            stmt.setInt(2, inscription.getCoursId());
            stmt.setDate(3, new java.sql.Date(inscription.getDateInscription().getTime()));
            stmt.setInt(4, inscription.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteInscription(int id) throws SQLException {
        String query = "DELETE FROM inscriptions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
