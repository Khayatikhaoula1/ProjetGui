package Services;

import Entites.Cours;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService {
    private Connection connection;

    public CoursService(Connection connection) {
        this.connection = connection;
    }

    public List<Cours> getAllCours() throws SQLException {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT * FROM cours";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                coursList.add(new Cours(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("professeur_id")
                ));
            }
        }
        return coursList;
    }

    public void createCours(Cours cours) throws SQLException {
        String query = "INSERT INTO cours (nom, description, professeur_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cours.getNom());
            stmt.setString(2, cours.getDescription());
            stmt.setInt(3, cours.getProfesseurId());
            stmt.executeUpdate();
        }
    }

    public void updateCours(Cours cours) throws SQLException {
        String query = "UPDATE cours SET nom = ?, description = ?, professeur_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cours.getNom());
            stmt.setString(2, cours.getDescription());
            stmt.setInt(3, cours.getProfesseurId());
            stmt.setInt(4, cours.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteCours(int id) throws SQLException {
        String query = "DELETE FROM cours WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
