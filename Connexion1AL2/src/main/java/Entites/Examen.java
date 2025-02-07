package Entites;

import java.sql.Date;

public class Examen {
    private int idExamen;
    private String nom; // Nom de l'examen
    private Date dateExamen;
    private int coursId; // ID du cours

    // Constructeur
    public Examen(int idExamen, String nom, Date dateExamen, int coursId) {
        this.idExamen = idExamen;
        this.nom = nom;
        this.dateExamen = dateExamen;
        this.coursId = coursId;
    }

    // Getters et Setters
    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateExamen() {
        return dateExamen;
    }

    public void setDateExamen(Date dateExamen) {
        this.dateExamen = dateExamen;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    @Override
    public String toString() {
        return "Examen{idExamen=" + idExamen + ", nom='" + nom + "', dateExamen=" + dateExamen + ", coursId=" + coursId + "}";
    }
}
