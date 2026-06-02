package fr.eseo.e3e.devlogiciel;

import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;
import fr.eseo.e3e.devlogiciel.ui.ConsoleUI;

public class Main {

    private static final String FICHIER_DATA = "sauvegarde_systeme.dat";
    private static final String CHEMIN_LOGS = "historique_fraudes.txt";

    public static void main(String[] args) {
        SystemeGestion systeme = SystemeGestion.charger(FICHIER_DATA);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[Fermeture] Enregistrement des données sur l'ordinateur...");
            systeme.sauvegarder(FICHIER_DATA);
            systeme.getJournal().exporterEnTexte(CHEMIN_LOGS);
            System.out.println("[Fermeture] Fichiers mis à jour avec succès.");
        }));

        ConsoleUI interfaceConsole = new ConsoleUI(systeme);
        interfaceConsole.demarrer();
    }
}