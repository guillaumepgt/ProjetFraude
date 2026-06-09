package fr.eseo.e3e.devlogiciel.fraude;

import fr.eseo.e3e.devlogiciel.utils.Logger;

import java.time.LocalDate;

/**
 * Représente une fraude utilisant une calculatrice.
 */
public class FraudeCalculatrice extends Fraude{

    /**
     * Marque de la calculatrice
     */
    private String marque;

    /**
     * Programme dans la calculatrice
     */
    private String programme;

    /**
     * Crée une nouvelle fraude avec une calculatrice avec des informations par défaut.
     */
    public FraudeCalculatrice() {
        super();
        Logger.info("Création d'une Fraude avec une Calculatrice vide (constructeur par défaut).");
    }

    /**
     * Crée une nouvelle fraude avec une calculatrice avec toutes ces informations.
     *
     * @param dateReleve               La date de la fraude
     * @param description            La description de la fraude
     * @param contenu               Le contenu de la fraude
     * @param marque               La marque de la calculatrice
     * @param programme            Le programme dans la calculatrice
     */
    public FraudeCalculatrice(LocalDate dateReleve, String description, String contenu, String marque, String programme) {
        super(dateReleve, description, contenu);
        this.setMarque(marque);
        this.setProgramme(programme);
        Logger.succes("Fraude avec une calculatrice initialisé avec succès.");
    }

    /**
     * Récupère la marque de la calculatrice
     *
     * @return La marque de la calculatrice
     */
    public String getMarque() {
        return marque;
    }

    /**
     * Définit la marque de la calculatrice.
     *
     * @param marque La nouvelle marque à définir
     */
    public void setMarque(String marque) {
        this.marque = marque;
    }

    /**
     * Récupère le programme de la calculatrice
     *
     * @return Le programme de la calculatrice
     */
    public String getProgramme() {
        return programme;
    }

    /**
     * Définit le programme de la calculatrice.
     *
     * @param programme Le nouveau programme à définir
     */
    public void setProgramme(String programme) {
        this.programme = programme;
    }
}
