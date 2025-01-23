package Services;

import Entites.Inscription;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionService {

    private Connection connection;

    // Utilisation du DataSource pour la gestion de la connexion
    public InscriptionService() {
        this.connection = DataSource.getInstance().getConn();
    }

    // Récupérer une inscription par son ID
    public Inscription getInscriptionById(int id) {
        String query = "SELECT * FROM inscriptions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Inscription(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),
                        rs.getDate("date_inscription")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'inscription : " + e.getMessage());
        }
        return null;
    }

    // Récupérer toutes les inscriptions
    public List<Inscription> getAllInscriptions() {
        List<Inscription> inscriptionsList = new ArrayList<>();
        String query = "SELECT * FROM inscriptions";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                inscriptionsList.add(new Inscription(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),
                        rs.getDate("date_inscription")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des inscriptions : " + e.getMessage());
        }
        return inscriptionsList;
    }

    // Ajouter une inscription
    public void addInscription(Inscription inscription) {
        String query = "INSERT INTO inscriptions (etudiant_id, cours_id, date_inscription) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, inscription.getEtudiantId());
            stmt.setInt(2, inscription.getCoursId());
            stmt.setDate(3, inscription.getDateInscription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'inscription : " + e.getMessage());
        }
    }

    // Supprimer une inscription par son ID
    public void deleteInscription(int id) {
        String query = "DELETE FROM inscriptions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'inscription : " + e.getMessage());
        }
    }

    // Récupérer les inscriptions d'un étudiant par son ID
    public List<Inscription> getInscriptionsByStudent(int etudiantId) {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM inscriptions WHERE etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inscriptions.add(new Inscription(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getInt("cours_id"),
                        rs.getDate("date_inscription")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des inscriptions de l'étudiant : " + e.getMessage());
        }
        return inscriptions;
    }
}
