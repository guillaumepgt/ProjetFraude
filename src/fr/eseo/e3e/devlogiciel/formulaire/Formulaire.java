package fr.eseo.e3e.devlogiciel.formulaire;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.fraude.Fraude;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Formulaire {

    private int id;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
    private Epreuve epreuve;
    private List<Etudiant> etudiants;
    private List<Fraude> fraudes;




    private void actualiserDateModification(){

    }

    public void Formulaire(int id, LocalDateTime dateCreation, LocalDateTime dateDerniereModification, Epreuve epreuve, List<Etudiant> etudiants, List<Fraude> fraudes){
        this.id=id;
        this.dateCreation=


    }

    public void ajouterEtudiant(){

    }

    public void ajouterFraude(){

    }


}
