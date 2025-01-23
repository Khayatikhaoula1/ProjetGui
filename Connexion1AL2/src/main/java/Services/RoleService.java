package Services;

import Entites.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleService {
    private Connection connection;

    public RoleService(Connection connection) {
        this.connection = connection;
    }

    public List<Role> getAllRoles() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM roles";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                roles.add(new Role(rs.getInt("id"), rs.getString("nom")));
            }
        }
        return roles;
    }

    public void createRole(Role role) throws SQLException {
        String query = "INSERT INTO roles (nom) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role.getNom());
            stmt.executeUpdate();
        }
    }

    public void updateRole(Role role) throws SQLException {
        String query = "UPDATE roles SET nom = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role.getNom());
            stmt.setInt(2, role.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteRole(int id) throws SQLException {
        String query = "DELETE FROM roles WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}


