package fr.eseo.e3e.devlogiciel.systeme;

import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.Fraude;
import fr.eseo.e3e.devlogiciel.journalhistorique.EntreeHistorique;
import fr.eseo.e3e.devlogiciel.journalhistorique.JournalHistorique;
import fr.eseo.e3e.devlogiciel.utils.FraudeException;
import fr.eseo.e3e.devlogiciel.utils.Logger;

import java.util.Map;
import java.util.HashMap;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale du projet qui sert de base de données en mémoire.
 * Elle gère les listes de formulaires, d'étudiants, et le journal d'historique.
 * Implémente Serializable pour permettre la sauvegarde dans un fichier binaire.
 */
public class SystemeGestion implements Serializable, IAnalyseStatistique {
    @Serial
    private static final long serialVersionUID = 1L;

    /** Liste de tous les formulaires enregistrés */
    private final List<Formulaire> formulaires;

    /** Liste globale des étudiants du système */
    private final List<Etudiant> etudiants;

    /** Objet qui stocke l'historique des actions de l'utilisateur */
    private final JournalHistorique journal;

    /**
     * Constructeur par défaut.
     * Initialise les listes et le journal à vide pour un nouveau système.
     */
    public SystemeGestion() {
        this.formulaires = new ArrayList<>();
        this.etudiants = new ArrayList<>();
        this.journal = new JournalHistorique();
        Logger.info("Création d'un Systeme de Gestion vide (constructeur par défaut).");
    }

    /**
     * Ajoute un nouveau formulaire dans la liste et trace l'action.
     * Met également à jour la liste globale des étudiants si nécessaire.
     *
     * @param formulaire Le formulaire de fraude à enregistrer
     */
    public void enregistrerFormulaire(Formulaire formulaire) {
        if (formulaire != null) {
            this.formulaires.add(formulaire);
            for (Etudiant etudiant : formulaire.getEtudiants()) {
                if (!this.etudiants.contains(etudiant)) {
                    this.etudiants.add(etudiant);
                }
            }
            this.journal.ajouterEntree("Enregistrement du formulaire ID : " + formulaire.getId());
            Logger.succes("Formulaire ID " + formulaire.getId() + " enregistré dans le système.");
        } else {
            Logger.avertissement("Tentative d'enregistrement d'un formulaire vide (null).");
        }
    }

    /**
     * Recherche et supprime un formulaire à partir de son ID.
     * Met à jour le journal avec la réussite ou l'échec de la suppression.
     *
     * @param id L'identifiant unique du formulaire à supprimer
     * @throws FraudeException Si le formulaire n'est pas trouvé dans la liste
     */
    public void supprimerFormulaire(int id) throws FraudeException {
        Formulaire aSupprimer = null;
        for (Formulaire f : formulaires) {
            if (f.getId() == id) {
                aSupprimer = f;
                break;
            }
        }

        if (aSupprimer != null) {
            this.formulaires.remove(aSupprimer);
            this.journal.ajouterEntree("Suppression du formulaire ID : " + id);
            Logger.succes("Le formulaire ID " + id + " a bien été supprimé.");
        } else {
            this.journal.ajouterEntree("Tentative échouée de suppression du formulaire ID : " + id);
            throw new FraudeException("Aucun dossier trouvé avec l'ID " + id + ".");
        }
    }

    /**
     * Retrouve tous les formulaires impliquant un étudiant donné par son ID.
     * @param numeroApprenant Le numéro de l'étudiant
     * @return Liste de formulaires
     */
    public List<Formulaire> trouverFormulairesParEtudiant(String numeroApprenant) {
        List<Formulaire> resultats = new ArrayList<>();
        for (Formulaire f : formulaires) {
            for (Etudiant e : f.getEtudiants()) {
                if (e.getId().equals(numeroApprenant)) {
                    resultats.add(f);
                    break;
                }
            }
        }
        return resultats;
    }

    /**
     * Retrouve tous les formulaires concernant une épreuve donnée par son code ECUE.
     * @param codeECUE Le code de l'épreuve
     * @return Liste de formulaires
     */
    public List<Formulaire> trouverFormulairesParEpreuve(String codeECUE) {
        List<Formulaire> resultats = new ArrayList<>();
        for (Formulaire f : formulaires) {
            if (f.getEpreuve() != null && f.getEpreuve().getCodeECUE().equals(codeECUE)) {
                resultats.add(f);
            }
        }
        return resultats;
    }

    /**
     * Recherche des étudiants par nom ou par prénom.
     * @param recherche La chaîne de recherche
     * @return Liste d'étudiants
     */
    public List<Etudiant> rechercherEtudiantsParNomPrenom(String recherche) {
        List<Etudiant> resultats = new ArrayList<>();
        String query = recherche.toLowerCase();
        for (Etudiant e : etudiants) {
            if (e.getNom().toLowerCase().contains(query) || e.getPrenom().toLowerCase().contains(query)) {
                resultats.add(e);
            }
        }
        return resultats;
    }

