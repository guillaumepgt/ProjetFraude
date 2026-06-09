package fr.eseo.e3e.devlogiciel;

import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;
import fr.eseo.e3e.devlogiciel.ui.ConsoleUI;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.FraudePapier;
import fr.eseo.e3e.devlogiciel.fraude.FraudeCalculatrice;
import fr.eseo.e3e.devlogiciel.fraude.FraudeIAGConnectee;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    private static final String FICHIER_DATA = "sauvegarde_systeme.dat";
    private static final String CHEMIN_LOGS = "historique_fraudes.txt";

    public static void main(String[] args) {
        SystemeGestion systeme = SystemeGestion.charger(FICHIER_DATA);

        if (systeme.getFormulaires().isEmpty()) {
            initialiserDonneesTest(systeme);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            systeme.sauvegarder(FICHIER_DATA);
            systeme.getJournal().exporterEnTexte(CHEMIN_LOGS);
        }));

        ConsoleUI interfaceConsole = new ConsoleUI(systeme);
        interfaceConsole.demarrer();
    }

    private static void initialiserDonneesTest(SystemeGestion systeme) {
        Etudiant e1 = new Etudiant("Durand", "Jean", "E001", Etudiant.Cursus.E1);
        Etudiant e2 = new Etudiant("Lefebvre", "Marie", "E002", Etudiant.Cursus.E2);
        Etudiant e3 = new Etudiant("Moreau", "Pierre", "E003", Etudiant.Cursus.E3e);
        Etudiant e4 = new Etudiant("Petit", "Sophie", "E004", Etudiant.Cursus.E4);

        Epreuve ep1 = new Epreuve("MA101", LocalDate.now(), LocalTime.of(9, 0), 90, Epreuve.Modalite.EXAMEN_ECRIT);
        Epreuve ep2 = new Epreuve("CS202", LocalDate.now(), LocalTime.of(14, 0), 120, Epreuve.Modalite.SUR_ORDINATEUR);

        Formulaire f1 = new Formulaire();
        f1.setEpreuve(ep1);
        f1.ajouterEtudiant(e1);
        f1.ajouterFraude(new FraudePapier(LocalDate.now(), "Anti-sèche", "Contenu maths", "10x5cm", true));
        systeme.enregistrerFormulaire(f1);

        Formulaire f2 = new Formulaire();
        f2.setEpreuve(ep2);
        f2.ajouterEtudiant(e2);
        f2.ajouterEtudiant(e3);
        f2.ajouterFraude(new FraudeCalculatrice(LocalDate.now(), "Programme interdit", "Script Python", "Casio Graph 35", "Solver"));
        f2.ajouterFraude(new FraudeIAGConnectee(LocalDate.now(), "Utilisation IA", "Prompt: Solve this", "ChatGPT", "192.168.1.50"));
        systeme.enregistrerFormulaire(f2);

        Formulaire f3 = new Formulaire();
        f3.setEpreuve(ep1);
        f3.ajouterEtudiant(e4);
        f3.ajouterFraude(new FraudeIAGConnectee(LocalDate.now(), "IA Offline", "Texte généré", "LocalLLM", "127.0.0.1"));
        systeme.enregistrerFormulaire(f3);
    }
}
