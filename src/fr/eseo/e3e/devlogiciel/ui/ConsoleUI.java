package fr.eseo.e3e.devlogiciel.ui;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.*;
import fr.eseo.e3e.devlogiciel.journalhistorique.EntreeHistorique;
import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final SystemeGestion systeme;
    private final Scanner scanner;

    public ConsoleUI(SystemeGestion systeme) {
        this.systeme = systeme;
        this.scanner = new Scanner(System.in);
    }

    public void demarrer() {
        boolean quitter = false;

        System.out.println("-------------------------------------------------");
        System.out.println("  APPLI DE GESTION DES FRAUDES ACADEMIQUES");
        System.out.println("-------------------------------------------------");

        while (!quitter) {
            afficherMenuPrincipal();
            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1":
                    saisirNouveauFormulaire();
                    break;
                case "2":
                    actionSupprimerFormulaire();
                    break;
                case "3":
                    afficherTousLesFormulaires();
                    break;
                case "4":
                    actionRechercheCroisee();
                    break;
                case "5":
                    afficherJournalHistorique();
                    break;
                case "6":
                    actionAnalyserReseau();
                    break;
                case "7":
                    quitter = true;
                    System.out.println("Fermeture de l'application.");
                    break;
                default:
                    System.out.println("[Erreur] Choix invalide, tapez un chiffre entre 1 et 6.");
            }
        }
        scanner.close();
    }

    private void afficherMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Creer un nouveau dossier de fraude");
        System.out.println("2. Supprimer un dossier par son ID");
        System.out.println("3. Afficher la liste des dossiers");
        System.out.println("4. Recherche croisee (Cursus + Type)");
        System.out.println("5. Afficher les logs du systeme");
        System.out.println("6. Analyser le réseau de tricheurs (Graphe)");
        System.out.println("7. Quitter");
        System.out.print("Votre choix : ");
    }

    private void saisirNouveauFormulaire() {
        System.out.println("\n=== REMPLISSAGE DU FORMULAIRE ===");
        Formulaire formulaire = new Formulaire();

        System.out.println("\n1. INFOS EPREUVE");
        System.out.print("Code ECUE (ex: S06-POO) : ");
        String code = scanner.nextLine().trim();

        LocalDate dateEpreuve = LocalDate.now();
        System.out.print("Date (AAAA-MM-JJ) [Entree pour aujourd'hui] : ");
        String dateStr = scanner.nextLine().trim();
        if (!dateStr.isEmpty()) {
            try {
                dateEpreuve = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Format incorrect. Date du jour selectionnee par defaut.");
            }
        }

        System.out.print("Heure du controle (HH:MM) : ");
        LocalTime heure = LocalTime.of(8, 0);
        try {
            heure = LocalTime.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Format incorrect. Heure mise a 08:00 par defaut.");
        }

        System.out.print("Duree totale (en minutes) : ");
        int duree = 120;
        try {
            duree = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valeur incorrecte. Duree mise a 120 min par defaut.");
        }

        Epreuve.Modalite modalite = selectionnerModalite();
        Epreuve epreuve = new Epreuve(code, dateEpreuve, heure, duree, modalite);

        System.out.println("\n2. INFOS ETUDIANT(S) IMPLIQUE(S)");
        boolean ajouterUnAutre = true;

        while (ajouterUnAutre) {
            System.out.print("Nom : ");
            String nom = scanner.nextLine().trim();
            System.out.print("Prenom : ");
            String prenom = scanner.nextLine().trim();
            System.out.print("Numero etudiant : ");
            String numApp = scanner.nextLine().trim();

            Cursus cursus = selectionnerCursus();
            formulaire.ajouterEtudiant(new Etudiant(nom, prenom, numApp, cursus));

            System.out.print("\nY a-t-il un autre etudiant (un complice) impliqué dans ce meme dossier ? (oui/non) : ");
            String reponse = scanner.nextLine().trim();
            if (!reponse.equalsIgnoreCase("oui")) {
                ajouterUnAutre = false;
            }
        }
        System.out.println("\n3. DETAILS DE LA FRAUDE");
        System.out.print("Description des faits : ");
        String desc = scanner.nextLine().trim();
        System.out.print("Preuves ou contenu textuel : ");
        String contenu = scanner.nextLine().trim();

        System.out.println("Type de support : 1.Papier | 2.Calculatrice | 3.IAG Connectee");
        System.out.print("Choix : ");
        String cat = scanner.nextLine().trim();

        Fraude fraude;
        if (cat.equals("2")) {
            System.out.print("Marque de la calculatrice : ");
            String marque = scanner.nextLine().trim();
            System.out.print("Nom du programme utilise : ");
            String prog = scanner.nextLine().trim();
            fraude = new FraudeCalculatrice(dateEpreuve, desc, contenu, marque, prog);
        } else if (cat.equals("3")) {
            System.out.print("Nom de l'IA (ex: ChatGPT) : ");
            String service = scanner.nextLine().trim();
            System.out.print("Adresse IP detectee : ");
            String ip = scanner.nextLine().trim();
            fraude = new FraudeIAGConnectee(dateEpreuve, desc, contenu, service, ip);
        } else {
            System.out.print("Format du papier (ex: A4, anti-seche) : ");
            String dim = scanner.nextLine().trim();
            System.out.print("Est-ce que la feuille etait pliee ? (oui/non) : ");
            boolean plie = scanner.nextLine().trim().equalsIgnoreCase("oui");
            fraude = new FraudePapier(dateEpreuve, desc, contenu, dim, plie);
        }

        formulaire.ajouterFraude(fraude);
        systeme.enregistrerFormulaire(formulaire);
        System.out.println("[OK] Dossier enregistre avec l'ID : " + formulaire.getId());
    }

    private Epreuve.Modalite selectionnerModalite() {
        System.out.println("Modalite : 1.ECRIT | 2.ORAL | 3.QCM | 4.MACHINE | 5.PROJET | 6.TP");
        System.out.print("Votre choix : ");
        switch (scanner.nextLine().trim()) {
            case "2": return Epreuve.Modalite.ORAL;
            case "3": return Epreuve.Modalite.QCM;
            case "4": return Epreuve.Modalite.SUR_ORDINATEUR;
            case "5": return Epreuve.Modalite.PROJET;
            case "6": return Epreuve.Modalite.TP;
            default: return Epreuve.Modalite.EXAMEN_ECRIT;
        }
    }

    private Cursus selectionnerCursus() {
        System.out.println("Promo : 1.E1 | 2.E2 | 3.E3e | 4.E3a | 5.E4 | 6.E5");
        System.out.print("Votre choix : ");
        switch (scanner.nextLine().trim()) {
            case "1": return Cursus.E1;
            case "2": return Cursus.E2;
            case "4": return Cursus.E3a;
            case "5": return Cursus.E4;
            case "6": return Cursus.E5;
            default: return Cursus.E3e;
        }
    }

    private void actionSupprimerFormulaire() {
        System.out.print("ID du formulaire a supprimer : ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            systeme.supprimerFormulaire(id);
            System.out.println("Traitement de la suppression terminee.");
        } catch (NumberFormatException e) {
            System.out.println("[Erreur] L'ID doit etre un nombre entier.");
        }
    }

    private void afficherTousLesFormulaires() {
        List<Formulaire> list = systeme.getFormulaires();
        if (list.isEmpty()) {
            System.out.println("Aucun dossier dans la base de donnees.");
            return;
        }
        System.out.println("\n--- LISTE DES DOSSIERS DE FRAUDE ---");
        for (Formulaire f : list) {
            System.out.println("Dossier ID : " + f.getId());


            if (!f.getEtudiants().isEmpty()) {
                System.out.println("  Etudiant(s) implique(s) :");

                for (Etudiant etu : f.getEtudiants()) {
                    System.out.println("   - " + etu.getNom() + " " + etu.getPrenom() + " (ID: " + etu.getId() + ")");
                }
            }

            if (!f.getFraudes().isEmpty()) {
                System.out.println("  Type fraude : " + f.getFraudes().get(0).getClass().getSimpleName());
                System.out.println("  Description : " + f.getFraudes().get(0).getDescription());
            }
            System.out.println("------------------------------------");
        }
    }

    private void actionRechercheCroisee() {
        System.out.println("\n--- FILTRES DE RECHERCHE ---");
        Cursus cursus = selectionnerCursus();

        System.out.println("Type de triche : 1.Papier | 2.Calculatrice | 3.IAG Connectee");
        System.out.print("Choix : ");
        Class<?> typeClasse = FraudePapier.class;
        String t = scanner.nextLine().trim();
        if (t.equals("2")) typeClasse = FraudeCalculatrice.class;
        if (t.equals("3")) typeClasse = FraudeIAGConnectee.class;

        List<Formulaire> res = systeme.rechercheCroisee(cursus, typeClasse);
        if (res.isEmpty()) {
            System.out.println("Aucun dossier ne correspond a ces criteres.");
        } else {
            System.out.println("Resultat(s) trouve(s) :");
            for (Formulaire f : res) {
                System.out.println("  - Dossier ID : " + f.getId());
            }
        }
    }

    private void afficherJournalHistorique() {
        List<EntreeHistorique> hist = systeme.consulterHistorique();
        if (hist.isEmpty()) {
            System.out.println("Le journal d'historique est vide.");
            return;
        }
        System.out.println("\n--- HISTORIQUE DU SYSTEME ---");
        for (EntreeHistorique e : hist) {
            System.out.println("[" + e.getHorodatage() + "] " + e.getAction());
        }
    }

    private void actionAnalyserReseau() {
        System.out.println("\n--- ANALYSE DU RESEAU DE FRAUDE ---");


        Etudiant suspect = systeme.trouverTricheurLePlusConnecte();

        if (suspect != null) {
            System.out.println("Resultat de l'analyse : Le cerveau presumé a été identifié !");
            System.out.println("L'etudiant au centre du plus grand nombre de fraudes croisees est :");
            System.out.println("-> " + suspect.getPrenom() + " " + suspect.getNom() + " (Cursus : " + suspect.getCursus() + ")");
        } else {
            System.out.println("Aucune fraude en groupe detectee pour le moment. Le reseau est vide ou sans connexions.");
        }
    }


}