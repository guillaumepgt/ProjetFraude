package fr.eseo.e3e.devlogiciel;

import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;
import fr.eseo.e3e.devlogiciel.ui.ConsoleUI;

/**
 * Point d'entrée principal de l'application de gestion des fraudes.
 * Cette classe se charge d'initialiser le système, de gérer la sauvegarde automatique
 * à la fermeture du programme et de lancer l'interface utilisateur.
 */
public class Main {

    /** * Nom du fichier utilisé pour la sauvegarde binaire des données du système.
     */
    private static final String FICHIER_DATA = "sauvegarde_systeme.dat";

    /** * Nom du fichier texte utilisé pour l'exportation du journal d'historique.
     */
    private static final String CHEMIN_LOGS = "historique_fraudes.txt";

    /**
     * Méthode principale exécutée au lancement du programme.
     * Elle restaure les données précédentes, configure une sécurité pour sauvegarder
     * en cas de fermeture inattendue, puis démarre le menu interactif.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans ce projet)
     */
    public static void main(String[] args) {
        SystemeGestion systeme = SystemeGestion.charger(FICHIER_DATA);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            systeme.sauvegarder(FICHIER_DATA);
            systeme.getJournal().exporterEnTexte(CHEMIN_LOGS);
        }));

        ConsoleUI interfaceConsole = new ConsoleUI(systeme);
        interfaceConsole.demarrer();
    }
}