package Entites;

import java.sql.Date;

public class Inscription {
    private int id;
    private int etudiantId;
    private int coursId;
    private Date dateInscription;  // Le quatrième paramètre

    // Constructeur actuel
    public Inscription(int id, int etudiantId, int coursId, Date dateInscription) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.coursId = coursId;
        this.dateInscription = dateInscription;
    }


    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }
}
