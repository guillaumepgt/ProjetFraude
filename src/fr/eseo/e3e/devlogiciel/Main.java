package fr.eseo.e3e.devlogiciel;

import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;
import fr.eseo.e3e.devlogiciel.ui.ConsoleUI;

public class Main {

    private static final String FICHIER_DATA = "sauvegarde_systeme.dat";
    private static final String CHEMIN_LOGS = "historique_fraudes.txt";

    public static void main(String[] args) {
        SystemeGestion systeme = SystemeGestion.charger(FICHIER_DATA);

        if(systeme.getFormulaires().isEmpty()){
            systeme.initialiserJeuDeDonnees();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            systeme.sauvegarder(FICHIER_DATA);
            systeme.getJournal().exporterEnTexte(CHEMIN_LOGS);
        }));

        ConsoleUI interfaceConsole = new ConsoleUI(systeme);
        interfaceConsole.demarrer();
    }
}