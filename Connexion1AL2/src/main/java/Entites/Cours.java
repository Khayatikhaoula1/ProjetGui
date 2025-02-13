package Entites;

public class Cours {
    private int id;
    private String nom;
    private String description;
    private int professeurId;
    private String professeurNom; // Nouveau champ

    // Constructeurs
    public Cours(int id, String nom, String description, int professeurId , String professeurNom) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.professeurId = professeurId;
        this.professeurNom = professeurNom;
    }

    // Getters et Setters
    public String getProfesseurNom() {
        return professeurNom;
    }

    public void setProfesseurNom(String professeurNom) {
        this.professeurNom = professeurNom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProfesseurId() {
        return professeurId;
    }

    public void setProfesseurId(int professeurId) {
        this.professeurId = professeurId;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", professeurId=" + professeurId +
                ", professeurNom='" + professeurNom + '\'' +
                '}';
    }
}