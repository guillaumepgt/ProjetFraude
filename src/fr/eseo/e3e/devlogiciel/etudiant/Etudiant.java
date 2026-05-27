package fr.eseo.e3e.devlogiciel.etudiant;

/**
 * Représente un étudiant inscrit dans l'école.
 * Contient les informations personnelles et les détails d'identification.
 */
public class Etudiant {

    /**
     * Cursus de l'étudiant
     */
    public enum Cursus {
        /** Année E1 */ E1,
        /** Année E2 */ E2,
        /** Année E3e */ E3e,
        /** Année E3a */ E3a,
        /** Année E4 */ E4,
        /** Année E5 */ E5
    }

    /**
     * Nom de l'étudiant
     */
    private String nom;

    /**
     * Prénom de l'étudiant
     */
    private String prenom;

    /**
     * Numéro apprenant unique de l'étudiant
     */
    private String numeroApprenant;

    /**
     * Cursus de l'étudiant
     */
    private Cursus cursus;

    /**
     * Crée un nouvel étudiant avec des informations par défaut.
     */
    public Etudiant() {
        this.nom = "Peloin";
        this.prenom = "Titouan";
        this.numeroApprenant = "1";
        this.cursus = Cursus.E3e;
    }

    /**
     * Crée un nouvel étudiant avec toutes ses informations.
     *
     * @param nom               Le nom de l'étudiant
     * @param prenom            Le prénom de l'étudiant
     * @param numeroApprenant   Le numéro apprenant unique de l'étudiant
     * @param cursus            Le cursus de l'étudiant
     */
    public Etudiant(String nom, String prenom, String numeroApprenant, Cursus cursus) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setId(numeroApprenant);
        this.setCursus(cursus);
    }

    /**
     * Récupère le nom de l'étudiant.
     *
     * @return Le nom de l'étudiant
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'étudiant.
     *
     * @param nom Le nouveau nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupère le prénom de l'étudiant.
     *
     * @return Le prénom de l'étudiant
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'étudiant.
     *
     * @param prenom Le nouveau prénom à définir
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Récupère le numéro apprenant de l'étudiant.
     *
     * @return Le numéro apprenant unique de l'étudiant
     */
    public String getId() {
        return numeroApprenant;
    }

    /**
     * Définit le numéro apprenant de l'étudiant.
     *
     * @param numeroApprenant Le nouveau numéro apprenant à définir
     */
    public void setId(String numeroApprenant) {
        this.numeroApprenant = numeroApprenant;
    }

    /**
     * Récupère le cursus de l'étudiant.
     *
     * @return Le cursus de l'étudiant
     */
    public Cursus getCursus() {
        return this.cursus;
    }

    /**
     * Définit le cursus de l'étudiant.
     *
     * @param cursus Le nouveau cursus à définir
     */
    public void setCursus(Cursus cursus) {
        this.cursus = cursus;
    }
}