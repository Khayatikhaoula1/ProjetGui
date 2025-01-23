package Entites;

import java.sql.Date;

public class Absence {
    private int id;
    private int etudiantId;
    private Date date;
    private String motif;

    // Constructeur pour Absence
    public Absence(int id, int etudiantId, Date date, String motif) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.date = date;
        this.motif = motif;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
