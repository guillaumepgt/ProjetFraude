package fr.eseo.e3e.devlogiciel.student;

import fr.eseo.e3e.devlogiciel.sms.Subject;
import fr.eseo.e3e.devlogiciel.sms.YearGroup;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a student enrolled in the school.
 * Contains personal information and identification details.
 */
public class Student {

    public static final DecimalFormat DF = new DecimalFormat("0.##");
    /**
     * Student's first name
     */
    private String forename;
    /**
     * Student's last name
     */
    private String surname;
    /**
     * Student's date of birth
     */
    private LocalDate dateOfBirth;
    /**
     * Unique student ID
     */
    private int id;
    /**
     * Generated username for the student
     */
    private String username;
    /**
     * Student's year group/class
     */
    private YearGroup yearGroup;

    private HashMap<Subject, ArrayList<Double>> grades;

    /**
     * Creates a new Student with basic information and default year group.
     *
     * @param forename    The student's first name
     * @param surname     The student's last name
     * @param dateOfBirth The student's date of birth
     */
    public Student(String forename, String surname, LocalDate dateOfBirth) {
        this.setForename(forename);
        this.setSurname(surname);
        this.setDateOfBirth(dateOfBirth);
        yearGroup = YearGroup.E3e;
        this.grades = new HashMap<>();
        this.setUsername("");
        this.setId(0);
    }

    /**
     * Creates a new Student with basic information and default year group using individual date components.
     *
     * @param forename The student's first name
     * @param surname  The student's last name
     * @param year     Birth year
     * @param month    Birth month
     * @param day      Birth day
     */
    public Student(String forename, String surname, int year, int month, int day) {
        this(forename, surname, LocalDate.of(year, month, day));
    }

    /**
     * Creates a new Student with complete information including ID and username.
     *
     * @param forename    The student's first name
     * @param surname     The student's last name
     * @param dateOfBirth The student's date of birth
     * @param id          The student's unique ID
     * @param username    The student's username
     */
    public Student(String forename, String surname, LocalDate dateOfBirth, int id, String username) {
        this(forename, surname, dateOfBirth);
        this.setId(id);
        this.setUsername(username);
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
     * Gets the student's date of birth.
     *
     * @return Student's date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the student's date of birth.
     *
     * @param dateOfBirth New date of birth to set
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    /**
     * Gets the student's username.
     *
     * @return Student's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the student's username.
     *
     * @param username New username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the student's year group.
     *
     * @return The student's current year group
     */
    public YearGroup getYearGroup() {
        return yearGroup;
    }

    /**
     * Sets the student's year group.
     *
     * @param yearGroup The new year group to set
     */
    public void setYearGroup(YearGroup yearGroup) {
        this.yearGroup = yearGroup;
    }

    /**
     * Returns a formatted string containing the student's information.
     *
     * @return Formatted string with student details
     */
    public String displayInformation() {
        String sb = this.getForename() +
                " " +
                this.getSurname() +
                " (" +
                this.getId() +
                ") : " +
                this.getYearGroup().getDescription() +
                "\n\t" +
                // new line and a tab
                "Born : " +
                this.getDateOfBirth().toString() +
                "\n\t" +
                "Username : " +
                this.getUsername() +
                "\n\tSubjects:";
        for(Subject subject : grades.keySet()) {
                sb = sb + "\n\t\t";
            sb = sb + subject.getTitle();
            for(double note : grades.get(subject)) {
                sb = sb + " ";
                sb = sb + DF.format(note);
            }


        }
        return sb;
    }

    /**
     * Adds a subject to the group of subjects that the student follows.
     * After checking that the subject does not yet exist as a key in the HashMap of Subjects, adds the Subject as a Key, creating and empty AddayList of doubles as the value.
     * @param subject the subject to be added to the students followed subjects
     */
    public void addSubject(Subject subject) {
        if (!this.grades.containsKey(subject)) {
            this.grades.put(subject, new ArrayList<>());
        }
    }

    /**
     * Returns the ensemble of the Subjects and their individual grades
     * @return a HashMap contain all the Subjects and their grades
     */
    public HashMap<Subject, ArrayList<Double>> getGrades() {
        return grades;
    }

    /**
     * Returns the ArrayList of grades for the given subject
     * @param subject the Subject that grades are required for
     * @return an ArrayList containing all the grades, if the subject is not followed, returns null
     */
    public ArrayList<Double> getGradesFor(Subject subject) {
        return grades.get(subject);
    }

    /**
     * If the student is enrolled in the given Subject, add a note to this subject for the student.
     * @param subject The Subject studied
     * @param note The note for an evaluation in the subject
     */
    public void addNoteFor(Subject subject, double note){
        if(this.grades.containsKey(subject)) {
           this.grades.get(subject).add(note);
        }
    }
}

