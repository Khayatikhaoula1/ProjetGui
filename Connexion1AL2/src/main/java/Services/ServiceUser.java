package Services;

import Entites.User;
import Entites.Role;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {

    private Connection con = DataSource.getInstance().getConn();

    public ServiceUser() {
        // Initialisation automatique avec la connexion de la DataSource
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO `User` (nom, email, mot_de_passe, role_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, user.getNom());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getMotDePasse());
            pre.setInt(4, user.getRole().getId()); // Assurez-vous que le rôle contient un ID valide
            pre.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
        }
    }

    @Override
    public void supprimer(User user) throws SQLException {
        String req = "DELETE FROM `User` WHERE id = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, user.getId());
            int rowsAffected = pre.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String req = "UPDATE `User` SET nom = ?, email = ?, mot_de_passe = ?, role_id = ? WHERE id = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, user.getNom());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getMotDePasse());
            pre.setInt(4, user.getRole().getId());
            pre.setInt(5, user.getId());
            int rowsAffected = pre.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur mis à jour avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        String req = "SELECT u.*, r.nom AS role_nom FROM `User` u LEFT JOIN roles r ON u.role_id = r.id WHERE u.id = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));

                Role role = new Role(rs.getInt("id"), rs.getString("nom"));
                role.setId(rs.getInt("role_id"));
                role.setNom(rs.getString("role_nom"));
                user.setRole(role);

                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        String req = "SELECT u.*, r.nom AS role_nom FROM `User` u LEFT JOIN roles r ON u.role_id = r.id";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));

                Role role = new Role(rs.getInt("id"), rs.getString("nom"));
                role.setId(rs.getInt("role_id"));
                role.setNom(rs.getString("role_nom"));
                user.setRole(role);

                list.add(user);
            }
        }
        return list;
    }

    public List<User> getUsersByRole(String roleName) throws SQLException {
        List<User> list = new ArrayList<>();
        String req = "SELECT u.*, r.nom AS role_nom FROM `User` u LEFT JOIN roles r ON u.role_id = r.id WHERE r.nom = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, roleName);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));

                Role role = new Role(rs.getInt("id"), rs.getString("nom"));
                role.setId(rs.getInt("role_id"));
                role.setNom(rs.getString("role_nom"));
                user.setRole(role);

                list.add(user);
            }
        }
        return list;
    }
}
