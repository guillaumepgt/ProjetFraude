package fr.eseo.e3e.devlogiciel.journalhistorique;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Représente une entrée (une action tracée) dans le journal d'historique du système.
 */
public class EntreeHistorique implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Date et heure à laquelle l'action a été effectuée.
     */
    private LocalDateTime horodatage;

    /**
     * Description de l'action effectuée.
     */
    private String action;

    /**
     * Constructeur par défaut vide
     */
    public EntreeHistorique(){
        this.horodatage= LocalDateTime.now();
        this.action= "Action non spécifiée";

    }
    /**
     * Crée une nouvelle entrée d'historique à l'instant présent.
     *
     * @param action La description de l'action effectuée
     */
    public EntreeHistorique(String action) {
        this.horodatage = LocalDateTime.now();
        this.action = action;
    }

    /**
     * Récupère l'horodatage de l'action.
     *
     * @return L'horodatage
     */
    public LocalDateTime getHorodatage() {
        return horodatage;
    }

    /**
     * Définit l'horodatage de l'action.
     *
     * @param horodatage Le nouvel horodatage à définir
     */
    public void setHorodatage(LocalDateTime horodatage) {
        this.horodatage = horodatage;
    }

    /**
     * Récupère la description de l'action.
     *
     * @return La description de l'action
     */
    public String getAction() {
        return action;
    }

    /**
     * Définit la description de l'action.
     *
     * @param action La nouvelle action à définir
     */
    public void setAction(String action) {
        this.action = action;
    }
}