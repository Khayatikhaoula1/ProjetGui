package Entites;

import java.sql.Date;

public class Note {
    private int id;
    private int etudiantId;
    private int coursId;  // Ajout du coursId
    private double note;
    private Date dateEvaluation;

    // Constructeur modifié pour accepter coursId
    public Note(int id, int etudiantId, int coursId, double note, Date dateEvaluation) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.coursId = coursId;  // Initialisation de coursId
        this.note = note;
        this.dateEvaluation = dateEvaluation;
    }

    // Getters et Setters pour chaque attribut

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

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public Date getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(Date dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }
}