    /**
     * Retrouve un étudiant précis par son numéro apprenant.
     * @param numeroApprenant Le numéro unique
     * @return L'étudiant ou null
     */
    public Etudiant trouverEtudiantParId(String numeroApprenant) {
        for (Etudiant e : etudiants) {
            if (e.getId().equals(numeroApprenant)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Cherche les formulaires qui correspondent à la fois à un cursus spécifique
     * et à un type de fraude précis (Papier, Calculatrice, etc.).
     *
     * @param cursus Le cursus ciblé (ex: E3e)
     * @param typeFraude La classe de la fraude ciblée (ex: FraudePapier.class)
     * @return Une liste contenant les formulaires qui valident les deux critères
     */
    public List<Formulaire> rechercheCroisee(Cursus cursus, Class<?> typeFraude) {
        List<Formulaire> resultats = new ArrayList<>();

        for (Formulaire form : formulaires) {
            boolean correspondCursus = false;
            for (Etudiant etu : form.getEtudiants()) {
                if (etu.getCursus() == cursus) {
                    correspondCursus = true;
                    break;
                }
            }

            boolean contientTypeFraude = false;
            for (Fraude fraude : form.getFraudes()) {
                if (typeFraude.isInstance(fraude)) {
                    contientTypeFraude = true;
                    break;
                }
            }

            if (correspondCursus && contientTypeFraude) {
                resultats.add(form);
            }
        }

        this.journal.ajouterEntree("Recherche croisée effectuée pour le cursus " + cursus + " et le type " + typeFraude.getSimpleName());
        Logger.info("Recherche croisée terminée : " + resultats.size() + " résultat(s) trouvé(s).");
        return resultats;
    }

    @Override
    public int getNombreTotalFormulaires() {
        return formulaires.size();
    }

    @Override
    public int getNombreEtudiantsDistincts() {
        return etudiants.size();
    }

    @Override
    public int getNombreTotalFraudes() {
        int total = 0;
        for (Formulaire f : formulaires) {
            total += f.getFraudes().size();
        }
        return total;
    }

    @Override
    public double getMoyenneFraudesParFormulaire() {
        if (formulaires.isEmpty()) return 0.0;
        return (double) getNombreTotalFraudes() / formulaires.size();
    }

    @Override
    public double getEcartTypeFraudesParFormulaire() {
        if (formulaires.isEmpty()) return 0.0;
        double moyenne = getMoyenneFraudesParFormulaire();
        double sommeCarres = 0;
        for (Formulaire f : formulaires) {
            double nbFraudes = f.getFraudes().size();
            sommeCarres += Math.pow(nbFraudes - moyenne, 2);
        }
        return Math.sqrt(sommeCarres / formulaires.size());
    }

    /**
     * Permet de récupérer tout le journal des actions.
     *
     * @return La liste des entrées de l'historique
     */
    public List<EntreeHistorique> consulterHistorique() {
        return this.journal.getEntrees();
    }

    /**
     * Exporte l'instance actuelle du système dans un fichier binaire.
     *
     * @param cheminFichier Le nom ou le chemin du fichier de sauvegarde
     */
    public void sauvegarder(String cheminFichier) {
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(cheminFichier))) {
            oos.writeObject(this);
            Logger.succes("Sauvegarde terminée avec succès dans : " + cheminFichier);
        } catch (java.io.IOException e) {
            Logger.erreur("Problème lors de la sauvegarde automatique : " + e.getMessage());
        }
    }

    /**
     * Tente de recréer un SystemeGestion à partir d'un fichier de sauvegarde binaire.
     * Si le fichier n'existe pas ou est corrompu, retourne un nouveau système vide.
     *
     * @param cheminFichier Le nom ou le chemin du fichier à lire
     * @return Le système chargé ou un système vide
     */
    public static SystemeGestion charger(String cheminFichier) {
        try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(cheminFichier))) {
            SystemeGestion systemeCharge = (SystemeGestion) ois.readObject();
            Logger.succes("Système chargé avec succès (" + systemeCharge.getFormulaires().size() + " formulaires récupérés).");
            return systemeCharge;
        } catch (java.io.FileNotFoundException e) {
            Logger.info("Aucune sauvegarde trouvée. Lancement d'un nouveau système à vide.");
        } catch (java.io.IOException | ClassNotFoundException e) {
            Logger.erreur("Fichier de sauvegarde illisible ou corrompu. Création d'un système vide.");
        }
        return new SystemeGestion();
    }

    /**
     * Récupère la liste des formulaires.
     * @return La liste de formulaires
     */
    public List<Formulaire> getFormulaires() {
        return formulaires;
    }

    /**
     * Récupère la liste des étudiants.
     * @return La liste d'étudiants
     */
    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    /**
     * Récupère le journal d'historique du système.
     * @return L'objet JournalHistorique
     */
    public JournalHistorique getJournal() {
        return journal;
    }

    public Map<Etudiant, List<Etudiant>> genererGrapheTricheurs() {
        Map<Etudiant, List<Etudiant>> graphe = new HashMap<>();

        for (Formulaire formulaire : formulaires) {
            List<Etudiant> impliques = formulaire.getEtudiants();

            for (Etudiant e1 : impliques) {
                graphe.putIfAbsent(e1, new ArrayList<>());
                for (Etudiant e2 : impliques) {
                    if (!e1.equals(e2) && !graphe.get(e1).contains(e2)) {
                        graphe.get(e1).add(e2);
                    }
                }
            }
        }
        return graphe;
    }

    public Etudiant trouverTricheurLePlusConnecte() {
        Map<Etudiant, List<Etudiant>> graphe = genererGrapheTricheurs();
        Etudiant cerveau = null;
        int maxConnexions = -1;

        for (Map.Entry<Etudiant, List<Etudiant>> entree : graphe.entrySet()) {
            if (entree.getValue().size() > maxConnexions) {
                maxConnexions = entree.getValue().size();
                cerveau = entree.getKey();
            }
        }
        return cerveau;
    }
}