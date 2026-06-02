package fr.eseo.e3e.devlogiciel.epreuve;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Représente une épreuve universitaire soumise à une surveillance de fraudes.
 * Cette classe définit les caractéristiques d'une épreuve.
 */
public class Epreuve {
    /**
     * Modalite
     * Enumération du type d'épreuve
     */
    public enum Modalite {
        EXAMEN_ECRIT, /**Examen au format papier*/
        ORAL, /** Epreuve Oral devant un jury*/
        QCM, /** Questionnaire à choix multiples*/
        SUR_ORDINATEUR, /**Epreuve sur machine*/
        PROJET, /**Rendu ou soutenance de projet*/
        TP /** Evaluation de travaux pratiques*/
    }

    /**
     * Code ECUE de l'épreuve
     */
    private String codeECUE;

    /**
     * Date de l'épreuve
     */
    private LocalDate date;

    /**
     * Heure de l'épreuve
     */
    private LocalTime heurePassage;
    /**
     * Durée de l'épreuve
     */
    private int duree;
    /**
     * Modalité de l'épreuve
     */
    private Modalite modalite;

    /**
     * Constructeur par défaut
     * Initialise une instance d'épreuve sans valeur par défaut.
     */
    public Epreuve() {
    }

    /**
     * Constructeur avec initialisation complète
     * @param codeECUE Le code identifiant de l'Unité d'Enseignement.
     * @param date La date de l'épreuve.
     * @param heurePassage L'heure de l'épreuve
     * @param duree La durée de l'épreuve (en minutes)
     * @param modalite La modalité d'évaluation de l'épreuve
     */
    public Epreuve(String codeECUE, LocalDate date, LocalTime heurePassage, int duree, Modalite modalite) {
        this.codeECUE = codeECUE;
        this.date = date;
        this.heurePassage = heurePassage;
        this.duree = duree;
        this.modalite = modalite;
    }

    /**
     * Récupère le code ECUE de l'épreuve
     * @return Le code de l'épreuve sous forme de chaîne de caractères.
     */
    public String getCodeECUE() {
        return codeECUE;
    }

    /**
     * Modifie le code ECUE de l'épreuve
     * @param codeECUE Le nouveau code de l'épreuve.
     */
    public void setCodeECUE(String codeECUE) {
        this.codeECUE = codeECUE;
    }

    /**
     * Récupère la date de l'épreuve
     * @return Le jour de l'épreuve (objet LocalDate).
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Modifie la date de l'épreuve.
     * @param date La nouvelle date de l'épreuve.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Récupère l'heure de passage de l'épreuve
     * @return L'heure de début (objet LocalTime)
     */
    public LocalTime getHeurePassage() {
        return heurePassage;
    }

    /**
     * Modifie l'heure de passage de l'épreuve.
     * @param heurePassage La nouvelle heure de début.
     */
    public void setHeurePassage(LocalTime heurePassage) {
        this.heurePassage = heurePassage;
    }

    /**
     * Récupère la durée de l'épreuve
     * @return La durée de l'épreuve (en minutes).
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Modifie la durée de l'épreuve.
     * @param duree La nouvelle durée (en minutes).
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * Récupère la modalité de l'épreuve
     * @return La modalité d'évaluation.
     */
    public Modalite getModalite() {
        return modalite;
    }

    /**
     * Modifie la modalité de l'épreuve
     * @param modalite La nouvelle modalité d'évaluation.
     */
    public void setModalite(Modalite modalite) {
        this.modalite = modalite;
    }
}