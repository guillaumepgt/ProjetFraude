package fr.eseo.e3e.devlogiciel.ui;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.*;
import fr.eseo.e3e.devlogiciel.journalhistorique.EntreeHistorique;
import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;
import fr.eseo.e3e.devlogiciel.utils.FraudeException;
import fr.eseo.e3e.devlogiciel.utils.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Gère l'interface utilisateur en ligne de commande pour l'application de gestion des fraudes.
 * Cette classe fait le lien entre les entrées de l'utilisateur et la logique métier du système.
 */
public class ConsoleUI {

    /**
     * Le système de gestion central contenant la logique métier et les données.
     */
    private final SystemeGestion systeme;

    /**
     * Le scanner utilisé pour lire les entrées clavier de l'utilisateur.
     */
    private final Scanner scanner;

    /**
     * Construit une nouvelle interface console liée à un système de gestion.
     *
     * @param systeme Le système de gestion des fraudes à manipuler.
     */
    public ConsoleUI(SystemeGestion systeme) {
        this.systeme = systeme;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Lance la boucle principale de l'application interactive.
     * Affiche le menu et traite les actions jusqu'à ce que l'utilisateur décide de quitter.
     */
    public void demarrer() {
        boolean quitter = false;

        System.out.println("---------------------------------");
        System.out.println("  APPLI DE GESTION DES FRAUDES");
        System.out.println("---------------------------------");

        while (!quitter) {
            afficherMenuPrincipal();
            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1": saisirNouveauFormulaire(); break;
                case "2": actionSupprimerFormulaire(); break;
                case "3": afficherTousLesFormulaires(); break;
                case "4": actionRechercheCroisee(); break;
                case "5": actionRechercherEtudiant(); break;
                case "6": actionRechercherDossiers(); break;
                case "7": afficherStatistiques(); break;
                case "8": actionAnalyserReseau(); break;
                case "9": afficherJournalHistorique(); break;
                case "10": quitter = true; Logger.info("Fermeture de l'application."); break;
                default: Logger.erreur("Choix invalide, tapez un chiffre entre 1 et 10.");
            }
        }
        scanner.close();
    }

