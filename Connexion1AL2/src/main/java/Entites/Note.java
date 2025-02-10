package Entites;

import java.sql.Date;

public class Note {
    private int id;
    private int etudiantId;
    private int coursId;
    private double note;
    private double noteControle;
    private Date dateEvaluation;
    private String resultat;

    public Note(int id, int etudiantId, int coursId, double note, double noteControle, Date dateEvaluation) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.coursId = coursId;
        this.note = note;
        this.noteControle = noteControle;
        this.dateEvaluation = dateEvaluation;
        setResultat();
    }

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
        setResultat();
    }

    public double getNoteControle() {
        return noteControle;
    }

    public void setNoteControle(double noteControle) {
        this.noteControle = noteControle;
        setResultat();
    }

    public Date getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(Date dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public boolean isReussite() {
        return note >= 10 || noteControle >= 10;
    }

    public String getResultat() {
        return resultat;
    }

    private void setResultat() {
        this.resultat = isReussite() ? "Admis" : "Refus";
    }
}