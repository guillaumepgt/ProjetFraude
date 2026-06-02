package fr.eseo.e3e.devlogiciel.formulaire;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.fraude.Fraude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un formulaire de relevé de fraudes lors d'une épreuve.
 */
public class Formulaire {
    private static int compteurId = 0;
    private final int id;
    private final LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
    private final Epreuve epreuve;
    private final List<Etudiant> etudiants;
    private final List<Fraude> fraudes;

    /**
     * Crée un nouveau formulaire vide avec des informations par défaut.
     */
    public Formulaire(){
        this.id=++compteurId;
        this.dateCreation=LocalDateTime.now();
        this.dateDerniereModification=this.dateCreation;
        this.epreuve=null;
        this.etudiants= new ArrayList<>();
        this.fraudes=new ArrayList<Fraude>();
    }

    /**
     * Crée un nouveau formulaire avec toutes les informations spécifiées.
     *
     * @param id                        L'identifiant du formulaire
     * @param dateCreation              La date de création
     * @param dateDerniereModification  La date de la dernière modification
     * @param epreuve                   L'épreuve concernée
     * @param etudiants                 La liste des étudiants impliqués
     * @param fraudes                   La liste des fraudes relevées
     */
    public Formulaire(int id, LocalDateTime dateCreation, LocalDateTime dateDerniereModification, Epreuve epreuve, List<Etudiant> etudiants, List<Fraude> fraudes){
        this.id=id;
        this.dateCreation=dateCreation;
        this.dateDerniereModification=dateDerniereModification;
        this.epreuve=epreuve;
        this.etudiants=etudiants;
        this.fraudes=fraudes;
    }

    private void actualiserDateModification(){
        this.dateDerniereModification=LocalDateTime.now();
    }

    /**
     * Ajoute un étudiant au formulaire
     *
     * @param etudiant L'étudiant à ajouter
     */
    public void ajouterEtudiant(Etudiant etudiant){
        this.etudiants.add(etudiant);
        actualiserDateModification();
    }

    /**
     * Ajoute une fraude au formulaire.
     *
     * @param fraude La fraude à ajouter
     */
    public void ajouterFraude(Fraude fraude){
        this.fraudes.add(fraude);
        actualiserDateModification();
    }

    /**
     * Récupère l'identifiant du formulaire.
     *
     * @return L'identifiant du formulaire
     */
    public int getId(){
        return id;
    }

    /**
     * Récupère l'épreuve concernée par le formulaire.
     *
     * @return L'épreuve
     */
    public Epreuve getEpreuve(){
        return epreuve;
    }

    /**
     * Récupère la liste des étudiants impliqués
     *
     * @return La liste des étudiants
     */
    public List<Etudiant> getEtudiants(){
        return etudiants;
    }

    /**
     * Récupère la liste des fraudes.
     *
     * @return La liste des fraudes
     */
    public List<Fraude> getFraudes(){
        return fraudes;
    }

    /**
     * Récupère la date de création du formulaire.
     *
     * @return La date de création
     */
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    /**
     * Récupère la date de la dernière modification du formulaire.
     *
     * @return La date de dernière modification
     */
    public LocalDateTime getDateDerniereModification() {
        return dateDerniereModification;
    }
}