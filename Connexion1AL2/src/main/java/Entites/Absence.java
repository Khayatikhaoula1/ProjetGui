package Entites;

import java.sql.Date;

public class Absence {
    private int id;
    private User etudiant;
    private Cours cours;
    private Date dateAbsence;
    private String motif;

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



    public Date getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(Date dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
    @Override
    public String toString() {
        return "Absence{" +
                "id=" + id +
                ", etudiant=" + etudiant +
                ", cours=" + cours +
                ", dateAbsence=" + dateAbsence +
                ", motif='" + motif + '\'' +
                '}';
    }
}