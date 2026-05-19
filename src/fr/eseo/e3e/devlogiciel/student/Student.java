package fr.eseo.e3e.devlogiciel.student;

/**
 * Represents a student enrolled in the school.
 * Contains personal information and identification details.
 */
public class Student {

    /**
     * Student's first name
     */
    private String forename;
    /**
     * Student's last name
     */
    private String surname;
    /**
     * Unique student ID
     */
    private int id;

    /**
     * Creates a new Student with complete information including ID and username.
     *
     * @param forename    The student's first name
     * @param surname     The student's last name
     * @param id          The student's unique ID
     */
    public Student(String forename, String surname, int id) {
        this.setForename(forename);
        this.setSurname(surname);
        this.setId(id);
    }

    /**
     * Gets the student's first name.
     *
     * @return Student's forename
     */
    public String getForename() {
        return forename;
    }

    /**
     * Sets the student's first name.
     *
     * @param forename New forename to set
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Gets the student's last name.
     *
     * @return Student's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the student's last name.
     *
     * @param surname New surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the student's ID.
     *
     * @return Student's unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the student's ID.
     *
     * @param id New ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
}

