package fr.eseo.e3e.devlogiciel.formulaire;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.fraude.Fraude;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Formulaire {
    private static int compteurId = 0;
    private final int id;
    private final LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
    private final Epreuve epreuve;
    private final List<Etudiant> etudiants;
    private final List<Fraude> fraudes;






    public  Formulaire(){
        this.id=++compteurId;
        this.dateCreation=LocalDateTime.now();
        this.dateDerniereModification=this.dateCreation;
        this.epreuve=null;
        this.etudiants= new ArrayList<>();
        this.fraudes=new ArrayList<Fraude>();
    }

    public  Formulaire(int id, LocalDateTime dateCreation, LocalDateTime dateDerniereModification, Epreuve epreuve, List<Etudiant> etudiants, List<Fraude> fraudes){
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

    public void ajouterEtudiant(Etudiant etudiant){
        this.etudiants.add(etudiant);
        actualiserDateModification();
    }

    public void ajouterFraude(Fraude fraude){
        this.fraudes.add(fraude);
        actualiserDateModification();
    }

    public int getId(){
        return id;
    }

    public Epreuve getEpreuve(){
        return epreuve;
    }

    public List<Etudiant> getEtudiants(){
        return etudiants;
    }

    public List<Fraude> getFraudes(){
        return fraudes;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateDerniereModification() {
        return dateDerniereModification;
    }
}
