package Services;

import Entites.Role;
import Entites.User;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private Connection connection;

    public UserService() {
        this.connection = DataSource.getInstance().getConn();
        if (this.connection == null) {
            throw new RuntimeException("⚠️ La connexion à la base de données a échoué !");
        }
    }


    // Créer un utilisateur
    public boolean createUser(String username, String password, Role role, String email) throws SQLException {
        String query = "INSERT INTO users (username, password, role_id, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, role.getId()); // Récupération correcte de l'ID du rôle
            stmt.setString(4, email);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean updateUser(int userId, String username, String password, Role role, String email) throws SQLException {
        String query = "UPDATE users SET username = ?, password = ?, role_id = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, role.getId());
            stmt.setString(4, email);
            stmt.setInt(5, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Utilisez "role_id" pour récupérer l'ID du rôle
                Role role = getRoleById(rs.getInt("role_id"));
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        role,
                        rs.getString("email")
                );
                users.add(user);
            }
        }
        return users;
    }

    // Récupérer un utilisateur par son ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Utilisez "role_id" ici aussi
                Role role = getRoleById(rs.getInt("role_id"));
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        role,
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return null;
    }




    // Méthode pour récupérer un rôle par son ID
    public Role getRoleById(int roleId) {
        String query = "SELECT * FROM roles WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Role(rs.getInt("id"), rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du rôle : " + e.getMessage());
        }
        return null;
    }

    // Supprimer un utilisateur
    public boolean deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }
    // Modifier la méthode getRoleByName (ligne 98)
    public Role getRoleByName(String roleName) {
        // Vérifier si la connexion est valide
        try {
            if (connection.isClosed() || !connection.isValid(2)) {
                System.err.println("La connexion est fermée ou invalide !");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Corriger le nom de la table (user -> roles ?)
        String query = "SELECT * FROM roles WHERE nom = ?"; // <-- Vérifier le nom de la table
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Role(rs.getInt("id"), rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL dans getRoleByName : " + e.getMessage());
        }
        return null;
    }
    public int getTotalUsers() throws SQLException {
        String query = "SELECT COUNT(*) FROM users";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public Map<String, Integer> getUserCountByRole() throws SQLException {
        Map<String, Integer> roleStats = new HashMap<>();
        String query = "SELECT roles.nom, COUNT(users.id) FROM users JOIN roles ON users.role_id = roles.id GROUP BY roles.nom";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                roleStats.put(rs.getString(1), rs.getInt(2));
            }
        }
        return roleStats;
    }
    public User getUserByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Role role = getRoleById(rs.getInt("role_id"));
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        role,
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Si l'utilisateur n'est pas trouvé
    }

    public User authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        getRoleById(rs.getInt("role_id")),
                        rs.getString("email")
                );
            }
        }
        return null;
    }
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        getRoleById(rs.getInt("role_id")),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL dans getUserByUsername : " + e.getMessage());
        }
        return null;
    }



}