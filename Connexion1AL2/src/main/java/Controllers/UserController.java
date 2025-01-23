package Controllers;

import Entites.User;
import Entites.Role;
import Services.ServiceUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final ServiceUser serviceUser;

    public UserController() {
        this.serviceUser = new ServiceUser();
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n--- Menu Gestion des Utilisateurs ---");
            System.out.println("1. Ajouter un utilisateur");
            System.out.println("2. Afficher tous les utilisateurs");
            System.out.println("3. Modifier un utilisateur");
            System.out.println("4. Supprimer un utilisateur");
            System.out.println("5. Rechercher un utilisateur par ID");
            System.out.println("6. Afficher les utilisateurs par rôle");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un entier valide.");
                System.out.print("Votre choix : ");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le saut de ligne

            switch (choix) {
                case 1 -> ajouterUtilisateur(scanner);
                case 2 -> afficherUtilisateurs();
                case 3 -> modifierUtilisateur(scanner);
                case 4 -> supprimerUtilisateur(scanner);
                case 5 -> rechercherUtilisateurParId(scanner);
                case 6 -> afficherUtilisateursParRole(scanner);
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 0);

        scanner.close();
    }

    private void ajouterUtilisateur(Scanner scanner) {
        try {
            System.out.println("\n--- Ajouter un utilisateur ---");
            System.out.print("Nom : ");
            String nom = scanner.nextLine();
            System.out.print("Email : ");
            String email = scanner.nextLine();
            System.out.print("Mot de passe : ");
            String motDePasse = scanner.nextLine();
            System.out.print("ID du rôle : ");
            int roleId = scanner.nextInt();

            Role role = serviceUser.getRoleById(roleId);
            if (role == null) {
                System.out.println("Rôle introuvable. Veuillez entrer un ID de rôle valide.");
                return;
            }

            User user = new User(0, nom, null, email, motDePasse, role);
            serviceUser.ajouter(user);
            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    private void afficherUtilisateurs() {
        try {
            System.out.println("\n--- Liste des utilisateurs ---");
            List<User> users = serviceUser.getAll();
            if (users.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                for (User user : users) {
                    System.out.println(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
    }

    private void modifierUtilisateur(Scanner scanner) {
        try {
            System.out.println("\n--- Modifier un utilisateur ---");
            System.out.print("ID de l'utilisateur à modifier : ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consommer le saut de ligne

            User user = serviceUser.getById(id);
            if (user == null) {
                System.out.println("Utilisateur introuvable.");
                return;
            }

            System.out.print("Nouveau nom (" + user.getNom() + ") : ");
            String nom = scanner.nextLine();
            if (!nom.isEmpty()) user.setNom(nom);

            System.out.print("Nouvel email (" + user.getEmail() + ") : ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) user.setEmail(email);

            System.out.print("Nouveau mot de passe : ");
            String motDePasse = scanner.nextLine();
            if (!motDePasse.isEmpty()) user.setMotDePasse(motDePasse);

            System.out.print("ID du nouveau rôle (" + (user.getRole() != null ? user.getRole().getId() : "Aucun") + ") : ");
            int roleId = scanner.nextInt();

            Role role = serviceUser.getRoleById(roleId);
            if (role == null) {
                System.out.println("Rôle introuvable.");
                return;
            }

            user.setRole(role);
            serviceUser.update(user);
            System.out.println("Utilisateur mis à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'utilisateur : " + e.getMessage());
        }
    }

    private void supprimerUtilisateur(Scanner scanner) {
        try {
            System.out.println("\n--- Supprimer un utilisateur ---");
            System.out.print("ID de l'utilisateur à supprimer : ");
            int id = scanner.nextInt();

            User user = serviceUser.getById(id);
            if (user == null) {
                System.out.println("Utilisateur introuvable.");
                return;
            }

            serviceUser.supprimer(user);
            System.out.println("Utilisateur supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    private void rechercherUtilisateurParId(Scanner scanner) {
        try {
            System.out.println("\n--- Rechercher un utilisateur par ID ---");
            System.out.print("ID de l'utilisateur : ");
            int id = scanner.nextInt();

            User user = serviceUser.getById(id);
            if (user == null) {
                System.out.println("Utilisateur introuvable.");
            } else {
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
        }
    }

    private void afficherUtilisateursParRole(Scanner scanner) {
        try {
            System.out.println("\n--- Afficher les utilisateurs par rôle ---");
            System.out.print("Nom du rôle : ");
            String roleName = scanner.nextLine();

            List<User> users = serviceUser.getUsersByRole(roleName);
            if (users.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé pour ce rôle.");
            } else {
                for (User user : users) {
                    System.out.println(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
    }
}
