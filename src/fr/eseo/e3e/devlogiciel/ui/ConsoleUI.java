package fr.eseo.e3e.devlogiciel.ui;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.*;
import fr.eseo.e3e.devlogiciel.journalhistorique.EntreeHistorique;
import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;
import fr.eseo.e3e.devlogiciel.utils.FraudeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
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
                    quitter = true;
                    System.out.println("Fermeture de l'application.");
                    break;
                default:
                    System.out.println("[Erreur] Choix invalide, tapez un chiffre entre 1 et 6.");
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
        System.out.println("5. Afficher les logs du système");
        System.out.println("6. Quitter");
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
        System.out.print("Date (JJ-MM-AAAA) [Entree pour aujourd'hui] : ");
        String dateStr = scanner.nextLine().trim();
        if (!dateStr.isEmpty()) {
            try {
                dateEpreuve = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Format incorrect. Date du jour sélectionnée par défaut.");
            }
        }

        System.out.print("Heure du contrôle (HH:MM) : ");
        LocalTime heure = LocalTime.of(8, 0);
        try {
            heure = LocalTime.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Format incorrect. Heure mise a 08:00 par défaut.");
        }

        System.out.print("Durée totale (en minutes) : ");
        int duree = 120;
        try {
            duree = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valeur incorrecte. Durée mise a 120 min par défaut.");
        }

        Epreuve.Modalite modalite = selectionnerModalite();
        formulaire.ajouterEpreuve(new Epreuve(code, dateEpreuve, heure, duree, modalite));

        System.out.println("\n2. INFOS ÉTUDIANT");
        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();
        System.out.print("Prenom : ");
        String prenom = scanner.nextLine().trim();
        System.out.print("Numero étudiant : ");
        String numApp = scanner.nextLine().trim();

        Cursus cursus = selectionnerCursus();
        formulaire.ajouterEtudiant(new Etudiant(nom, prenom, numApp, cursus));

        System.out.println("\n3. DETAILS DE LA FRAUDE");
        System.out.print("Description des faits : ");
        String desc = scanner.nextLine().trim();
        System.out.print("Preuves ou contenu textuel : ");
        String contenu = scanner.nextLine().trim();

        System.out.println("Type de support : 1.Papier | 2.Calculatrice | 3.IAG Connectée");
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
            System.out.print("Adresse IP détectée : ");
            String ip = scanner.nextLine().trim();
            fraude = new FraudeIAGConnectee(dateEpreuve, desc, contenu, service, ip);
        } else {
            System.out.print("Format du papier (ex: A4, anti-sèche) : ");
            String dim = scanner.nextLine().trim();
            System.out.print("Est-ce que la feuille était pliée ? (oui/non) : ");
            boolean plie = scanner.nextLine().trim().equalsIgnoreCase("oui");
            fraude = new FraudePapier(dateEpreuve, desc, contenu, dim, plie);
        }

        formulaire.ajouterFraude(fraude);
        systeme.enregistrerFormulaire(formulaire);
        System.out.println("[OK] Dossier enregistre avec l'ID : " + formulaire.getId());
    }

    /**
     * Demande à l'utilisateur de sélectionner la modalité de l'épreuve parmi une liste.
     *
     * @return La modalité choisie par l'utilisateur. Retourne EXAMEN_ECRIT par défaut.
     */
    private Epreuve.Modalite selectionnerModalite() {
        System.out.println("Modalité : 1.ECRIT | 2.ORAL | 3.QCM | 4.MACHINE | 5.PROJET | 6.TP");
        System.out.print("Votre choix : ");
        return switch (scanner.nextLine().trim()) {
            case "2" -> Epreuve.Modalite.ORAL;
            case "3" -> Epreuve.Modalite.QCM;
            case "4" -> Epreuve.Modalite.SUR_ORDINATEUR;
            case "5" -> Epreuve.Modalite.PROJET;
            case "6" -> Epreuve.Modalite.TP;
            default -> Epreuve.Modalite.EXAMEN_ECRIT;
        };
    }

    /**
     * Demande à l'utilisateur de sélectionner le cursus de l'étudiant parmi une liste.
     *
     * @return Le cursus choisi par l'utilisateur. Retourne E3e par défaut.
     */
    private Cursus selectionnerCursus() {
        System.out.println("Promo : 1.E1 | 2.E2 | 3.E3e | 4.E3a | 5.E4 | 6.E5");
        System.out.print("Votre choix : ");
        return switch (scanner.nextLine().trim()) {
            case "1" -> Cursus.E1;
            case "2" -> Cursus.E2;
            case "4" -> Cursus.E3a;
            case "5" -> Cursus.E4;
            case "6" -> Cursus.E5;
            default -> Cursus.E3e;
        };
    }

    /**
     * Demande à l'utilisateur l'identifiant d'un formulaire et procède à sa suppression via le système.
     */
    private void actionSupprimerFormulaire() {
        System.out.print("ID du formulaire a supprimer : ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());

            systeme.supprimerFormulaire(id);
            System.out.println("[OK] Traitement de la suppression terminée.");

        } catch (NumberFormatException e) {
            System.out.println("[Erreur] L'ID doit être un nombre entier.");

        } catch (FraudeException e) {
            System.out.println("[Erreur Metier] " + e.getMessage());
        }
    }

    /**
     * Récupère la liste de tous les formulaires enregistrés et affiche un résumé pour chacun.
     */
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
                System.out.println("  Étudiant : " + f.getEtudiants().get(0).getNom() + " " + f.getEtudiants().get(0).getPrenom());
            }
            if (!f.getFraudes().isEmpty()) {
                System.out.println("  Type fraude : " + f.getFraudes().get(0).getClass().getSimpleName());
                System.out.println("  Description : " + f.getFraudes().get(0).getDescription());
            }
            System.out.println("------------------------------------");
        }
    }

    /**
     * Guide l'utilisateur pour effectuer une recherche croisée dans les dossiers
     * en fonction d'un cursus spécifique et d'une catégorie de fraude précise.
     */
    private void actionRechercheCroisee() {
        System.out.println("\n--- FILTRES DE RECHERCHE ---");
        Cursus cursus = selectionnerCursus();

        System.out.println("Type de triche : 1.Papier | 2.Calculatrice | 3.IAG");
        System.out.print("Choix : ");
        Class<?> typeClasse = FraudePapier.class;
        String t = scanner.nextLine().trim();
        if (t.equals("2")) typeClasse = FraudeCalculatrice.class;
        if (t.equals("3")) typeClasse = FraudeIAGConnectee.class;

        List<Formulaire> res = systeme.rechercheCroisee(cursus, typeClasse);
        if (res.isEmpty()) {
            System.out.println("Aucun dossier ne correspond a ces critères.");
        } else {
            System.out.println("Résultats trouves :");
            for (Formulaire f : res) {
                System.out.println("  - Dossier ID : " + f.getId());
            }
        }
    }

    /**
     * Affiche l'historique complet des actions effectuées et tracées par le système.
     */
    private void afficherJournalHistorique() {
        List<EntreeHistorique> hist = systeme.consulterHistorique();
        if (hist.isEmpty()) {
            System.out.println("Le journal d'historique est vide.");
            return;
        }
        System.out.println("\n--- HISTORIQUE DU SYSTÈME ---");
        for (EntreeHistorique e : hist) {
            System.out.println("[" + e.getHorodatage() + "] " + e.getAction());
        }
    }
}