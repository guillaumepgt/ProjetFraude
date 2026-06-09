package fr.eseo.e3e.devlogiciel.systeme;

import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.Fraude;
import fr.eseo.e3e.devlogiciel.journalhistorique.EntreeHistorique;
import fr.eseo.e3e.devlogiciel.journalhistorique.JournalHistorique;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemeGestion implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Formulaire> formulaires;
    private final List<Etudiant> etudiants;
    private final JournalHistorique journal;

    public SystemeGestion() {
        this.formulaires = new ArrayList<>();
        this.etudiants = new ArrayList<>();
        this.journal = new JournalHistorique();
    }

    public void enregistrerFormulaire(Formulaire f) {
        if (f != null) {
            this.formulaires.add(f);
            this.journal.ajouterEntree("Enregistrement du formulaire ID : " + f.getId());
        }
    }

    public void supprimerFormulaire(int id) {
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
            this.journal.ajouterEntree("Tentative échouée de suppression du formulaire ID : " + id);
        }
    }

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

    public List<EntreeHistorique> consulterHistorique() {
        return this.journal.getEntrees();
    }

    public void sauvegarder(String cheminFichier) {
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(cheminFichier))) {
            oos.writeObject(this);
        } catch (java.io.IOException e) {
            System.err.println("⚠️ Erreur lors de la sauvegarde automatique : " + e.getMessage());
        }
    }

    public static SystemeGestion charger(String cheminFichier) {
        try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(cheminFichier))) {
            return (SystemeGestion) ois.readObject();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("[Système] Aucune sauvegarde trouvée. Initialisation à vide.");
        } catch (java.io.IOException | ClassNotFoundException e) {
            System.out.println("⚠️ Erreur de lecture de la sauvegarde. Création d'un système vide.");
        }
        return new SystemeGestion();
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

    public void initialiserJeuDeDonnees() {
        if (this.formulaires.isEmpty()) {
            Etudiant e1 = new Etudiant("Peloin", "Titouan", "444", Cursus.E3e);
            Etudiant e2 = new Etudiant("Prigent", "Guillaume", "555", Cursus.E3e);

            Formulaire formTest = new Formulaire();
            formTest.ajouterEtudiant(e1);
            formTest.ajouterEtudiant(e2);

            formTest.ajouterFraude(new fr.eseo.e3e.devlogiciel.fraude.FraudePapier(
                    java.time.LocalDate.now(),
                    "Copie flagrante en salle machine",
                    "Anti-sèche dans la trousse",
                    "A4",
                    true
            ));

            this.enregistrerFormulaire(formTest);
            this.journal.ajouterEntree("Initialisation du jeu de données de test (Bouchonnage) terminée.");
        }
    }

    public List<Formulaire> getFormulaires() { return formulaires; }
    public List<Etudiant> getEtudiants() { return etudiants; }
    public JournalHistorique getJournal() { return journal; }
}