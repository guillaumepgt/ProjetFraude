package fr.eseo.e3e.devlogiciel.formulaire;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.fraude.Fraude;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Formulaire {

    private int id;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
    private Epreuve epreuve;
    private List<Etudiant> etudiants;
    private List<Fraude> fraudes;






    public void Formulaire(){
        this.id=1234;
        this.dateCreation=LocalDateTime.now();
        this.dateDerniereModification=LocalDateTime.now();
        //this.epreuve=;
        this.etudiants= new ArrayList<>();
        this.fraudes=new ArrayList<Fraude>();
    }

    public void Formulaire(int id, LocalDateTime dateCreation, LocalDateTime dateDerniereModification, Epreuve epreuve, List<Etudiant> etudiants, List<Fraude> fraudes){
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

    public void ajouterEtudiant(List<Etudiant> etudiants){
        this.etudiants.add((Etudiant) etudiants);
        actualiserDateModification();
    }

    public void ajouterFraude(List<Fraude> fraudes){
        this.fraudes.add((Fraude) fraudes);
        actualiserDateModification();
    }

}
