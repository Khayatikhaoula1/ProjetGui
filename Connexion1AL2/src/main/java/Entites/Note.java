package Entites;

import java.util.Date;

public class Note {
    private int id;
    private int etudiantId;
    private int coursId;
    private double note;
    private Date dateEvaluation;
    private Double noteControle;
    private String resultat;
    private String nomEtudiant;

    // Constructeur
    public Note(int id, int etudiantId, int coursId, double note, Date dateEvaluation, Double noteControle, String resultat, String nomEtudiant) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.coursId = coursId;
        this.note = note;
        this.dateEvaluation = dateEvaluation;
        this.noteControle = noteControle;
        this.resultat = resultat;
        this.nomEtudiant = nomEtudiant;
    }

    // Getters et setters
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

    public Double getNoteControle() {
        return noteControle;
    }

    public void setNoteControle(Double noteControle) {
        this.noteControle = noteControle;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", etudiantId=" + etudiantId +
                ", coursId=" + coursId +
                ", note=" + note +
                ", dateEvaluation=" + dateEvaluation +
                ", noteControle=" + noteControle +
                ", resultat='" + resultat + '\'' +
                ", nomEtudiant='" + nomEtudiant + '\'' +
                '}';
    }
}
