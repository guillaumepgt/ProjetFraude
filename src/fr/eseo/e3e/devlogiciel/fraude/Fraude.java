package fr.eseo.e3e.devlogiciel.fraude;

import fr.eseo.e3e.devlogiciel.utils.Logger;

import java.io.Serial;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * Représente une fraude lors d'une épreuve
 * Contient les informations de la fraude et des preuves
 */
public abstract class Fraude implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Date de la fraude
     */
    private LocalDate dateReleve;

    /**
     * Description de la fraude
     */
    private String description;

    /**
     * Contenu de la fraude
     */
    private String contenu;

    /**
     * Crée une nouvelle fraude avec des informations par défaut.
     */
    public Fraude() {
        Logger.info("Création d'une Fraude vide (constructeur par défaut).");
    }

    /**
     * Crée une nouvelle fraude avec toutes ces informations.
     *
     * @param dateReleve               La date de la fraude
     * @param description            Le contenu de la fraude
     * @param contenu               La description de la fraude
     */
    public Fraude(LocalDate dateReleve, String description, String contenu) {
        this.dateReleve = dateReleve;
        this.description = description;
        this.contenu = contenu;
        Logger.succes("Fraude " + this.description + " du " + this.dateReleve + " initialisé avec succès.");
    }
    /**
     * Récupère la date de la fraude
     *
     * @return La date de la fraude
     */
    public LocalDate getDateReleve() {
        return dateReleve;
    }

    /**
     * Définit la date de la fraude.
     *
     * @param dateReleve La nouvelle date à définir
     */
    public void setDateReleve(LocalDate dateReleve) {
        this.dateReleve = dateReleve;
    }

    /**
     * Récupère le contenu de la fraude
     *
     * @return Le contenu de la fraude
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Définit le contenu de la fraude.
     *
     * @param contenu Le contenu à définir
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * Récupère la description de la fraude
     *
     * @return La description de la fraude
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de la fraude.
     *
     * @param description La description à définir
     */
    public void setDescription(String description) {
        this.description = description;
    }
}