package fr.eseo.e3e.devlogiciel.systeme;

import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.Fraude;
import fr.eseo.e3e.devlogiciel.journalhistorique.EntreeHistorique;
import fr.eseo.e3e.devlogiciel.journalhistorique.JournalHistorique;
import fr.eseo.e3e.devlogiciel.utils.FraudeException;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale du projet qui sert de base de données en mémoire.
 * Elle gère les listes de formulaires, d'étudiants, et le journal d'historique.
 * Implémente Serializable pour permettre la sauvegarde dans un fichier binaire.
 */
public class SystemeGestion implements Serializable {
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
    }

    /**
     * Ajoute un nouveau formulaire dans la liste et trace l'action.
     *
     * @param f Le formulaire de fraude à enregistrer
     */
    public void enregistrerFormulaire(Formulaire f) {
        if (f != null) {
            this.formulaires.add(f);
            this.journal.ajouterEntree("Enregistrement du formulaire ID : " + f.getId());
        }
    }

    /**
     * Recherche et supprime un formulaire à partir de son ID.
     * Met à jour le journal avec la réussite ou l'échec de la suppression.
     *
     * @param id L'identifiant unique du formulaire à supprimer
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
        } else {
            throw new FraudeException("Aucun dossier trouve avec l'ID " + id + ".");
        }
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
        return resultats;
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
        } catch (java.io.IOException e) {
            System.err.println("[Erreur] Problème lors de la sauvegarde automatique : " + e.getMessage());
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
            return (SystemeGestion) ois.readObject();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("[Info] Aucune sauvegarde trouvée. Lancement d'un nouveau système a vide.");
        } catch (java.io.IOException | ClassNotFoundException e) {
            System.out.println("[Erreur] Fichier de sauvegarde illisible ou corrompu. Creation d'un système vide.");
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
}