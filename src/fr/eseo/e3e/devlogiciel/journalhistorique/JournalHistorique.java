package fr.eseo.e3e.devlogiciel.journalhistorique;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente le journal d'historique contenant toutes les traces du système.
 */
public class JournalHistorique {

    /**
     * Liste des entrées contenues dans l'historique.
     */
    private List<EntreeHistorique> entrees;

    /**
     * Crée un nouveau journal historique vide.
     */
    public JournalHistorique() {
        this.entrees = new ArrayList<>();
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

    public void exporterEnTexte(String cheminFichier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (EntreeHistorique entree : entrees) {
                writer.write(entree.getHorodatage() + " - " + entree.getAction());
                writer.newLine();
            }
            System.out.println("Journal exporté en texte !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}