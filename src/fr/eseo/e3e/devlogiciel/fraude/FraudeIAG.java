package fr.eseo.e3e.devlogiciel.fraude;

import java.time.LocalDate;

/**
 * Représente une fraude utilisant une Intelligence Artificielle Générative (IAG) lors d'une épreuve.
 * Il s'agit d'une classe abstraite qui sert de base pour des fraudes IAG plus spécifiques.
 */
public abstract class FraudeIAG extends Fraude {

    /**
     * Nom du service d'IAG utilisé (par exemple : ChatGPT, Gemini, Claude, etc.)
     */
    private String nomService;

    /**
     * Crée une nouvelle fraude IAG avec des informations par défaut.
     */
    public FraudeIAG() {
        super();
        this.nomService = "";
    }

    /**
     * Crée une nouvelle fraude IAG avec toutes les informations spécifiées.
     *
     * @param dateReleve   La date de la fraude
     * @param description  La description de la fraude
     * @param contenu      Le contenu de la fraude
     * @param nomService   Le nom du service d'IAG utilisé pour tricher
     */
    public FraudeIAG(LocalDate dateReleve, String description, String contenu, String nomService) {
        super(dateReleve, description, contenu);
        this.setNomService(nomService);
    }

    /**
     * Récupère le nom du service d'IAG utilisé.
     *
     * @return Le nom du service d'IAG
     */
    public String getNomService() {
        return nomService;
    }

    /**
     * Définit le nom du service d'IAG utilisé.
     *
     * @param nomService Le nouveau nom du service à définir
     */
    public void setNomService(String nomService) {
        this.nomService = nomService;
    }
}