package Services;

import Entites.Cours;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService {

    private Connection connection;

    public CoursService() {
        this.connection = DataSource.getInstance().getConn();
    }

    // Récupérer un cours par son ID
    public Cours getCoursById(int id) {
        String query = "SELECT c.id, c.nom, c.description, c.professeur_id, u.username AS professeur_nom " +
                "FROM cours c LEFT JOIN users u ON c.professeur_id = u.id WHERE c.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cours(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("professeur_id"),
                        rs.getString("professeur_nom")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du cours par ID : " + e.getMessage());
        }
        return null;
    }

    // Récupérer tous les cours
    public List<Cours> getAllCours() {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT c.id, c.nom, c.description, c.professeur_id, u.username AS professeur_nom " +
                "FROM cours c LEFT JOIN users u ON c.professeur_id = u.id";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                coursList.add(new Cours(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("professeur_id"),
                        rs.getString("professeur_nom")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la liste des cours : " + e.getMessage());
        }
        return coursList;
    }

    // Récupérer les cours d'un étudiant
    public List<Cours> getCoursesByStudent(int etudiantId) {
        List<Cours> courses = new ArrayList<>();
        String query = "SELECT c.id, c.nom, c.description, c.professeur_id, u.username AS professeur_nom " +
                "FROM cours c " +
                "JOIN inscriptions i ON c.id = i.cours_id " +
                "LEFT JOIN users u ON c.professeur_id = u.id " +
                "WHERE i.etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(new Cours(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("professeur_id"),
                        rs.getString("professeur_nom")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cours de l'étudiant : " + e.getMessage());
        }
        return courses;
    }

    // Ajouter un cours
    public void ajouterCours(Cours cours) {
        String query = "INSERT INTO cours (nom, description, professeur_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cours.getNom());
            stmt.setString(2, cours.getDescription());
            stmt.setInt(3, cours.getProfesseurId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du cours : " + e.getMessage());
        }
    }

    // Modifier un cours
    public void modifierCours(Cours cours) {
        String query = "UPDATE cours SET nom = ?, description = ?, professeur_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cours.getNom());
            stmt.setString(2, cours.getDescription());
            stmt.setInt(3, cours.getProfesseurId());
            stmt.setInt(4, cours.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du cours : " + e.getMessage());
        }
    }

    // Supprimer un cours
    public void supprimerCours(int id) {
        String query = "DELETE FROM cours WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du cours : " + e.getMessage());
        }
    }
}
