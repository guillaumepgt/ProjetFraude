package fr.eseo.e3e.devlogiciel.epreuve;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Epreuve implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Modalite {
        EXAMEN_ECRIT, ORAL, QCM, SUR_ORDINATEUR, PROJET, TP
    }

    private String codeECUE;
    private LocalDate date;
    private LocalTime heurePassage;
    private int duree;
    private Modalite modalite;

    public Epreuve() {
    }

    public Epreuve(String codeECUE, LocalDate date, LocalTime heurePassage, int duree, Modalite modalite) {
        this.codeECUE = codeECUE;
        this.date = date;
        this.heurePassage = heurePassage;
        this.duree = duree;
        this.modalite = modalite;
    }

    public String getCodeECUE() {
        return codeECUE;
    }

    public void setCodeECUE(String codeECUE) {
        this.codeECUE = codeECUE;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeurePassage() {
        return heurePassage;
    }

    public void setHeurePassage(LocalTime heurePassage) {
        this.heurePassage = heurePassage;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Modalite getModalite() {
        return modalite;
    }

    public void setModalite(Modalite modalite) {
        this.modalite = modalite;
    }
}