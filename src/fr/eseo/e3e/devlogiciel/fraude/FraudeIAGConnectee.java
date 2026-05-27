package fr.eseo.e3e.devlogiciel.fraude;

import java.time.LocalDate;

/**
 * Représente une fraude IAG avec internet lors d'une épreuve
 * Contient les informations de l'adresse ip utilisée
 */
public class FraudeIAGConnectee extends FraudeIAG {

    /**
     * Adresse IP de la fraude
     */
    private String adresseIP;

    /**
     * Crée une nouvelle fraude avec une IAG et adresse IP avec des informations par défaut.
     */
    public FraudeIAGConnectee() {
        super();
        this.adresseIP = "127.0.0.1";
    }

    public FraudeIAGConnectee(LocalDate dateReleve, String description, String contenu, String nomService, String adresseIP) {
        super(dateReleve, description, contenu, nomService);
        this.setAdresseIP(adresseIP);
    }

    /**
     * Récupère l'adresse IP de la fraude
     *
     * @return L'adresse IP de la fraude
     */
    public String getAdresseIP() {
        return adresseIP;
    }

    /**
     * Définit l'adresse IP de la fraude.
     *
     * @param adresseIP La nouvelle adresse IP à définir
     */
    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }
}