package Entites;

import java.sql.Date;

public class Inscription {
    private int id;
    private User etudiant;
    private Cours cours;
    private Date dateInscription;

    public Inscription(int id, int etudiantId, int coursId, Date dateInscription) {
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(User etudiant) {
        this.etudiant = etudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "id=" + id +
                ", etudiant=" + etudiant +
                ", cours=" + cours +
                ", dateInscription=" + dateInscription +
                '}';
    }
}