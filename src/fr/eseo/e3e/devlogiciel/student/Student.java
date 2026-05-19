package fr.eseo.e3e.devlogiciel.student;

/**
 * Represents a student enrolled in the school.
 * Contains personal information and identification details.
 */
public class Student {

    /**
     * Student's first name
     */
    private String nom;
    /**
     * Student's last name
     */
    private String prenom;
    /**
     * Unique student ID
     */
    private String numeroApprenant;


    /** Creates a new Student with autocomplete information.
     */
    public Student() {
        this.nom = "Peloin";
        this.prenom = "Titouan";
        this.numeroApprenant = "1";
    }

    /**
     * Creates a new Student with complete information.
     *
     * @param nom    The student's first name
     * @param prenom     The student's last name
     * @param numeroApprenant   The student's unique ID
     */
    public Student(String nom, String prenom, String numeroApprenant) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setId(numeroApprenant);
    }

    /**
     * Gets the student's first name.
     *
     * @return Student's forename
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets the student's first name.
     *
     * @param nom New forename to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Gets the student's last name.
     *
     * @return Student's surname
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Sets the student's last name.
     *
     * @param prenom New surname to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Gets the student's ID.
     *
     * @return Student's unique ID
     */
    public String getId() {
        return numeroApprenant;
    }

    /**
     * Sets the student's ID.
     *
     * @param numeroApprenant New ID to set
     */
    public void setId(String numeroApprenant) {
        this.numeroApprenant = numeroApprenant;
    }
}

