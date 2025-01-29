package Services;

import Entites.Cours;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService {

    private Connection connection;

    // Utilisation du DataSource pour la gestion de la connexion
    public CoursService() {
        this.connection = DataSource.getInstance().getConn();
    }

    // Récupérer un cours par son ID
    public Cours getCoursById(int id) {
        String query = "SELECT * FROM cours WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int professeurId = rs.getObject("professeur_id") != null ? rs.getInt("professeur_id") : 0;
                return new Cours(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        professeurId
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du cours : " + e.getMessage());
        }
        return null;
    }
    // Récupérer tous les cours
    public List<Cours> getAllCours() {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT * FROM cours";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int professeurId = rs.getObject("professeur_id") != null ? rs.getInt("professeur_id") : 0;
                coursList.add(new Cours(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        professeurId
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cours : " + e.getMessage());
        }
        return coursList;
    }

    // Récupérer les cours d'un étudiant
    public List<Cours> getCoursesByStudent(int etudiantId) {
        List<Cours> courses = new ArrayList<>();
        String query = "SELECT c.* FROM cours c JOIN inscriptions i ON c.id = i.cours_id WHERE i.etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int professeurId = rs.getObject("professeur_id") != null ? rs.getInt("professeur_id") : 0;
                courses.add(new Cours(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        professeurId
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cours de l'étudiant : " + e.getMessage());
        }
        return courses;
    }
}