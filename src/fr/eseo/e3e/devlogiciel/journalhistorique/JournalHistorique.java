package fr.eseo.e3e.devlogiciel.journalhistorique;

import fr.eseo.e3e.devlogiciel.utils.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente le journal d'historique contenant toutes les traces du système.
 */
public class JournalHistorique implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Liste des entrées contenues dans l'historique.
     */
    private List<EntreeHistorique> entrees;

    /**
     * Crée un nouveau journal historique vide.
     */
    public JournalHistorique() {
        this.entrees = new ArrayList<>();
        Logger.info("Création d'un journal d'historique vide (constructeur par défaut).");
    }

    /**
     * Constructeur initialisant le journal d'historique
     * @param entrees La Liste d'entrées existante à charger
     */
    public JournalHistorique(List<EntreeHistorique> entrees){
        this.entrees=(entrees != null)?entrees:new ArrayList<>();
    }

    /**
     * Crée une nouvelle entrée d'historique et l'ajoute au journal.
     *
     * @param description La description de l'action à enregistrer
     */
    public void ajouterEntree(String description) {
        EntreeHistorique nouvelleEntree = new EntreeHistorique(description);
        this.entrees.add(nouvelleEntree);
    }

    /**
     * Récupère la liste de toutes les entrées de l'historique.
     *
     * @return La liste des entrées
     */
    public List<EntreeHistorique> getEntrees() {
        return entrees;
    }

    /**
     * Définit la liste des entrées de l'historique.
     *
     * @param entrees La nouvelle liste d'entrées
     */
    public void setEntrees(List<EntreeHistorique> entrees) {
        this.entrees = entrees;
    }

    /**
     * Exporte l'ensemble des entrées du journal dans un fichier texte physique.
     * @param cheminFichier Le chemin ou le nom du fichier de destination
     */
    public void exporterEnTexte(String cheminFichier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (EntreeHistorique entree : entrees) {
                writer.write(entree.getHorodatage() + " - " + entree.getAction());
                writer.newLine();
            }
        } catch (IOException e) {
            Logger.erreur("Impossible d'exporter le journal historique : " + e.getMessage());
        }
    }
}