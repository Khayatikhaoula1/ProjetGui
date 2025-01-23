package Controllers;

import Services.UserService;
import Entites.User;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();  // Assure-toi d'utiliser le bon constructeur ici
    }

    // Méthode pour créer un utilisateur
    public boolean createUser(String username, String password, String role) {
        try {
            return userService.createUser(username, password, role);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'utilisateur: " + e.getMessage());
            return false;
        }
    }

    // Méthode pour obtenir tous les utilisateurs
    public List<User> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
            return null;
        }
    }

    // Méthode pour obtenir un utilisateur par son ID
    public User getUserById(int userId) {
        try {
            return userService.getUserById(userId);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
            return null;
        }
    }

    // Méthode pour supprimer un utilisateur
    public boolean deleteUser(int userId) {
        try {
            return userService.deleteUser(userId);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
            return false;
        }
    }
}
