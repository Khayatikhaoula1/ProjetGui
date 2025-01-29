package Controllers;

import Services.CoursService;
import Entites.Cours;
import java.util.List;

public class CoursController {

    private CoursService coursService;

    public CoursController() {
        this.coursService = new CoursService();
    }

    // Récupérer les cours auxquels un étudiant est inscrit
    public List<Cours> getCoursesByStudent(int etudiantId) {
        return coursService.getCoursesByStudent(etudiantId);
    }
}