    /**
     * Affiche les différentes options du menu principal dans la console.
     */
    private void afficherMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Créer un nouveau dossier de fraude");
        System.out.println("2. Supprimer un dossier par son ID");
        System.out.println("3. Afficher la liste des dossiers");
        System.out.println("4. Recherche croisée (Cursus + Type)");
        System.out.println("5. Rechercher un étudiant (Nom ou ID)");
        System.out.println("6. Rechercher des dossiers (par Étudiant ou Épreuve)");
        System.out.println("7. Afficher les statistiques globales");
        System.out.println("8. Analyser le réseau de tricheurs (Graphe)");
        System.out.println("9. Afficher les logs du système");
        System.out.println("10. Quitter");
        System.out.print("Votre choix : ");
    }

    /**
     * Guide l'utilisateur étape par étape pour créer et enregistrer un nouveau dossier de fraude.
     * Demande successivement les informations sur l'épreuve, l'étudiant, et la nature de la fraude.
     */
    private void saisirNouveauFormulaire() {
        System.out.println("\n=== REMPLISSAGE DU FORMULAIRE ===");
        Formulaire formulaire = new Formulaire();

        System.out.println("\n1. INFOS ÉPREUVE");
        System.out.print("Code ECUE : ");
        String code = scanner.nextLine().trim();

        LocalDate dateEpreuve = LocalDate.now();
        System.out.print("Date (AAAA-MM-JJ) [Entree pour aujourd'hui] : ");
        String dateStr = scanner.nextLine().trim();
        if (!dateStr.isEmpty()) {
            try { dateEpreuve = LocalDate.parse(dateStr); }
            catch (DateTimeParseException e) { Logger.avertissement("Format incorrect (AAAA-MM-JJ). Date du jour sélectionnée."); }
        }

        System.out.print("Heure du contrôle (HH:MM) : ");
        LocalTime heure = LocalTime.of(8, 0);
        try { heure = LocalTime.parse(scanner.nextLine().trim()); }
        catch (DateTimeParseException e) { Logger.avertissement("Format incorrect. Heure mise à 08:00."); }

        System.out.print("Durée totale (en minutes) : ");
        int duree = 120;
        try { duree = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { Logger.avertissement("Valeur incorrecte. Durée mise à 120 min."); }

        Epreuve.Modalite modalite = selectionnerModalite();
        formulaire.setEpreuve(new Epreuve(code, dateEpreuve, heure, duree, modalite));

        System.out.println("\n2. INFOS ETUDIANT(S) IMPLIQUE(S)");
        boolean ajouterUnAutre = true;
        while (ajouterUnAutre) {
            System.out.print("Nom : ");
            String nom = scanner.nextLine().trim();
            System.out.print("Prénom : ");
            String prenom = scanner.nextLine().trim();
            System.out.print("Numéro étudiant : ");
            String numApp = scanner.nextLine().trim();
            Cursus cursus = selectionnerCursus();
            formulaire.ajouterEtudiant(new Etudiant(nom, prenom, numApp, cursus));

            System.out.print("Ajouter un complice ? (oui/non) : ");
            ajouterUnAutre = scanner.nextLine().trim().equalsIgnoreCase("oui");
        }

        System.out.println("\n3. DETAILS DE LA FRAUDE");
        System.out.print("Description des faits : ");
        String desc = scanner.nextLine().trim();
        System.out.print("Contenu ou preuves : ");
        String contenu = scanner.nextLine().trim();

        System.out.println("Type : 1.Papier | 2.Calculatrice | 3.IAG Connectée");
        String cat = scanner.nextLine().trim();
        Fraude fraude;
        if (cat.equals("2")) {
            System.out.print("Marque : "); String marque = scanner.nextLine().trim();
            System.out.print("Programme : "); String prog = scanner.nextLine().trim();
            fraude = new FraudeCalculatrice(dateEpreuve, desc, contenu, marque, prog);
        } else if (cat.equals("3")) {
            System.out.print("Service (ex: ChatGPT) : "); String service = scanner.nextLine().trim();
            System.out.print("IP : "); String ip = scanner.nextLine().trim();
            fraude = new FraudeIAGConnectee(dateEpreuve, desc, contenu, service, ip);
        } else {
            System.out.print("Dimensions : "); String dim = scanner.nextLine().trim();
            System.out.print("Plié ? (oui/non) : "); boolean plie = scanner.nextLine().trim().equalsIgnoreCase("oui");
            fraude = new FraudePapier(dateEpreuve, desc, contenu, dim, plie);
        }
        formulaire.ajouterFraude(fraude);
        systeme.enregistrerFormulaire(formulaire);
        System.out.println("[OK] Dossier enregistré ID : " + formulaire.getId());
    }

    /**
     * Demande à l'utilisateur de sélectionner la modalité de l'épreuve parmi une liste.
     *
     * @return La modalité choisie par l'utilisateur. Retourne EXAMEN_ECRIT par défaut.
     */
    private Epreuve.Modalite selectionnerModalite() {
        System.out.println("Modalité : 1.ECRIT | 2.ORAL | 3.QCM | 4.MACHINE | 5.PROJET | 6.TP");
        switch (scanner.nextLine().trim()) {
            case "2": return Epreuve.Modalite.ORAL;
            case "3": return Epreuve.Modalite.QCM;
            case "4": return Epreuve.Modalite.SUR_ORDINATEUR;
            case "5": return Epreuve.Modalite.PROJET;
            case "6": return Epreuve.Modalite.TP;
            default: return Epreuve.Modalite.EXAMEN_ECRIT;
        }
    }

    /**
     * Demande à l'utilisateur de sélectionner le cursus de l'étudiant parmi une liste.
     *
     * @return Le cursus choisi par l'utilisateur. Retourne E3e par défaut.
     */
    private Cursus selectionnerCursus() {
        System.out.println("Promo : 1.E1 | 2.E2 | 3.E3e | 4.E3a | 5.E4 | 6.E5");
        switch (scanner.nextLine().trim()) {
            case "1": return Cursus.E1;
            case "2": return Cursus.E2;
            case "4": return Cursus.E3a;
            case "5": return Cursus.E4;
            case "6": return Cursus.E5;
            default: return Cursus.E3e;
        }
    }

    /**
     * Demande à l'utilisateur l'identifiant d'un formulaire et procède à sa suppression via le système.
     */
    private void actionSupprimerFormulaire() {
        System.out.print("ID du formulaire à supprimer : ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            systeme.supprimerFormulaire(id);
        } catch (Exception e) { Logger.erreur(e.getMessage()); }
    }

    /**
     * Récupère la liste de tous les formulaires enregistrés et affiche un résumé pour chacun.
     */
    private void afficherTousLesFormulaires() {
        List<Formulaire> list = systeme.getFormulaires();
        if (list.isEmpty()) { Logger.info("Aucun dossier."); return; }
        for (Formulaire f : list) {
            System.out.println("ID: " + f.getId() + " | Epreuve: " + (f.getEpreuve() != null ? f.getEpreuve().getCodeECUE() : "N/A"));
            for (Etudiant e : f.getEtudiants()) System.out.println("  - Etu: " + e.getNom() + " " + e.getPrenom() + " (" + e.getId() + ")");
        }
    }

    /**
     * Guide l'utilisateur pour effectuer une recherche croisée dans les dossiers
     * en fonction d'un cursus spécifique et d'une catégorie de fraude précise.
     */
    private void actionRechercheCroisee() {
        Cursus c = selectionnerCursus();
        System.out.println("Type : 1.Papier | 2.Calculatrice | 3.IAG");
        Class<?> type = FraudePapier.class;
        String t = scanner.nextLine().trim();
        if (t.equals("2")) type = FraudeCalculatrice.class;
        if (t.equals("3")) type = FraudeIAGConnectee.class;
        List<Formulaire> res = systeme.rechercheCroisee(c, type);
        for (Formulaire f : res) System.out.println("ID : " + f.getId());
    }

    /**
     * Guide l'utilisateur pour rechercher un étudiant par son nom, son prénom ou son numéro apprenant.
     */
    private void actionRechercherEtudiant() {
        System.out.print("Nom ou ID de l'étudiant : ");
        String q = scanner.nextLine().trim();
        List<Etudiant> etus = systeme.rechercherEtudiantsParNomPrenom(q);
        Etudiant eId = systeme.trouverEtudiantParId(q);
        if (eId != null && !etus.contains(eId)) etus.add(eId);
        if (etus.isEmpty()) Logger.info("Aucun étudiant trouvé.");
        else for (Etudiant e : etus) System.out.println("- " + e.getNom() + " " + e.getPrenom() + " (" + e.getId() + ") [" + e.getCursus() + "]");
    }

    /**
     * Guide l'utilisateur pour rechercher tous les dossiers impliquant un étudiant ou une épreuve spécifique.
     */
    private void actionRechercherDossiers() {
        System.out.println("1. Par ID étudiant | 2. Par Code ECUE");
        String choix = scanner.nextLine().trim();
        if (choix.equals("1")) {
            System.out.print("ID étudiant : ");
            List<Formulaire> res = systeme.trouverFormulairesParEtudiant(scanner.nextLine().trim());
            for (Formulaire f : res) System.out.println("Dossier ID : " + f.getId());
        } else {
            System.out.print("Code ECUE : ");
            List<Formulaire> res = systeme.trouverFormulairesParEpreuve(scanner.nextLine().trim());
            for (Formulaire f : res) System.out.println("Dossier ID : " + f.getId());
        }
    }

    /**
     * Affiche les statistiques globales du système calculées sur l'ensemble des formulaires.
     */
    private void afficherStatistiques() {
        System.out.println("\n--- STATISTIQUES GLOBALES ---");
        System.out.println("Nombre total de dossiers : " + systeme.getNombreTotalFormulaires());
        System.out.println("Nombre d'étudiants distincts : " + systeme.getNombreEtudiantsDistincts());
        System.out.println("Nombre total de fraudes : " + systeme.getNombreTotalFraudes());
        System.out.printf("Moyenne de fraudes / dossier : %.2f\n", systeme.getMoyenneFraudesParFormulaire());
        System.out.printf("Écart-type : %.2f\n", systeme.getEcartTypeFraudesParFormulaire());
    }

    /**
     * Analyse et affiche le réseau de fraude (graphe de plagiat) ainsi que l'étudiant le plus impliqué.
     */
    private void actionAnalyserReseau() {
        System.out.println("\n--- ANALYSE DU RÉSEAU (GRAPHE) ---");
        Map<Etudiant, List<Etudiant>> graphe = systeme.genererGrapheTricheurs();
        if (graphe.isEmpty()) { System.out.println("Graphe vide."); return; }
        for (Map.Entry<Etudiant, List<Etudiant>> entry : graphe.entrySet()) {
            System.out.print(entry.getKey().getNom() + " " + entry.getKey().getPrenom() + " est lié à : ");
            for (Etudiant voisin : entry.getValue()) System.out.print(voisin.getNom() + " " + voisin.getPrenom() + ", ");
            System.out.println();
        }
        Etudiant cerveau = systeme.trouverTricheurLePlusConnecte();
        if (cerveau != null) System.out.println("\nLe cerveau présumé est : " + cerveau.getNom() + " " + cerveau.getPrenom());
    }

    /**
     * Affiche l'historique complet des actions effectuées et tracées par le système.
     */
    private void afficherJournalHistorique() {
        List<EntreeHistorique> hist = systeme.consulterHistorique();
        for (EntreeHistorique e : hist) System.out.println("[" + e.getHorodatage() + "] " + e.getAction());
    }
}
