
package Services;

import Entites.Absence;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceService {

    private Connection connection;

    // Utilisation du DataSource pour la gestion de la connexion
    public AbsenceService() {
        this.connection = DataSource.getInstance().getConn();
    }

    // Récupérer une absence par son ID
    public Absence getAbsenceById(int id) {
        String query = "SELECT * FROM absences WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int etudiantId = rs.getObject("etudiant_id") != null ? rs.getInt("etudiant_id") : 0;
                return new Absence(
                        rs.getInt("id"),
                        etudiantId,
                        rs.getDate("date"),
                        rs.getString("motif")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'absence : " + e.getMessage());
        }
        return null;
    }

    // Récupérer toutes les absences
    public List<Absence> getAllAbsences() {
        List<Absence> absencesList = new ArrayList<>();
        String query = "SELECT * FROM absences";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int etudiantId = rs.getObject("etudiant_id") != null ? rs.getInt("etudiant_id") : 0;
                absencesList.add(new Absence(
                        rs.getInt("id"),
                        etudiantId,
                        rs.getDate("date"),
                        rs.getString("motif")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des absences : " + e.getMessage());
        }
        return absencesList;
    }

    // Ajouter une absence
    public void addAbsence(Absence absence) {
        String query = "INSERT INTO absences (etudiant_id, date, motif) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, absence.getEtudiantId());
            stmt.setDate(2, absence.getDate()); // Passe l'objet Date directement
            stmt.setString(3, absence.getMotif());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'absence : " + e.getMessage());
        }
    }

    // Supprimer une absence par son ID
    public void deleteAbsence(int id) {
        String query = "DELETE FROM absences WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'absence : " + e.getMessage());
        }
    }

    // Récupérer les absences d'un étudiant par son ID
    public List<Absence> getAbsencesByStudent(int etudiantId) {
        List<Absence> absences = new ArrayList<>();
        String query = "SELECT * FROM absences WHERE etudiant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, etudiantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                absences.add(new Absence(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getDate("date"),
                        rs.getString("motif")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des absences de l'étudiant : " + e.getMessage());
        }
        return absences;
    }
} 