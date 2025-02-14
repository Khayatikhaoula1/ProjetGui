package Services;

import Entites.Examen;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamenService {

    private Connection connection;

    public ExamenService() {
        this.connection = DataSource.getInstance().getConn();  // Utilisation de la classe DataSource
    }

    public void addExamen(Examen examen) {
        String query = "INSERT INTO Examens (nom, date, cours_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, examen.getNom());
            stmt.setDate(2, examen.getDateExamen());
            stmt.setInt(3, examen.getCoursId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'examen : " + e.getMessage());
        }
    }

    public Examen getExamenById(int id) {
        String query = "SELECT * FROM Examens WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Examen(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDate("date"),
                        rs.getInt("cours_id")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'examen : " + e.getMessage());
        }
        return null;
    }

    public List<Examen> getAllExamen() {
        List<Examen> examenList = new ArrayList<>();
        String query = "SELECT * FROM Examens";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                examenList.add(new Examen(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDate("date"),
                        rs.getInt("cours_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des examens : " + e.getMessage());
        }
        return examenList;
    }

    public void updateExamen(Examen examen) {
        String query = "UPDATE Examens SET nom = ?, date = ?, cours_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, examen.getNom());
            stmt.setDate(2, examen.getDateExamen());
            stmt.setInt(3, examen.getCoursId());
            stmt.setInt(4, examen.getIdExamen());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'examen : " + e.getMessage());
        }
    }

    public void deleteExamen(int id) {
        String query = "DELETE FROM Examens WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'examen : " + e.getMessage());
        }
    }
}
