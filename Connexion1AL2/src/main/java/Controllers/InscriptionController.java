package Controllers;

import Services.InscriptionService;
import Entites.Inscription;
import java.util.List;

public class InscriptionController {

    private InscriptionService inscriptionService;

    public InscriptionController() {
        this.inscriptionService = new InscriptionService();
    }

    // Récupérer toutes les inscriptions d'un étudiant
    public List<Inscription> getInscriptionsByStudent(int etudiantId) {
        return inscriptionService.getInscriptionsByStudent(etudiantId);
    }
}
