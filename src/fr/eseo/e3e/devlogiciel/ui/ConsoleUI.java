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

        System.out.println("=================================================");
        System.out.println("  BIENVENUE DANS LE SYSTÈME DE GESTION DES FRAUDES");
        System.out.println("=================================================");

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
                    System.out.println("Arrêt de l'application. Au revoir !");
                    break;
                default:
                    System.out.println("⚠️ Option invalide. Choisissez entre 1 et 6.");
            }
        }
        scanner.close();
    }

    private void afficherMenuPrincipal() {
        System.out.println("\n================= MENU PRINCIPAL =================");
        System.out.println("1. Enregistrer un nouveau dossier de fraude complet");
        System.out.println("2. Supprimer un dossier par son ID");
        System.out.println("3. Afficher tous les dossiers et leurs détails");
        System.out.println("4. Lancer une recherche croisée ciblée (Cursus + Type)");
        System.out.println("5. Afficher le journal d'historique en direct");
        System.out.println("6. Quitter le programme");
        System.out.print("Votre choix (1-6) : ");
    }

    private void saisirNouveauFormulaire() {
        System.out.println("\n--- ENREGISTREMENT D'UN NOUVEAU FORMULAIRE ---");
        Formulaire formulaire = new Formulaire();

        System.out.println("\n[Étape 1 : Informations de l'Épreuve]");
        System.out.print("Code ECUE (ex: S06-POO) : ");
        String code = scanner.nextLine().trim();

        LocalDate dateEpreuve = LocalDate.now();
        System.out.print("Date (AAAA-MM-JJ) [Entrée = Aujourd'hui] : ");
        String dateStr = scanner.nextLine().trim();
        if (!dateStr.isEmpty()) {
            try { dateEpreuve = LocalDate.parse(dateStr); } catch (DateTimeParseException e) { System.out.println("Format invalide, date du jour choisie."); }
        }

        System.out.print("Heure (HH:MM) : ");
        LocalTime heure = LocalTime.of(8, 0);
        try { heure = LocalTime.parse(scanner.nextLine().trim()); } catch (DateTimeParseException e) { System.out.println("Heure par défaut réglée à 08:00."); }

        System.out.print("Durée (en minutes) : ");
        int duree = 120;
        try { duree = Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) { System.out.println("Durée par défaut réglée à 120 min."); }

        Epreuve.Modalite modalite = selectionnerModalite();
        Epreuve epreuve = new Epreuve(code, dateEpreuve, heure, duree, modalite);

        System.out.println("\n[Étape 2 : Informations de l'Étudiant Impliqué]");
        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine().trim();
        System.out.print("Numéro Apprenant : ");
        String numApp = scanner.nextLine().trim();

        Cursus cursus = selectionnerCursus();
        formulaire.ajouterEtudiant(new Etudiant(nom, prenom, numApp, cursus));

        System.out.println("\n[Étape 3 : Nature de la Fraude]");
        System.out.print("Description des faits : ");
        String desc = scanner.nextLine().trim();
        System.out.print("Contenu textuel / Preuve : ");
        String contenu = scanner.nextLine().trim();

        System.out.println("Catégorie : 1.Papier | 2.Calculatrice | 3.IAG Connectée");
        System.out.print("Votre choix : ");
        String cat = scanner.nextLine().trim();

        Fraude fraude;
        if (cat.equals("2")) {
            System.out.print("Marque de la calculatrice : ");
            String marque = scanner.nextLine().trim();
            System.out.print("Nom du programme : ");
            String prog = scanner.nextLine().trim();
            fraude = new FraudeCalculatrice(dateEpreuve, desc, contenu, marque, prog);
        } else if (cat.equals("3")) {
            System.out.print("Service IAG (ex: ChatGPT) : ");
            String service = scanner.nextLine().trim();
            System.out.print("Adresse IP : ");
            String ip = scanner.nextLine().trim();
            fraude = new FraudeIAGConnectee(dateEpreuve, desc, contenu, service, ip);
        } else {
            System.out.print("Dimensions du papier : ");
            String dim = scanner.nextLine().trim();
            System.out.print("Feuille pliée ? (oui/non) : ");
            boolean plie = scanner.nextLine().trim().equalsIgnoreCase("oui");
            fraude = new FraudePapier(dateEpreuve, desc, contenu, dim, plie);
        }

        formulaire.ajouterFraude(fraude);
        systeme.enregistrerFormulaire(formulaire);
        System.out.println("\n✔️ Dossier enregistré ! ID généré : " + formulaire.getId());
    }

    private Epreuve.Modalite selectionnerModalite() {
        System.out.println("Modalité : 1.ECRIT | 2.ORAL | 3.QCM | 4.MACHINE | 5.PROJET | 6.TP");
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
        System.out.println("Cursus : 1.E1 | 2.E2 | 3.E3e | 4.E3a | 5.E4 | 6.E5");
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
        System.out.print("❌ Entrez l'ID du formulaire à supprimer : ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            systeme.supprimerFormulaire(id);
            System.out.println("✔️ Demande traitée par le système.");
        } catch (NumberFormatException e) {
            System.out.println("⚠️ ID invalide.");
        }
    }

    private void afficherTousLesFormulaires() {
        List<Formulaire> formulaires = systeme.getFormulaires();
        if (formulaires.isEmpty()) {
            System.out.println("ℹ️ Base de données vide.");
            return;
        }
        System.out.println("\n================ BASE DE DONNÉES ================");
        for (Formulaire f : formulaires) {
            System.out.println("\n📂 DOSSIER ID : " + f.getId());
            System.out.println("   |_ Étudiant : " + f.getEtudiants().get(0).getNom() + " " + f.getEtudiants().get(0).getPrenom());
            System.out.println("   |_ Tricherie : " + f.getFraudes().get(0).getClass().getSimpleName() + " -> " + f.getFraudes().get(0).getDescription());
        }
    }

    private void actionRechercheCroisee() {
        System.out.println("\n--- CONFIGURATION RECHERCHE ---");
        Cursus cursus = selectionnerCursus();

        System.out.println("Type : 1.Papier | 2.Calculatrice | 3.IAG Connectée");
        System.out.print("Choix : ");
        Class<?> typeClasse = FraudePapier.class;
        String t = scanner.nextLine().trim();
        if (t.equals("2")) typeClasse = FraudeCalculatrice.class;
        if (t.equals("3")) typeClasse = FraudeIAGConnectee.class;

        List<Formulaire> res = systeme.rechercheCroisee(cursus, typeClasse);
        if (res.isEmpty()) {
            System.out.println("❌ Aucun résultat.");
        } else {
            System.out.println("🔍 " + res.size() + " dossier(s) trouvé(s) :");
            for (Formulaire f : res) System.out.println("  • ID : " + f.getId());
        }
    }

    private void afficherJournalHistorique() {
        List<EntreeHistorique> historique = systeme.consulterHistorique();
        if (historique.isEmpty()) {
            System.out.println("ℹ️ Journal vide.");
            return;
        }
        System.out.println("\n--- AUDIT LIVE ---");
        for (EntreeHistorique e : historique) {
            System.out.println("[" + e.getHorodatage() + "] " + e.getAction());
        }
    }
}