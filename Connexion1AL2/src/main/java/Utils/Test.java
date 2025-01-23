package Utils;

import Services.UserService;
import Services.NoteService;
import Services.InscriptionService;
import Services.AbsenceService;
import Entites.User;
import Entites.Note;
import Entites.Inscription;
import Entites.Absence;
import java.sql.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Créer une instance de UserService pour tester les utilisateurs
        UserService userService = new UserService();

        try {
            // Ajouter un utilisateur
            User newUser = new User(0, "john_doe", "password123", "Etudiant");
            userService.createUser(newUser.getUsername(), newUser.getPassword(), newUser.getRole());  // Passer les champs de User
            System.out.println("Utilisateur ajouté avec succès !");

            // Récupérer un utilisateur par ID
            User fetchedUser = userService.getUserById(1); // ID à ajuster selon ta base de données
            if (fetchedUser != null) {
                System.out.println("Utilisateur trouvé : " + fetchedUser.getUsername());
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }

            // Tester les notes avec NoteService
            NoteService noteService = new NoteService();
            Note newNote = new Note(0, 1, 1, 85.5, new Date(System.currentTimeMillis())); // (ID étudiant, ID cours, note)
            noteService.addNote(newNote);  // Appel à la méthode addNote() de NoteService
            System.out.println("Note ajoutée avec succès !");

            // Récupérer les notes pour un étudiant
            List<Note> fetchedNotes = noteService.getNotesByStudent(1); // Récupérer les notes pour un étudiant par son ID
            if (!fetchedNotes.isEmpty()) {
                for (Note note : fetchedNotes) {
                    System.out.println("Note de l'étudiant : " + note.getNote());
                }
            } else {
                System.out.println("Aucune note trouvée pour cet étudiant.");
            }

            // Tester les inscriptions avec InscriptionService
            InscriptionService inscriptionService = new InscriptionService();
            Inscription newInscription = new Inscription(0, 1, 1, new Date(System.currentTimeMillis())); // Ajout de la date d'inscription
            inscriptionService.addInscription(newInscription);  // Appel à la méthode addInscription() de InscriptionService
            System.out.println("Inscription ajoutée avec succès !");

            // Récupérer les inscriptions pour un étudiant
            Inscription fetchedInscription = inscriptionService.getInscriptionById(1); // Ajuster selon l'ID de l'étudiant
            if (fetchedInscription != null) {
                System.out.println("Inscriptions de l'étudiant : " + fetchedInscription.getId());
            } else {
                System.out.println("Aucune inscription trouvée pour cet étudiant.");
            }

            // Tester les absences avec AbsenceService
            AbsenceService absenceService = new AbsenceService();
            Absence newAbsence = new Absence(0, 1, new Date(System.currentTimeMillis()), "Maladie"); // (ID étudiant, date d'absence, motif)
            absenceService.addAbsence(newAbsence);  // Appel à la méthode addAbsence() de AbsenceService
            System.out.println("Absence ajoutée avec succès !");

            // Récupérer les absences pour un étudiant
            Absence fetchedAbsence = absenceService.getAbsenceById(1); // Ajuster selon l'ID de l'étudiant
            if (fetchedAbsence != null) {
                System.out.println("Absence de l'étudiant : " + fetchedAbsence.getDate()); // Utilisation de getDate() au lieu de getDateAbsence()
            } else {
                System.out.println("Aucune absence trouvée pour cet étudiant.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue. Vérifiez les logs.");
        }
    }
}
