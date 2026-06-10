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

/**
 * Classe principale de l'application de gestion des fraudes.
 * Elle contient le point d'entrée du programme et s'occupe de l'initialisation
 * du système, de la sauvegarde des données à la fermeture, et du lancement de
 * l'interface utilisateur en console.
 */
public class Main {
    /**
     * Nom du fichier utilisé pour la sauvegarde et le chargement de l'état du système.
     */
    private static final String FICHIER_DATA = "sauvegarde_systeme.dat";
    
    /**
     * Chemin vers le fichier texte utilisé pour l'exportation du journal historique des fraudes.
     */
    private static final String CHEMIN_LOGS = "historique_fraudes.txt";

    /**
     * Point d'entrée principal de l'application.
     * Il charge les données du système depuis le fichier de sauvegarde. Si le système
     * est vide, il initialise des données de test. Il enregistre également un mécanisme
     * pour sauvegarder les données et exporter les logs lors de la fermeture
     * de l'application. Enfin, il démarre l'interface console.
     * 
     * @param args les arguments de la ligne de commande (non utilisés)
     */
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

    /**
     * Initialise le système avec un ensemble de données de test (étudiants, épreuves, 
     * formulaires, et fraudes). Utilisé uniquement si aucune donnée préalable n'est 
     * trouvée lors du chargement.
     * 
     * @param systeme le système de gestion dans lequel les données de test doivent être ajoutées
     */
    private static void initialiserDonneesTest(SystemeGestion systeme) {
        Etudiant etudiant1 = new Etudiant("Durand", "Jean", "E001", Etudiant.Cursus.E1);
        Etudiant etudiant2 = new Etudiant("Lefebvre", "Marie", "E002", Etudiant.Cursus.E2);
        Etudiant etudiant3 = new Etudiant("Moreau", "Pierre", "E003", Etudiant.Cursus.E3e);
        Etudiant etudiant4 = new Etudiant("Petit", "Sophie", "E004", Etudiant.Cursus.E4);

        Epreuve epreuve1 = new Epreuve("MA101", LocalDate.now(), LocalTime.of(9, 0), 90, Epreuve.Modalite.EXAMEN_ECRIT);
        Epreuve epreuve2 = new Epreuve("CS202", LocalDate.now(), LocalTime.of(14, 0), 120, Epreuve.Modalite.SUR_ORDINATEUR);

        Formulaire f1 = new Formulaire();
        f1.setEpreuve(epreuve1);
        f1.ajouterEtudiant(etudiant1);
        f1.ajouterFraude(new FraudePapier(LocalDate.now(), "Anti-sèche", "Contenu maths", "10x5cm", true));
        systeme.enregistrerFormulaire(f1);

        Formulaire f2 = new Formulaire();
        f2.setEpreuve(epreuve2);
        f2.ajouterEtudiant(etudiant2);
        f2.ajouterEtudiant(etudiant3);
        f2.ajouterFraude(new FraudeCalculatrice(LocalDate.now(), "Programme interdit", "Script Python", "Casio Graph 35", "Solver"));
        f2.ajouterFraude(new FraudeIAGConnectee(LocalDate.now(), "Utilisation IA", "Prompt: Solve this", "ChatGPT", "192.168.1.50"));
        systeme.enregistrerFormulaire(f2);

        Formulaire f3 = new Formulaire();
        f3.setEpreuve(epreuve1);
        f3.ajouterEtudiant(etudiant4);
        f3.ajouterFraude(new FraudeIAGConnectee(LocalDate.now(), "IA Offline", "Texte généré", "LocalLLM", "127.0.0.1"));
        systeme.enregistrerFormulaire(f3);
    }
}
