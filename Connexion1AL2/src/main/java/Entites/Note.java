package Entites;

import java.sql.Date;

public class Note {
    private int id;
    private User etudiant;
    private Cours cours;
    private double note;
    private Date dateNote;

    public Note(int id, int etudiantId, int coursId, double note, Date dateNote) {
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

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public Date getDateNote() {
        return dateNote;
    }

    public void setDateNote(Date dateNote) {
        this.dateNote = dateNote;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", etudiant=" + etudiant +
                ", cours=" + cours +
                ", note=" + note +
                ", dateNote=" + dateNote +
                '}';
    }
}